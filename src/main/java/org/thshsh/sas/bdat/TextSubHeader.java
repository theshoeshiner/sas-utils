package org.thshsh.sas.bdat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class TextSubHeader extends SubHeader {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(TextSubHeader.class);

	public static final Struct<TextSubHeader> STRUCT = Struct.create(TextSubHeader.class);
	
	@StructToken(order = 0)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000", validate = false)
		})
	public Short length; 
	
	public String string;
	
	public Compression compression;
	public String creatorProcess;
	public DatasetBdat dataset;
	public Integer offset;
	
	public Integer getStringLength() {
		//length - this length - header
		return length - 12;
	}
	
	public TextSubHeader() {}

	public TextSubHeader(String string) {
		super();
		this.string = string;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TextSubHeader [length=");
		builder.append(length);
		builder.append(", string=");
		builder.append(string);
		builder.append("]");
		return builder.toString();
	}

	public String getString() {
		return string;
	}
	
	public String getSubString(int start, int length) {
		start = start + offset;
		return string.substring(start, start+length);
	}

	public Short getLength() {
		return length;
	}

	public void setLength(Short length) {
		this.length = length;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	

}
