package org.thshsh.sas.xpt;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;

import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.SasConstants;
import org.thshsh.struct.ByteOrder;
import org.thshsh.struct.Struct;

public class ParserXpt {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ParserXpt.class);

	
	public static LibraryXpt parseLibrary(File f) throws IOException {
		return ParserXpt.parseLibrary(new RandomAccessFileInputStream(new RandomAccessFile(f, "r")));
	}

	public static LibraryXpt parseLibrary(RandomAccessFileInputStream rafi) throws IOException {
	
		
		XptInputStream input = new XptInputStream(rafi, 80);
		
		try {
			
			
			
		
			LibraryXpt library = new LibraryXpt();
			
			Struct<LibraryHeaderXpt> s = Struct.create(LibraryHeaderXpt.class);
			Struct<DatasetHeaderXpt> dsHeaderStruct = Struct.create(DatasetHeaderXpt.class);
	
			library.header = s.unpackEntity(input);
			
			LOGGER.info("library header: {}",library.header);
			
			boolean nextMember = false;
			
			SasConstants.debugBytes(input, 80);
			
			//skip padding after header
			input.nextPage();
			
			do {
	
	
				DatasetHeaderXpt header = dsHeaderStruct.unpackEntity(input);
				
				LOGGER.info("dataset header: {}",header);
				
				DatasetXpt dataset = new DatasetXpt(header);
				
				library.getDatasets().add(dataset);
				
				if(!header.getDescriptor140()) throw new IllegalStateException("using 136");
				
				Struct<? extends VariableXpt> variableStruct = Struct.create((Class<? extends VariableXpt>)(header.getDescriptor140()?VariableXpt140.class:VariableXpt136.class)).byteOrder(ByteOrder.Big);
		
	
				for(int i=0;i<header.getVariableCount();i++) {
					VariableXpt var = variableStruct.unpackEntity(input);
					LOGGER.info("variable: {}",var);
					dataset.getVariables().add(var);
				}
				
				LOGGER.info("position: {}",input.getPosition());
				LOGGER.info("page position: {}",input.getPagePosition());
				
				SasConstants.debugBytes(input, 79);
				//debugBytes(input, 40);
				
				//skip to the next page if we are not already there
				input.nextPage();
				
				LOGGER.info("position after page: {}",input.getPosition());
				LOGGER.info("searching for observations");
				
				SasConstants.debugBytes(input, 79);

				//this page should be a header
				if(!input.isHeader()) throw new IllegalStateException("Observation header not found");
				
				//force skip to the next page where observations should be
				input.nextPage(true);
				
		
				//dataset.observationStartByte = input.getRandomAccessFile().getFilePointer();
				dataset.observationStartByte = input.getPosition();
				LOGGER.info("observationStartByte: {}",dataset.observationStartByte);
	
				//now skip all data pages until we find another header, which implies multiple datasets
				
				LOGGER.info("searching for next dataset");
	
				//force skip pages until we fund another header
				nextMember = input.isHeader();
				while(!nextMember && input.nextPage(true)) {
					nextMember = input.isHeader();
				}
			
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
	 * Searches an array for a sub array, up to the index specified by length, and returns the index where the found match begins
	 * @param array
	 * @param length
	 * @param find
	 * @return
	 */
	/*public static int contains(int length, byte[] find,byte[]... arrays ) {
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
	}*/
	
	public static LocalDateTime parseDateTime(String s) {
		return LocalDateTime.from(LibraryXpt.DATE_TIME_FORMAT.parse(s));
		
	}

}
