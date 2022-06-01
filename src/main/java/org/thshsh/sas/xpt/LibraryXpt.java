package org.thshsh.sas.xpt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.apache.commons.io.input.buffer.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.PagedInputStream;
import org.thshsh.struct.ByteOrder;
import org.thshsh.struct.Struct;

public class LibraryXpt extends org.thshsh.sas.Library {
	
	public static final String METADATA_CHARSET_NAME = "US-ASCII";
	public static Charset METADATA_CHARSET = Charset.forName("US-ASCII");
	
	static DateTimeFormatter DATE_TIME_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("ddMMMyy:HH:mm:ss")
            .toFormatter();
	
	protected LibraryXptHeader header;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LibraryXpt.class);
	//
	
	protected List<DatasetXpt> datasets;
	
	public LibraryXpt() {
		super();
	}
	
	


	public LibraryXptHeader getHeader() {
		return header;
	}


	public void setHeader(LibraryXptHeader header) {
		this.header = header;
	}


	public List<DatasetXpt> getDatasets() {
		if(datasets ==null) datasets = new ArrayList<DatasetXpt>();
		return datasets;
	}







	



	public static LibraryXpt fromFile(File f) throws IOException {
		return fromStream(new RandomAccessFileInputStream(new RandomAccessFile(f, "r")));
	}
	
	
	public static LibraryXpt fromStream(RandomAccessFileInputStream input) throws IOException {

		try {
		
			LibraryXpt library = new LibraryXpt();
			
			Struct<LibraryXptHeader> s = Struct.create(LibraryXptHeader.class);
			Struct<DatasetXpt> dsHeaderStruct = Struct.create(DatasetXpt.class);
			Struct<ObservationsXptHeader> obsHeaderStruct = Struct.create(ObservationsXptHeader.class);

			library.header = s.unpackEntity(input);
			
			LOGGER.info("header: {}",library.header);
			
			boolean nextMember = false;
			
			do {

				DatasetXpt dataset = dsHeaderStruct.unpackEntity(input);
				library.getDatasets().add(dataset);
				
				Struct<? extends VariableXpt> variableStruct = Struct.create((Class<? extends VariableXpt>)(dataset.getDescriptor140()?VariableXpt140.class:VariableXpt136.class)).byteOrder(ByteOrder.Big);
		
				PagedInputStream pis = new PagedInputStream(input, 80);
				
				for(int i=0;i<dataset.getVariableCount();i++) {
					VariableXpt var = variableStruct.unpackEntity(pis);
					LOGGER.info("variable: {}",var);
					dataset.getVariables().add(var);
				}
				
				LOGGER.info("position: {}",pis.getPosition());
				
				pis.nextPage();
				
				LOGGER.info("dataset: {}",dataset);
				
				LOGGER.info("searching for observations");
				
				BufferedInputStream bis = new BufferedInputStream(input);
				
				debugBytes(input, 100);
				
				//TODO can we just search for the next non null byte?
				searchForBytes(input,ObservationsXptHeader.HEADER_STRING.getBytes(),true);
				
				ObservationsXptHeader obs = obsHeaderStruct.unpackEntity(input);
				LOGGER.info("obs: {}",obs);
				
				dataset.observationStartByte = input.getRandomAccessFile().getFilePointer();
				
	
				LOGGER.info("searching for next dataset");
				nextMember = searchForBytes(input,DatasetXpt.HEADER_STRING.getBytes(),true);
			
				LOGGER.info("nextMember: {}",nextMember);
			
			}
			while(nextMember);
			
			
			
			
			/*for(VariableXpt var :  library.getVariables()) {
				
			}*/
			
			//check for other member header
			
			/*
				InputStreamCharSequence csis = new InputStreamCharSequence(input,size,METADATA_CHARSET);
			
			LOGGER.info("input:\n{}",csis);
	
			 
			 Matcher libHeadMatcher = HEADER_PATTERN.matcher(csis);
			 
			 if(libHeadMatcher.find()) {
				LOGGER.info("matches!");
				
				LocalDateTime created = parseDateTime(libHeadMatcher.group("created"));
				LocalDateTime modified = parseDateTime(libHeadMatcher.group("modified"));
				String sasOs = libHeadMatcher.group("os");
				String sasVersion = libHeadMatcher.group("version");
				
				LOGGER.info("sasOs: {} version: {}",sasOs,sasVersion);
				
				List<DatasetV56> members = new ArrayList<DatasetV56>();
				
				Matcher memHeadMatcher = MemberHeader.pattern.matcher(csis);
				
				int end = -1;
				DatasetV56 lastMember = null;
				while(memHeadMatcher.find()) {
					
					int start = memHeadMatcher.start();
					if(lastMember!=null) lastMember.setEndByte(start);
					end = memHeadMatcher.end();
					LOGGER.info("group: {}-{}",start,end);
			
					lastMember = org.thshsh.sas.v56.DatasetV56.from_bytes(memHeadMatcher);
					members.add(lastMember);
					
			
					
					
					//cs = csis.subSequence(end+40, end+80);
					//LOGGER.info("test chunk: {}",cs);
			
				}
				if(lastMember!=null) lastMember.setEndByte(csis.getPosition());
				
				
				LibraryV56 l = new LibraryV56(members, created, modified, sasOs, sasVersion);
				return l;
			}
			
			throw new InvalidFormatException("Invalid Header for V56 XPORT Format");*/
			
			return library;
		}
		finally {
			input.close();
		}
	}


	/**
	 * TODO Turn this into a generic byte buffer class that also tracks the lengths of each array 
	 * @param input
	 * @param search
	 * @param start
	 * @return
	 * @throws IOException
	 */
	public static boolean searchForBytes(RandomAccessFileInputStream input,byte[] search,boolean start) throws IOException {
		
		LOGGER.info("Starting search at: {}",input.getRandomAccessFile().getFilePointer());
		
		boolean found = false;

		int bufferSize = IOUtils.DEFAULT_BUFFER_SIZE<search.length*2?search.length*2:IOUtils.DEFAULT_BUFFER_SIZE;
		
	
		byte[] buffer0 = new byte[bufferSize/2];
		byte[] buffer1 = new byte[bufferSize/2];
		
		//start by filling buffer 0
		int read = 0;
		read = IOUtils.read(input, buffer0);
		do {
			//read into buffer1
			int r = IOUtils.read(input, buffer1);
			read += r;
			//search buffers
			int c = contains(read,search,buffer0, buffer1);

			if(c > -1) {
				LOGGER.info("Found search string in buffer at {}",c);
				
				if(start) input.getRandomAccessFile().seek(input.getRandomAccessFile().getFilePointer() - (read - c));
				
				LOGGER.info("Seeking: {}",input.getRandomAccessFile().getFilePointer());
				
				//LOGGER.info("file at: {}",input.getRandomAccessFile().getFilePointer());
				found = true;
			}
			else {
				//swap buffers
				read = 0 ;
				byte[] t = buffer1;
				buffer1 = buffer0;
				buffer0 = t;
			}
		}
		while(input.available()>0 && !found);
		
		LOGGER.info("Ending Search at: {}",input.getRandomAccessFile().getFilePointer());
				
		return found;
		
	}
	
	/**
	 * Searches an array for a sub array, up to the index specified by length, and returns the index where the found match begins
	 * @param array
	 * @param length
	 * @param find
	 * @return
	 */
	public static int contains(int length, byte[] find,byte[]... arrays ) {
		int matchIndex = 0;

		int i=0;
		for(byte[] array : arrays) {
			for(byte b : array) {
				if(b == find[matchIndex]) matchIndex++;
				else matchIndex = 0;
				if(matchIndex == find.length) {
					return i-find.length+1;
				}
				i++;
				if(i==length) return -1;
			}
		}
		
		return -1;
	}
	
	public static LocalDateTime parseDateTime(String s) {
		return LocalDateTime.from(DATE_TIME_FORMAT.parse(s));
		
	}


	@Override
	public LocalDateTime getModified() {
		return parseDateTime(header.modifiedString);
	}


	@Override
	public LocalDateTime getCreated() {
		return parseDateTime(header.createdString);
	}


	@Override
	public void setDatasets(List<? extends Dataset> ds) {
		this.datasets = (List<DatasetXpt>)datasets;
		
	}
	
	public static void debugBytes(RandomAccessFileInputStream input, int num) throws IOException {
		long mark = input.getRandomAccessFile().getFilePointer();
		byte[] bytes = new byte[num];
		IOUtils.read(input, bytes);
		LOGGER.info("Next {} Bytes String: {}",num,new String(bytes));
		LOGGER.info("Next {} Bytes Hex: {}",num,Hex.encodeHexString(bytes));
		input.getRandomAccessFile().seek(mark);
		
		
	}
	

}
