package org.thshsh.sas.xpt89;

import org.thshsh.sas.xpt.XptConstants;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.TokenType;

public class LibraryHeaderXpt extends org.thshsh.sas.xpt.LibraryHeaderXpt {

	public static final String HEADER_1 = XptConstants.HEADER_TAG + "LIBV8   HEADER RECORD!!!!!!!000000000000000000000000000000  ";
	
	@Override
	@StructTokenPrefix({
		@StructToken(type=TokenType.String,constant = HEADER_1),
		@StructToken(type=TokenType.String,constant = HEADER_2),
		@StructToken(type=TokenType.String,constant = HEADER_3),
		})
	@StructToken(order=1,length=8)
	public String getVersion() {
		return super.getVersion();
	}

	
	
}
