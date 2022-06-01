package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class RowSizeSubHeader extends SubHeader {
	
	/*
	 Struct<?> s = Struct.create(
				5*header.properties.integerBytesLength+"s"
				+header.properties.integerStructFormat
				+header.properties.integerStructFormat
				+2*header.properties.integerBytesLength+"s"
				+header.properties.integerStructFormat
				+header.properties.integerStructFormat
				+4*header.properties.integerBytesLength+"s"
				+header.properties.integerStructFormat
				+"290s" //8
				+"h"
				+"22s"
				+"h"
	 */
	
	public byte[] skip1;
	
	public Number rowLength;
	
	public Number rowCount;
	
	public byte[] skip2;
	
	public Number columnCountP1;
	
	public Number columnCountP2;
	
	public byte[] skip3;
	
	public Number mixedPageRowCount;

	@StructToken(order = 8,length = 290)
	public byte[] skip4;
	
	@StructToken(order = 9)
	public Short lcs;
	
	@StructToken(order = 10,length = 22)
	public byte[] skip5;

	@StructToken(order = 11)
	public 	Short lcp;
	
	
	



	public byte[] getSkip4() {
		return skip4;
	}

	public void setSkip4(byte[] skip4) {
		this.skip4 = skip4;
	}

	public Short getLcs() {
		return lcs;
	}

	public void setLcs(Short lcs) {
		this.lcs = lcs;
	}

	public byte[] getSkip2() {
		return skip2;
	}

	public void setSkip2(byte[] skip2) {
		this.skip2 = skip2;
	}

	public Short getLcp() {
		return lcp;
	}

	public void setLcp(Short lcp) {
		this.lcp = lcp;
	}

	public byte[] getSkip1() {
		return skip1;
	}

	public void setSkip1(byte[] skip1) {
		this.skip1 = skip1;
	}

	public Number getRowLength() {
		return rowLength;
	}

	public void setRowLength(Number rowLength) {
		this.rowLength = rowLength;
	}

	public Number getRowCount() {
		return rowCount;
	}

	public void setRowCount(Number rowCount) {
		this.rowCount = rowCount;
	}

	public void setColumnCountP1(Number rowCountP1) {
		this.columnCountP1 = rowCountP1;
	}


	public void setColumnCountP2(Number rowCountP2) {
		this.columnCountP2 = rowCountP2;
	}

	public byte[] getSkip3() {
		return skip3;
	}

	public void setSkip3(byte[] skip3) {
		this.skip3 = skip3;
	}

	public Number getMixedPageRowCount() {
		return mixedPageRowCount;
	}

	public void setMixedPageRowCount(Number mixedPageRowCount) {
		this.mixedPageRowCount = mixedPageRowCount;
	}

	public byte[] getSkip5() {
		return skip5;
	}

	public void setSkip5(byte[] skip5) {
		this.skip5 = skip5;
	}

	public Number getColumnCountP1() {
		return columnCountP1;
	}

	public Number getColumnCountP2() {
		return columnCountP2;
	}

	public Long getColumnCount() {
		return getColumnCountP1().longValue() + getColumnCountP2().longValue();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RowSizeSubHeader [rowLength=");
		builder.append(rowLength);
		builder.append(", rowCount=");
		builder.append(rowCount);
		builder.append(", columnCountP1=");
		builder.append(columnCountP1);
		builder.append(", columnCountP2=");
		builder.append(columnCountP2);
		builder.append(", mixedPageRowCount=");
		builder.append(mixedPageRowCount);
		builder.append(", lcs=");
		builder.append(lcs);
		builder.append(", lcp=");
		builder.append(lcp);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
