package org.thshsh.sas.xpt;

import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;

@StructEntity(trimAndPad = false,charset = LibraryXpt.METADATA_CHARSET_NAME)
public class ObservationsXptHeader {

	//There is inconsistent padding in front of this string that we have to skip over
	public static final String HEADER_STRING = "HEADER RECORD*******OBS     HEADER RECORD!!!!!!!000000000000000000000000000000  ";
	
	@StructToken(order =0,length = 80)
	public String header;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObservationHeader [header=");
		builder.append(header);
		builder.append("]");
		return builder.toString();
	}
	
	
}
