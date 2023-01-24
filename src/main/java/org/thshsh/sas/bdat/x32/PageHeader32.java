package org.thshsh.sas.bdat.x32;

import org.thshsh.sas.bdat.PageHeader;
import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;

public class PageHeader32 extends PageHeader {

	public static final Struct<PageHeader32> STRUCT = Struct.create(PageHeader32.class);
	
	@StructToken(order = 1,length = 12)
	public byte[] unknown;
	
}
