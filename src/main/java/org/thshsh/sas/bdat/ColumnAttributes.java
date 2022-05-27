package org.thshsh.sas.bdat;

import java.util.Arrays;

import org.thshsh.sas.VariableType;
import org.thshsh.struct.StructToken;

public class ColumnAttributes {
	
	
	Number offset;
	
	Number length;
	
	@StructToken(order=2,length = 2)
	byte[] skip;
	
	@StructToken(order=3)
	Byte variableTypeId;
	
	@StructToken(order=4)
	Byte skip2;

	public Number getOffset() {
		return offset;
	}

	public void setOffset(Number offset) {
		this.offset = offset;
	}

	public Number getLength() {
		return length;
	}

	public void setLength(Number length) {
		this.length = length;
	}

	public byte[] getSkip() {
		return skip;
	}

	public void setSkip(byte[] skip) {
		this.skip = skip;
	}

	public Byte getVariableTypeId() {
		return variableTypeId;
	}

	public void setVariableTypeId(Byte variableType) {
		this.variableTypeId = variableType;
	}
	
	public VariableType getVariableType() {
		return VariableType.values()[getVariableTypeId()-1];
	}

	public Byte getSkip2() {
		return skip2;
	}

	public void setSkip2(Byte skip2) {
		this.skip2 = skip2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[offset=");
		builder.append(offset);
		builder.append(", length=");
		builder.append(length);
		builder.append(", getVariableType()=");
		builder.append(getVariableType());
		builder.append("]");
		return builder.toString();
	}
	
	

}
