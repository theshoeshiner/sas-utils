package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class ColumnName {
	
	public static final Struct<ColumnName> STRUCT = Struct.create(ColumnName.class);
	
	@StructToken(order=0)
	public Short index;
	
	@StructToken(order=1)
	public Short start;
	
	@StructToken(order=2)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000")})
	public Short length;
	
	
	DatasetBdat dataset;
	
	public ColumnName() {}

	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

	public Short getStart() {
		return start;
	}

	public void setStart(Short start) {
		this.start = start;
	}

	public Short getLength() {
		return length;
	}

	public void setLength(Short length) {
		this.length = length;
	}

	/*	public byte[] getSkip() {
			return skip;
		}
	
		public void setSkip(byte[] skip) {
			this.skip = skip;
		}*/
	
	public String getName() {
		return dataset.getSubHeaderString(index, start, length).get();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnName [index=");
		builder.append(index);
		builder.append(", start=");
		builder.append(start);
		builder.append(", length=");
		builder.append(length);
		builder.append("]");
		return builder.toString();
	}
	
	

}
