package org.thshsh.sas.bdat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;

public class TextSubHeader extends SubHeader {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(TextSubHeader.class);

	public static final Struct<TextSubHeader> STRUCT = Struct.create(TextSubHeader.class);
	
		@StructToken(order = 0)
		
		public Short length; 
	
	//public abstract Long getLength();
	
	/*@StructTokenPrefix({
		@StructToken(type = TokenType.Bytes,constant = "00000000", validate = false)
		})
	@StructToken(order = 1,length = 16)
	public String compression;*/
	
	public String string;
	
	//These values are embedded in the string object and must be extracted based on indexes in the RowSizeSubheader
	//public Compression compression;
	//public String compression;
	//public String creatorProcess;
	//public DatasetBdat dataset;
	//public Integer offset ;
	
	public Integer getStringLength() {
		//length - this length - header
		//return getLength() - 12;
		return length - 12;
	}
	
	
	
	public Short getLength() {
		return length;
	}



	public void setLength(Short length) {
		this.length = length;
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
		//builder.append(getLength());
		builder.append(length);
		//builder.append(", string=");
		//builder.append(string);
		builder.append("]");
		return builder.toString();
	}

	public String getString() {
		return string;
	}
	
	//public abstract String getSubString(int start, int length);

	
	public void setString(String string) {
		this.string = string;
	}
	
	public String getSubString(int start, int length) {
		start = start - STRUCT.byteCount();
		return string.substring(start, start+length);
	}

}
