package org.thshsh.sas.bdat.x64;

import org.thshsh.sas.bdat.PageHeader;
import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;

public class PageHeader64 extends PageHeader {
	
	public static final Struct<PageHeader64> STRUCT = Struct.create(PageHeader64.class);

	@StructToken(order = 1,length = 28)
	public byte[] unknown;
	
}
