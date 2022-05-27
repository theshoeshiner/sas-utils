package org.thshsh.sas.xpt;

import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;

@StructEntity(charset = LibraryXpt.METADATA_CHARSET_NAME,trimAndPad = true)
public class LibraryXptHeader {

	/*
	 static Pattern HEADER_PATTERN = Pattern.compile(
			"(HEADER RECORD\\*{7}LIBRARY HEADER RECORD\\!{7}0{30} {2}SAS {5}SAS {5}SASLIB {2}(?<version>.{8})(?<os>.{8}) {24}(?<created>.{16})(?<modified>.{16}) {64})"
			,Pattern.DOTALL);
	 */
	
	@StructToken(order=0,length=104)
	public String magic;
	
	@StructToken(order=1,length=8)
	public String version;
	
	@StructToken(order=2,length=8,suffix = 24)
	public String os;
	
	@StructToken(order=3,length=16)
	public String createdString;
	
	@StructToken(order=4,length=16,suffix = 48)
	public String modifiedString;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header [magic=");
		builder.append(magic);
		builder.append(", version=");
		builder.append(version);
		builder.append(", os=");
		builder.append(os);
		builder.append(", created=");
		builder.append(createdString);
		builder.append(", modified=");
		builder.append(modifiedString);
		builder.append("]");
		return builder.toString();
	}
	
	
}
