package org.thshsh.sas.v56;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryV56 extends org.thshsh.sas.Library {
	
	public static Charset METADATA_CHARSET = Charset.forName("US-ASCII");
	
	static DateTimeFormatter DATE_TIME_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("ddMMMyy:HH:mm:ss")
            .toFormatter();
	
	public LibraryV56(List<Member> members, LocalDateTime created, LocalDateTime modified, String os, String version) {
		super(members, created, modified, os, version);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(LibraryV56.class);


	static Pattern PATTERN = Pattern.compile(
			"HEADER RECORD\\*{7}LIBRARY HEADER RECORD\\!{7}0{30} {2}SAS {5}SAS {5}SASLIB {2}(?<version>.{8})(?<os>.{8}) {24}(?<created>.{16})(?<modified>.{16}) {64}(?<members>.*)"
			,Pattern.DOTALL);
	
	static Pattern HEADER_PATTERN = Pattern.compile(
			"(HEADER RECORD\\*{7}LIBRARY HEADER RECORD\\!{7}0{30} {2}SAS {5}SAS {5}SASLIB {2}(?<version>.{8})(?<os>.{8}) {24}(?<created>.{16})(?<modified>.{16}) {64})"
			,Pattern.DOTALL);
	
	
	
	public static LibraryV56 from_file(File f) throws IOException {
		return from_bytes(new FileInputStream(f), f.length());
	}
	
	public static LibraryV56 from_bytes(InputStream input, long size) throws IOException {

		try {
		
			InputStreamCharSequence csis = new InputStreamCharSequence(input,size,METADATA_CHARSET);
			
			LOGGER.info("input:\n{}",csis);
	
			
			Matcher libHeadMatcher = HEADER_PATTERN.matcher(csis);
			
			if(libHeadMatcher.find()) {
				LOGGER.info("matches!");
				
				LocalDateTime created = parseDateTime(libHeadMatcher.group("created"));
				LocalDateTime modified = parseDateTime(libHeadMatcher.group("modified"));
				String sasOs = libHeadMatcher.group("os");
				String sasVersion = libHeadMatcher.group("version");
				
				List<Member> members = new ArrayList<Member>();
				
				Matcher memHeadMatcher = MemberHeader.pattern.matcher(csis);
				
				int end = -1;
				Member lastMember = null;
				while(memHeadMatcher.find()) {
					
					int start = memHeadMatcher.start();
					if(lastMember!=null) lastMember.setEndByte(start);
					end = memHeadMatcher.end();
					LOGGER.info("group: {}-{}",start,end);
	
					lastMember = org.thshsh.sas.v56.Member.from_bytes(memHeadMatcher);
					members.add(lastMember);
					
					/*for(int i=0;i<10;i++) {
						CharSequence cs = csis.subSequence(end+i*40, end+i*40 + 40);
						LOGGER.info("test chunk: {}",cs);
					}*/
					
					
					//cs = csis.subSequence(end+40, end+80);
					//LOGGER.info("test chunk: {}",cs);
		
				}
				if(lastMember!=null) lastMember.setEndByte(csis.getPosition());
				
				
				LibraryV56 l = new LibraryV56(members, created, modified, sasOs, sasVersion);
				return l;
			}
			
			throw new IllegalArgumentException("No Library Found");
			
		}
		finally {
			input.close();
		}
	}

	//static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("ddMMMyy:HH:mm:ss");
	
	
	
	
	public static LocalDateTime parseDateTime(String s) {
		return LocalDateTime.from(DATE_TIME_FORMAT.parse(s));
		
	}
}
