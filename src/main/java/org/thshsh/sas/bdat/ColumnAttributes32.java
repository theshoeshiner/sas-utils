package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class ColumnAttributes32 extends ColumnAttributes {

	
	@StructToken(order=0)
	public void setOffset(Integer offset) {
		super.setOffset(offset);
	}

	@StructToken(order=1)
	public void setLength(Integer length) {
		super.setLength(length);
	}


}
