package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public abstract class RowSizeSubHeader extends SubHeader {
	

	
	@StructToken(order = 137) 
	public Short unknown14; //value 0
	
	@StructToken(order = 138) 
	public Short unknown15; //value 0|8
	
	@StructToken(order = 139) 
	public Short unknown16; //value 4
	
	@StructToken(order = 140) 
	public Short unknown17; //value 0
	
	
	@StructToken(order = 150)
	public Short creatorSoftwareOffset; 
	
	@StructToken(order = 160)
	public Short creatorSoftwareLength; //offset 350
	
	@StructToken(order = 161) 
	public Short unknown18; //value 0
	
	@StructToken(order = 162) 
	public Short unknown19; //value 20
	
	@StructToken(order = 170)
	public Short maxLengthValid; // value of 8 indicates MXNAM and MXLAB valid := IMAXN
	
	@StructToken(order = 171) 
	public Short unknown20; //value 0
	
	@StructToken(order = 180)
	public Short compressionMethodOffset;
	
	@StructToken(order = 190)
	public Short compressionMethodLength;
	
	@StructToken(order = 191) 
	public Short unknown21; //value 0
	
	@StructToken(order = 192) 
	public Short unknown22; //value 12
	
	@StructToken(order = 193) 
	public Short unknown23; //value 8
	
	@StructToken(order = 194) 
	public Short unknown24; //value 0
	

	
	@StructToken(order = 200)
	public Short creatorProcOffset; //offset 374
	
	@StructToken(order = 210)
	public Short creatorProcLength; //offset 374

	
	@StructTokenPrefix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000000000000000000000000000000000000000000000000000",validate = false) 
	})
	@StructToken(order = 212) 
	public Short unknown26; //value 4
	
	@StructToken(order = 213) 
	public Short unknown27; //value 1
	

	@StructToken(order = 220)
	public Short columnTextHeadersCount; //offset 416
	
	@StructToken(order = 230)
	public Short columnNameMaxLength; //offset 418
	
	@StructToken(order = 240)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000",validate = false) 
	})
	public Short columnLabelMaxLength; //offset 420
	
	
	@StructToken(order = 250)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000000000000000000000000000000000",validate = false) 
	})
	public Short fullPageRowsCount; //offset 434
	
	
	@StructToken(order = 251)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000",validate = false) 
	})
	public Byte unknown28;
	
	

	public Short getCreatorSoftwareOffset() {
		return creatorSoftwareOffset;
	}



	public Short getCreatorSoftwareLength() {
		return creatorSoftwareLength;
	}



	public Short getCompressionMethodOffset() {
		return compressionMethodOffset;
	}



	public Short getCompressionMethodLength() {
		return compressionMethodLength;
	}



	public Short getCreatorProcOffset() {
		return creatorProcOffset;
	}



	public Short getCreatorProcLength() {
		return creatorProcLength;
	}



	public Short getUnknown14() {
		return unknown14;
	}



	public void setUnknown14(Short unknown14) {
		this.unknown14 = unknown14;
	}



	public Short getUnknown15() {
		return unknown15;
	}



	public void setUnknown15(Short unknown15) {
		this.unknown15 = unknown15;
	}



	public Short getUnknown16() {
		return unknown16;
	}



	public void setUnknown16(Short unknown16) {
		this.unknown16 = unknown16;
	}



	public Short getUnknown17() {
		return unknown17;
	}



	public void setUnknown17(Short unknown17) {
		this.unknown17 = unknown17;
	}



	public Short getUnknown18() {
		return unknown18;
	}



	public void setUnknown18(Short unknown18) {
		this.unknown18 = unknown18;
	}



	public Short getUnknown19() {
		return unknown19;
	}



	public void setUnknown19(Short unknown19) {
		this.unknown19 = unknown19;
	}



	public Short getUnknown20() {
		return unknown20;
	}



	public void setUnknown20(Short unknown20) {
		this.unknown20 = unknown20;
	}



	public Short getUnknown21() {
		return unknown21;
	}



	public void setUnknown21(Short unknown21) {
		this.unknown21 = unknown21;
	}



	public Short getUnknown22() {
		return unknown22;
	}



	public void setUnknown22(Short unknown22) {
		this.unknown22 = unknown22;
	}



	public Short getUnknown23() {
		return unknown23;
	}



	public void setUnknown23(Short unknown23) {
		this.unknown23 = unknown23;
	}



	public Short getUnknown24() {
		return unknown24;
	}



	public void setUnknown24(Short unknown24) {
		this.unknown24 = unknown24;
	}



	public Short getUnknown26() {
		return unknown26;
	}



	public void setUnknown26(Short unknown26) {
		this.unknown26 = unknown26;
	}



	public Short getUnknown27() {
		return unknown27;
	}



	public void setUnknown27(Short unknown27) {
		this.unknown27 = unknown27;
	}



	public Byte getUnknown28() {
		return unknown28;
	}



	public void setUnknown28(Byte unknown28) {
		this.unknown28 = unknown28;
	}



	public void setCreatorSoftwareOffset(Short creatorSoftwareOffset) {
		this.creatorSoftwareOffset = creatorSoftwareOffset;
	}



	public void setCreatorSoftwareLength(Short creatorSoftwareLength) {
		this.creatorSoftwareLength = creatorSoftwareLength;
	}



	public void setMaxLengthValid(Short maxLengthValid) {
		this.maxLengthValid = maxLengthValid;
	}



	public void setCompressionMethodOffset(Short compressionMethodOffset) {
		this.compressionMethodOffset = compressionMethodOffset;
	}



	public void setCompressionMethodLength(Short compressionMethodLength) {
		this.compressionMethodLength = compressionMethodLength;
	}



	public void setCreatorProcOffset(Short creatorProcOffset) {
		this.creatorProcOffset = creatorProcOffset;
	}



	public void setCreatorProcLength(Short creatorProcLength) {
		this.creatorProcLength = creatorProcLength;
	}



	public void setColumnTextHeadersCount(Short columnTextHeadersCount) {
		this.columnTextHeadersCount = columnTextHeadersCount;
	}



	public void setColumnNameMaxLength(Short columnNameMaxLength) {
		this.columnNameMaxLength = columnNameMaxLength;
	}



	public void setColumnLabelMaxLength(Short columnLabelMaxLength) {
		this.columnLabelMaxLength = columnLabelMaxLength;
	}



	public void setFullPageRowsCount(Short fullPageRowsCount) {
		this.fullPageRowsCount = fullPageRowsCount;
	}



	public Boolean getCompressed() {
		return compressionMethodLength > 0;
	}

	

	public abstract Long getRowLength();

	public abstract Long getRowCount();

	

	public abstract Long getDeletedRowCount();

	public abstract Long getColumnCountP1();

	public abstract Long getColumnCountP2();

	public abstract Long getPageSize();



	public abstract Long getMixedPageRowCount();

	public abstract Integer getPageSequence();


	public abstract Long getPagesWithSubHeadersCount();



	public abstract Long getPagesWithSubHeadersCountDuplicate();


	public abstract Long getPageCount();
	

	



	public Long getColumnCount() {
		return getColumnCountP1().longValue() + getColumnCountP2().longValue();
	}
	
	

}
