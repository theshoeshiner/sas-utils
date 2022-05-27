package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class SubHeaderPointer32 extends SubHeaderPointer {


	

	@StructToken(order=1)
	public void setOffset(Integer i) {
		super.setOffset(i);
	}

	@StructToken(order=2)
	public Integer getLength() {
		return length.intValue();
	}


	
}
