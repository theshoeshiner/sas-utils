package org.thshsh.sas.v56;

import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;

@StructEntity(trimAndPad = true,charset = LibraryXpt.METADATA_CHARSET_NAME)
public class VariableXpt140 extends VariableXpt {

	@Override
	@StructToken(order = 15,length=52)
	public void setSkip2(byte[] skip2) {
		super.setSkip2(skip2);
	}

	
	
	
}
