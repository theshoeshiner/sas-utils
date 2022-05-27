package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class ColumnAttributes64 extends ColumnAttributes {

	@StructToken(order=0)
	public void setOffset(Long offset) {
		super.setOffset(offset);
	}

	@StructToken(order=1)
	public void setLength(Long length) {
		super.setLength(length);
	}

	
}
