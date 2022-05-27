package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class SubHeaderPointer64 extends SubHeaderPointer {


	@StructToken(order=1)
	public Long getOffset() {
		return offset.longValue();
	}

	@StructToken(order=2)
	public Long getLength() {
		return length.longValue();
	}


	
	
}
