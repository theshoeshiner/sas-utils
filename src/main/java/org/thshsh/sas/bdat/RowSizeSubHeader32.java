package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class RowSizeSubHeader32 extends RowSizeSubHeader {

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
	
	@Override
	@StructToken(order = 0,length = 4*4)
	public void setSkip1(byte[] skip1) {
		super.setSkip1(skip1);
	}
	
	
	@StructToken(order = 1)
	public void setRowLength(Integer rowLength) {
		super.setRowLength(rowLength);
	}

	@StructToken(order = 2)
	public void setRowCount(Integer rowCount) {
		super.setRowCount(rowCount);
	}
	
	@Override
	@StructToken(order = 3,length = 2*4)
	public void setSkip2(byte[] skip2) {
		super.setSkip2(skip2);
	}


	@StructToken(order = 4)
	public void setColumnCountP1(Integer rowCountP1) {
		super.setColumnCountP1(rowCountP1);
	}

	@StructToken(order = 5)
	public void setColumnCountP2(Integer rowCountP2) {
		super.setColumnCountP2(rowCountP2);
	}

	@Override
	@StructToken(order = 6,length = 4*4)
	public void setSkip3(byte[] skip3) {
		super.setSkip3(skip3);
	}

	@StructToken(order = 7)
	public void setMixedPageRowCount(Integer mixedPageRowCount) {
		super.setMixedPageRowCount(mixedPageRowCount);
	}
	
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
				
				
	header.properties.rowLength = (int) objects.get(1);
		header.rowCount = (int) objects.get(2);
		header.properties.columnCountP1 = (int) objects.get(4);
		header.properties.columnCountP2 = (int) objects.get(5);
		header.properties.mixPageRowCount = (int) objects.get(7);
		header.properties.lcs =  (short) objects.get(9);
		header.properties.lcp = (short) objects.get(11);
	 */

	/*@StructToken(order = 0,length = 5*4)
	public byte[] skip1;
	
	@StructToken(order=1)
	public Integer rowLength;
	
	@StructToken(order=2)
	public Integer rowCount;
	
	@StructToken(order=3,length = 2*4)
	public byte[] skip2;
	
	@StructToken(order=4)
	public Integer rowCountP1;
	
	@StructToken(order=5)
	public Integer rowCountP2;
	
	
	@StructToken(order=6,length = 4*4)
	public byte[] skip3;
	
	@StructToken(order=7)
	public Integer mixedPageRowCount;*/
	
	

	/*@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RowSizeSubHeader32 [skip1=");
		builder.append(Arrays.toString(skip1));
		builder.append(", rowLength=");
		builder.append(rowLength);
		builder.append(", rowCount=");
		builder.append(rowCount);
		builder.append(", skip2=");
		builder.append(Arrays.toString(skip2));
		builder.append(", rowCountP1=");
		builder.append(rowCountP1);
		builder.append(", rowCountP2=");
		builder.append(rowCountP2);
		builder.append(", skip3=");
		builder.append(Arrays.toString(skip3));
		builder.append(", mixedPageRowCount=");
		builder.append(mixedPageRowCount);
		builder.append(", skip=");
		builder.append(Arrays.toString(skip));
		builder.append(", lcs=");
		builder.append(lcs);
		builder.append(", lcp=");
		builder.append(lcp);
		builder.append("]");
		return builder.toString();
	}
	*/
	

}
