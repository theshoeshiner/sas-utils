package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class ColumnName {
	
	@StructToken(order=0)
	Short index;
	
	@StructToken(order=1)
	Short start;
	
	@StructToken(order=2)
	Short length;
	
	@StructToken(order=3,length = 2)
	byte[] skip;
	
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

	public byte[] getSkip() {
		return skip;
	}

	public void setSkip(byte[] skip) {
		this.skip = skip;
	}
	
	public String getName() {
		return dataset.getSubHeaderString(index, start, length).get();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnNameSubHeader [index=");
		builder.append(index);
		builder.append(", start=");
		builder.append(start);
		builder.append(", length=");
		builder.append(length);
		builder.append("]");
		return builder.toString();
	}
	
	

}
