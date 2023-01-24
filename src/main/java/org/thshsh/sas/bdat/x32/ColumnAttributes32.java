package org.thshsh.sas.bdat.x32;

import org.thshsh.sas.bdat.ColumnAttributes;
import org.thshsh.struct.StructToken;

public class ColumnAttributes32 extends ColumnAttributes {

	@StructToken(order=0)
	public Integer offset;

	@Override
	public Long getOffset() {
		return offset.longValue();
	}
	
}
