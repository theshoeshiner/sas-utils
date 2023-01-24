package org.thshsh.sas.bdat.x32;

import org.thshsh.sas.bdat.Header3;
import org.thshsh.struct.StructToken;

public class Header332 extends Header3 {

	//public static final Struct<Header232> STRUCT = Struct.create(Header232.class);
	
	@StructToken(order = 6)
	public Integer pageCount;

	public Long getPageCount() {
		return pageCount.longValue();
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
}
