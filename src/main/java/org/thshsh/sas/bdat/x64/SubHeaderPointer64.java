package org.thshsh.sas.bdat.x64;

import org.thshsh.sas.bdat.SubHeaderPointer;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.TokenType;

public class SubHeaderPointer64 extends SubHeaderPointer {

	@StructToken(order=0,type = TokenType.LongUnsignedToSigned)
	public Long pageOffset;
	
	@StructToken(order=1,type = TokenType.LongUnsignedToSigned)
	public Long length;
	
	@StructToken(order=4,constant="000000000000",validate = false)
	public byte[] padding;

	@Override
	public Long getPageOffset() {
		return pageOffset;
	}

	@Override
	public Long getLength() {
		return length;
	}
	
	
}
