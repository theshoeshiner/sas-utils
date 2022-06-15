package org.thshsh.sas.xpt;

import org.apache.commons.lang3.NotImplementedException;
import org.thshsh.struct.StructEntity;

/**
 * Not sure if this will ever be needed, but supposedly some files written on VAX/VMX operating systems use this?
 * @author daniel.watson
 *
 */
@StructEntity(trimAndPad = true,charset = LibraryXpt.METADATA_CHARSET_NAME)
//TODO need to change the suffix to 48 bytes
public class VariableXpt136 extends VariableXpt {
	

	public VariableXpt136() {
		throw new NotImplementedException();
	}
	
	
}

