package org.thshsh.sas.xpt;

import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

@StructEntity(charset = LibraryXpt.METADATA_CHARSET_NAME,trimAndPad = true)
public class LibraryHeaderXpt {


	public static final String HEADER_1 = XptConstants.HEADER_TAG + "LIBRARY HEADER RECORD!!!!!!!000000000000000000000000000000  ";
	public static final String HEADER_2 = "SAS     ";
	public static final String HEADER_3 = "SASLIB  ";
	
	@StructTokenPrefix({
		@StructToken(type=TokenType.String,constant = HEADER_1),
		@StructToken(type=TokenType.String,constant = HEADER_2),
		@StructToken(type=TokenType.String,constant = HEADER_2),
		@StructToken(type=TokenType.String,constant = HEADER_3),
		})
	@StructToken(order=1,length=8)
	public String version;
	
	@StructToken(order=2,length=8)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant = XptConstants.SPACES_24)}) 
	public String os;
	
	@StructToken(order=3,length=16)
	public String createdString;
	
	@StructToken(order=4,length=16)
	public String modifiedString;
	

	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header [magic=");

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
