package org.thshsh.sas.bdat.x32;

import org.thshsh.sas.bdat.SubHeaderPointer;
import org.thshsh.struct.StructToken;

public class SubHeaderPointer32 extends SubHeaderPointer {
	
	@StructToken(order=0)
	public Integer pageOffset;
	
	@StructToken(order=1)
	public Integer length; //TODO long when 64
	
	@StructToken(order=4,constant="0000",validate = false)
	public byte[] padding;

	@Override
	public Long getPageOffset() {
		return pageOffset.longValue();
	}

	@Override
	public Long getLength() {
		return length.longValue();
	}
	
	

}
