package org.thshsh.sas.xpt;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;

import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.thshsh.sas.SasConstants;
import org.thshsh.struct.ByteOrder;
import org.thshsh.struct.Struct;

public class ParserXpt {

	public static LibraryXpt parseLibrary(File f) throws IOException {
		return ParserXpt.parseLibrary(new RandomAccessFileInputStream(new RandomAccessFile(f, "r")));
	}

	public static LibraryXpt parseLibrary(RandomAccessFileInputStream rafi) throws IOException {
	
		
		XptInputStream input = new XptInputStream(rafi, 80);
		
		try {
			
			
			
		
			LibraryXpt library = new LibraryXpt();
			
			Struct<LibraryHeaderXpt> s = Struct.create(LibraryHeaderXpt.class);
			Struct<DatasetXpt> dsHeaderStruct = Struct.create(DatasetXpt.class);
	
			library.header = s.unpackEntity(input);
			
			LibraryXpt.LOGGER.info("library header: {}",library.header);
			
			boolean nextMember = false;
			
			SasConstants.debugBytes(input, 80);
			
			//skip padding after header
			input.nextPage();
			
			do {
	
	
				DatasetXpt dataset = dsHeaderStruct.unpackEntity(input);
				
				LibraryXpt.LOGGER.info("dataset header: {}",dataset);
				
				library.getDatasets().add(dataset);
				
				Struct<? extends VariableXpt> variableStruct = Struct.create((Class<? extends VariableXpt>)(dataset.getDescriptor140()?VariableXpt140.class:VariableXpt136.class)).byteOrder(ByteOrder.Big);
		
	
				for(int i=0;i<dataset.getVariableCount();i++) {
					VariableXpt var = variableStruct.unpackEntity(input);
					LibraryXpt.LOGGER.info("variable: {}",var);
					dataset.getVariables().add(var);
				}
				
				LibraryXpt.LOGGER.info("position: {}",input.getPosition());
				
				SasConstants.debugBytes(input, 79);
				//debugBytes(input, 40);
				
				//observation header will be on next page, so skip any padding after variables
				input.nextPage();
				
				LibraryXpt.LOGGER.info("position after page: {}",input.getPosition());
				LibraryXpt.LOGGER.info("searching for observations");
				
				//BufferedInputStream bis = new BufferedInputStream(input);
				
				SasConstants.debugBytes(input, 79);
				//debugBytes(pis, 79);
				
				//TODO can we just search for the next non null byte?
				//searchForBytes(input,ObservationsXptHeader.HEADER_STRING.getBytes(),true);
				
				if(!input.isHeader()) throw new IllegalStateException("Observation header not found");
				
				input.nextPage();
				
				//ObservationsXptHeader obs = obsHeaderStruct.unpackEntity(input);
				//LOGGER.info("obs: {}",obs);
				
				//dataset.observationStartByte = input.getRandomAccessFile().getFilePointer();
				dataset.observationStartByte = input.getPosition();
				LibraryXpt.LOGGER.info("observationStartByte: {}",dataset.observationStartByte);
	
				//now read pages until we find another header
				
				LibraryXpt.LOGGER.info("searching for next dataset");
	
				nextMember = input.isHeader();
				while(!nextMember && input.nextPage()) {
					nextMember = input.isHeader();
				}
			
				LibraryXpt.LOGGER.info("nextMember: {}",nextMember);
			
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
