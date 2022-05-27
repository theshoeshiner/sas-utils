package org.thshsh.sas.v56;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
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
		
				
				for(int i=0;i<dataset.getVariableCount();i++) {
					VariableXpt var = variableStruct.unpackEntity(input);
					LOGGER.info("variable: {}",var);
					dataset.getVariables().add(var);
				}
				
				seekToBytes(input,ObservationsXptHeader.HEADER_STRING.getBytes(),true);
				
				ObservationsXptHeader obs = obsHeaderStruct.unpackEntity(input);
				LOGGER.info("obs: {}",obs);
				
				dataset.observationStartByte = input.getRandomAccessFile().getFilePointer();
				
				LOGGER.info("dataset: {}",dataset);
				
				nextMember = seekToBytes(input,DatasetXpt.HEADER_STRING.getBytes(),true);
			
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

	//static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("ddMMMyy:HH:mm:ss");
	
	public static boolean seekToBytes(RandomAccessFileInputStream input,byte[] search,boolean start) throws IOException {
		boolean found = false;
		int matchIndex = 0;
		do {
			byte b = (byte) input.read();
			if(b == search[matchIndex]) matchIndex++;
			else matchIndex = 0;
			if(matchIndex == search.length) {
				found = true;
			}
		}
		while(input.available()>0 && !found);
		
		if(start) input.getRandomAccessFile().seek(input.getRandomAccessFile().getFilePointer()-search.length);
		
		return found;
		
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
	

}
