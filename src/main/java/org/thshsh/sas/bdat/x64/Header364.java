package org.thshsh.sas.bdat.x64;

import org.thshsh.sas.bdat.Header3;
import org.thshsh.struct.StructToken;

public class Header364 extends Header3 {
	
	//public static final Struct<Header264> STRUCT = Struct.create(Header264.class);
	
	@StructToken(order = 6)
	public Long pageCount;

	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}


	
}
