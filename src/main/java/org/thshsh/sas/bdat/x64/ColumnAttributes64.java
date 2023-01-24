package org.thshsh.sas.bdat.x64;

import org.thshsh.sas.bdat.ColumnAttributes;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.TokenType;

public class ColumnAttributes64 extends ColumnAttributes {

	@StructToken(order=0,type = TokenType.LongUnsignedToSigned)
	public Long offset;

	@Override
	public Long getOffset() {
		return offset;
	}
	

	
}
