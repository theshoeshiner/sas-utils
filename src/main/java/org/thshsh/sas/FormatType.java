package org.thshsh.sas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum FormatType {
	
	NUMERIC,CHARACTER,TIME,DATETIME,YYMMDD,MMDDYY,DDMMYY,DATE,JULIAN,MONYY;
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(FormatType.class);
	
	public static String CHARACTER_FORMAT = "$";
	
	public static FormatType fromString(String string) {
		//LOGGER.info("fromString: {}",string);
		
		try {
			return FormatType.valueOf(string.toUpperCase());
		}
		catch(IllegalArgumentException e) {}
		if(CHARACTER_FORMAT.equals(string)) return CHARACTER;
		else return NUMERIC;
		
	}
}