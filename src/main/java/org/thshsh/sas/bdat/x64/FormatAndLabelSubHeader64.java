package org.thshsh.sas.bdat.x64;

import org.thshsh.sas.bdat.FormatAndLabelSubHeader;
import org.thshsh.struct.StructToken;

public class FormatAndLabelSubHeader64 extends FormatAndLabelSubHeader {

	@StructToken(order=-10,constant = "0000000000000000")
	public byte[] unknown;
	
}
