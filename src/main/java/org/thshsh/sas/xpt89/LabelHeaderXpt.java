package org.thshsh.sas.xpt89;

import org.thshsh.sas.xpt.XptConstants;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class LabelHeaderXpt {

	public static final String HEADER_PREFIX =    XptConstants.HEADER_TAG+ "LABELV8 HEADER RECORD!!!!!!!";
	
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = HEADER_PREFIX)})
	@StructToken(type = TokenType.String,length = 5)
	@StructTokenSuffix({@StructToken(type=TokenType.String,constant = "                           ")})
	String variableCountString;
	
}
