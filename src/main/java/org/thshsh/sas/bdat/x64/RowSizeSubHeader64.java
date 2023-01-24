package org.thshsh.sas.bdat.x64;

import org.thshsh.sas.bdat.RowSizeSubHeader;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class RowSizeSubHeader64 extends RowSizeSubHeader  {

	@StructToken(order = -10,length = 32)  //32 bytes if 64
	public byte[] unknown0;

	@StructToken(order=0,type = TokenType.LongUnsignedToSigned) //TODO long if 64
	public Long rowLength;
	
	@StructToken(order = 10,type = TokenType.LongUnsignedToSigned) //long if 64
	public Long rowCount;
	
	@StructToken(order = 15,length = 8)  //double if 64
	public byte[] unknown00;
	
	@StructToken(order = 20,type = TokenType.LongUnsignedToSigned) //long if 64
	public Long deletedRowCount;
	
	@StructToken(order = 30,type = TokenType.LongUnsignedToSigned)  //long if 64
	public Long columnCountP1;
	
	@StructToken(order = 40,type = TokenType.LongUnsignedToSigned)  //long if 64
	public Long columnCountP2;
	
	
	@StructToken(order = 45,length = 16)  //16 bytes if 64
	public byte[] unknown1;
	
	@StructToken(order = 50,type = TokenType.LongUnsignedToSigned)
	public Long pageSize; //long if 64
	
	@StructToken(order = 55,length = 8)  //double if 64
	public byte[] unknown2;
	
	@StructToken(order = 60,type = TokenType.LongUnsignedToSigned)
	public Long mixedPageRowCount; //long if 64
	

	@StructTokenPrefix({
		//double if x64
		@StructToken(type = TokenType.Bytes,constant = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"),
		@StructToken(type = TokenType.Bytes,constant = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",validate = false)
	})
	@StructToken(order = 70) //always equal to pageheader sig
	@StructTokenSuffix({
		//double if x64
		@StructToken(type = TokenType.Bytes,constant = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",validate = false)
	})
	public Integer pageSequence;

	@StructToken(order = 80,type = TokenType.LongUnsignedToSigned)
	public Long unknown3; //long if 64 , value = 1

	@StructToken(order = 85)
	public Short unknown4; //value = 2
	
	@StructToken(order = 86,constant="000000000000",validate = false)  //6 bytes if x64
	public byte[] unknown5;
	
	@StructToken(order = 90,type = TokenType.LongUnsignedToSigned)
	public Long pagesWithSubHeadersCount; //long if 64 
	
	@StructToken(order = 100)
	public Short lastPageSubHeadersCount;
	
	@StructToken(order = 105,constant="000000000000",validate = false)  //6 bytes if x64
	public byte[] unknown6;
	
	@StructToken(order = 110,type = TokenType.LongUnsignedToSigned)
	public Long pagesWithSubHeadersCountDuplicate; //long if 64
	
	@StructToken(order = 120)
	public Short numLastPageSubHeadersPlusTwo; //offset 
	
	@StructToken(order = 125,constant="000000000000",validate = false)  //6 bytes if x64
	public byte[] unknown7;
	
	@StructToken(order = 130,type = TokenType.LongUnsignedToSigned) 
	public Long pageCount; //long if x64
	
	@StructToken(order = 131) 
	public Short unknown8; //values 22,26,9,56 observed 
	
	@StructToken(order = 132,constant="000000000000",validate = false)  //6 bytes if x64
	public byte[] unknown9;
	
	@StructToken(order = 133,type = TokenType.LongUnsignedToSigned)
	public Long unknown10; //long if x64 
	
	@StructToken(order = 134) 
	public Short unknown11; //values 7|8 
	
	@StructToken(order = 135,constant="000000000000",validate=false)  //6 bytes if x64, values observerd 0100
	public byte[] unknown12;
	
	
	@StructToken(order = 136,type = TokenType.Bytes,constant = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",validate = false) //double if x64
	public byte[] unknown13;


	public byte[] getUnknown0() {
		return unknown0;
	}


	public void setUnknown0(byte[] unknown0) {
		this.unknown0 = unknown0;
	}


	public Long getRowLength() {
		return rowLength;
	}


	public void setRowLength(Long rowLength) {
		this.rowLength = rowLength;
	}


	public Long getRowCount() {
		return rowCount;
	}


	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}


	public byte[] getUnknown00() {
		return unknown00;
	}


	public void setUnknown00(byte[] unknown00) {
		this.unknown00 = unknown00;
	}


	public Long getDeletedRowCount() {
		return deletedRowCount;
	}


	public void setDeletedRowCount(Long deletedRowCount) {
		this.deletedRowCount = deletedRowCount;
	}


	public Long getColumnCountP1() {
		return columnCountP1;
	}


	public void setColumnCountP1(Long columnCountP1) {
		this.columnCountP1 = columnCountP1;
	}


	public Long getColumnCountP2() {
		return columnCountP2;
	}


	public void setColumnCountP2(Long columnCountP2) {
		this.columnCountP2 = columnCountP2;
	}


	public byte[] getUnknown1() {
		return unknown1;
	}


	public void setUnknown1(byte[] unknown1) {
		this.unknown1 = unknown1;
	}


	public Long getPageSize() {
		return pageSize;
	}


	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}


	public byte[] getUnknown2() {
		return unknown2;
	}


	public void setUnknown2(byte[] unknown2) {
		this.unknown2 = unknown2;
	}


	public Long getMixedPageRowCount() {
		return mixedPageRowCount;
	}


	public void setMixedPageRowCount(Long mixedPageRowCount) {
		this.mixedPageRowCount = mixedPageRowCount;
	}


	public Integer getPageSequence() {
		return pageSequence;
	}


	public void setPageSequence(Integer pageSequence) {
		this.pageSequence = pageSequence;
	}


	public Long getUnknown3() {
		return unknown3;
	}


	public void setUnknown3(Long unknown3) {
		this.unknown3 = unknown3;
	}


	public Short getUnknown4() {
		return unknown4;
	}


	public void setUnknown4(Short unknown4) {
		this.unknown4 = unknown4;
	}


	public byte[] getUnknown5() {
		return unknown5;
	}


	public void setUnknown5(byte[] unknown5) {
		this.unknown5 = unknown5;
	}


	public Long getPagesWithSubHeadersCount() {
		return pagesWithSubHeadersCount;
	}


	public void setPagesWithSubHeadersCount(Long pagesWithSubHeadersCount) {
		this.pagesWithSubHeadersCount = pagesWithSubHeadersCount;
	}


	public Short getLastPageSubHeadersCount() {
		return lastPageSubHeadersCount;
	}


	public void setLastPageSubHeadersCount(Short lastPageSubHeadersCount) {
		this.lastPageSubHeadersCount = lastPageSubHeadersCount;
	}


	public byte[] getUnknown6() {
		return unknown6;
	}


	public void setUnknown6(byte[] unknown6) {
		this.unknown6 = unknown6;
	}


	public Long getPagesWithSubHeadersCountDuplicate() {
		return pagesWithSubHeadersCountDuplicate;
	}


	public void setPagesWithSubHeadersCountDuplicate(Long pagesWithSubHeadersCountDuplicate) {
		this.pagesWithSubHeadersCountDuplicate = pagesWithSubHeadersCountDuplicate;
	}


	public Short getNumLastPageSubHeadersPlusTwo() {
		return numLastPageSubHeadersPlusTwo;
	}


	public void setNumLastPageSubHeadersPlusTwo(Short numLastPageSubHeadersPlusTwo) {
		this.numLastPageSubHeadersPlusTwo = numLastPageSubHeadersPlusTwo;
	}


	public byte[] getUnknown7() {
		return unknown7;
	}


	public void setUnknown7(byte[] unknown7) {
		this.unknown7 = unknown7;
	}


	public Long getPageCount() {
		return pageCount;
	}


	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}


	public Short getUnknown8() {
		return unknown8;
	}


	public void setUnknown8(Short unknown8) {
		this.unknown8 = unknown8;
	}


	public byte[] getUnknown9() {
		return unknown9;
	}


	public void setUnknown9(byte[] unknown9) {
		this.unknown9 = unknown9;
	}


	public Long getUnknown10() {
		return unknown10;
	}


	public void setUnknown10(Long unknown10) {
		this.unknown10 = unknown10;
	}


	public Short getUnknown11() {
		return unknown11;
	}


	public void setUnknown11(Short unknown11) {
		this.unknown11 = unknown11;
	}


	public byte[] getUnknown12() {
		return unknown12;
	}


	public void setUnknown12(byte[] unknown12) {
		this.unknown12 = unknown12;
	}


	public byte[] getUnknown13() {
		return unknown13;
	}


	public void setUnknown13(byte[] unknown13) {
		this.unknown13 = unknown13;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RowSizeSubHeader64 [");
		//builder.append(Arrays.toString(unknown0));
		builder.append("rowLength=");
		builder.append(rowLength);
		builder.append(", rowCount=");
		builder.append(rowCount);
		/*builder.append(", unknown00=");
		builder.append(Arrays.toString(unknown00));*/
		builder.append(", deletedRowCount=");
		builder.append(deletedRowCount);
		builder.append(", columnCountP1=");
		builder.append(columnCountP1);
		builder.append(", columnCountP2=");
		builder.append(columnCountP2);
		/*builder.append(", unknown1=");
		builder.append(Arrays.toString(unknown1));*/
		builder.append(", pageSize=");
		builder.append(pageSize);
		/*builder.append(", unknown2=");
		builder.append(Arrays.toString(unknown2));*/
		builder.append(", mixedPageRowCount=");
		builder.append(mixedPageRowCount);
		builder.append(", pageSequence=");
		builder.append(pageSequence);
		/*builder.append(", unknown3=");
		builder.append(unknown3);
		builder.append(", unknown4=");
		builder.append(unknown4);
		builder.append(", unknown5=");
		builder.append(Arrays.toString(unknown5));*/
		builder.append(", pagesWithSubHeadersCount=");
		builder.append(pagesWithSubHeadersCount);
		builder.append(", lastPageSubHeadersCount=");
		builder.append(lastPageSubHeadersCount);
		/*builder.append(", unknown6=");
		builder.append(Arrays.toString(unknown6));*/
		builder.append(", pagesWithSubHeadersCountDuplicate=");
		builder.append(pagesWithSubHeadersCountDuplicate);
		builder.append(", numLastPageSubHeadersPlusTwo=");
		builder.append(numLastPageSubHeadersPlusTwo);
		/*builder.append(", unknown7=");
		builder.append(Arrays.toString(unknown7));*/
		builder.append(", pageCount=");
		builder.append(pageCount);
		/*builder.append(", unknown8=");
		builder.append(unknown8);
		builder.append(", unknown9=");
		builder.append(Arrays.toString(unknown9));
		builder.append(", unknown10=");
		builder.append(unknown10);
		builder.append(", unknown11=");
		builder.append(unknown11);
		builder.append(", unknown12=");
		builder.append(Arrays.toString(unknown12));
		builder.append(", unknown13=");
		builder.append(Arrays.toString(unknown13));
		builder.append(", unknown14=");
		builder.append(unknown14);
		builder.append(", unknown15=");
		builder.append(unknown15);
		builder.append(", unknown16=");
		builder.append(unknown16);
		builder.append(", unknown17=");
		builder.append(unknown17);*/
		builder.append(", creatorSoftwareOffset=");
		builder.append(creatorSoftwareOffset);
		builder.append(", creatorSoftwareLength=");
		builder.append(creatorSoftwareLength);
		/*	builder.append(", unknown18=");
			builder.append(unknown18);
			builder.append(", unknown19=");
			builder.append(unknown19);*/
		builder.append(", maxLengthValid=");
		builder.append(maxLengthValid);
		/*builder.append(", unknown20=");
		builder.append(unknown20);*/
		builder.append(", compressionMethodOffset=");
		builder.append(compressionMethodOffset);
		builder.append(", compressionMethodLength=");
		builder.append(compressionMethodLength);
		/*builder.append(", unknown21=");
		builder.append(unknown21);
		builder.append(", unknown22=");
		builder.append(unknown22);
		builder.append(", unknown23=");
		builder.append(unknown23);
		builder.append(", unknown24=");
		builder.append(unknown24);*/
		builder.append(", creatorProcOffset=");
		builder.append(creatorProcOffset);
		builder.append(", creatorProcLength=");
		builder.append(creatorProcLength);
		/*builder.append(", unknown26=");
		builder.append(unknown26);
		builder.append(", unknown27=");
		builder.append(unknown27);*/
		builder.append(", columnTextHeadersCount=");
		builder.append(columnTextHeadersCount);
		builder.append(", columnNameMaxLength=");
		builder.append(columnNameMaxLength);
		builder.append(", columnLabelMaxLength=");
		builder.append(columnLabelMaxLength);
		builder.append(", fullPageRowsCount=");
		builder.append(fullPageRowsCount);
		/*builder.append(", unknown28=");
		builder.append(unknown28);*/
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
}
