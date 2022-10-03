package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class RowSizeSubHeader extends SubHeader {
	
	public static final Struct<RowSizeSubHeader> STRUCT = Struct.create(RowSizeSubHeader.class);
		
	
	@StructToken(offset=16)
	public Integer rowLength;
	
	@StructToken(offset = 20)
	public Integer rowCount;
	
	@StructToken(offset = 28)
	public Integer deletedRowCount;
	
	@StructToken(offset = 32)
	public Integer columnCountP1;
	
	@StructToken(offset = 36)
	public Integer columnCountP2;
	
	@StructToken(offset = 48)
	public Integer pageSize;
	
	@StructToken(offset = 56)
	public Integer mixedPageRowCount;
	
	@StructToken(offset = 216) //always equal to pageheader sig
	public Integer pageSequence;
	
	/*	@StructToken(offset =260) //value: 1 //offset 260
		public Integer unkown;
		
		@StructToken(offset =264)
		public Short unknown2;
		*/
	@StructToken(offset = 268)
	public Integer pagesWithSubHeadersCount; //offset 
	
	@StructToken(offset = 272)
	public Short lastPageSubHeadersCount;
	
	@StructToken(offset = 276)
	public Integer pagesWithSubHeadersCountDuplicate; //offset 276
	
	@StructToken(offset = 280)
	public Short numLastPageSubHeadersPlusTwo; //offset 
	
	@StructToken(offset = 284) //same has page count
	public Integer pageCount; //offset 284
	
	/*@StructToken(offset = 288) //values 22,26,9,56 observed
	public Short unknown3; //offset 288
	
	@StructToken(offset = 292) //value: 1 
	public Integer unknown4; //offset 292
	*/	
	
	@StructToken(offset = 348)
	public Short creatorSoftwareOffset; 
	
	@StructToken(offset = 350)
	public Short creatorSoftwareLength; //offset 350
	
	@StructToken(offset = 356)
	public Short maxLengthValid;
	
	@StructToken(offset = 360)
	public Short compressionMethodOffset;
	
	@StructToken(offset = 362)
	public Short compressionMethodLength;
	
	@StructToken(offset = 372)
	public Short creatorProcOffset; //offset 374
	
	@StructToken(offset = 374)
	public Short creatorProcLength; //offset 374
	
	@StructToken(offset = 416)
	public Short columnTextHeadersCount; //offset 416
	
	@StructToken(offset = 418)
	public Short columnNameMaxLength; //offset 418
	
	@StructToken(offset = 420)
	public Short columnLabelMaxLength; //offset 420
	
	@StructToken(offset = 434)
	public Short fullPageRowsCount; //offset 434
	

	public Long getColumnCount() {
		return columnCountP1.longValue() + columnCountP2.longValue();
	}
	
	public Boolean getCompressed() {
		return compressionMethodLength > 0;
	}
	
	


	public Integer getRowLength() {
		return rowLength;
	}

	public void setRowLength(Integer rowLength) {
		this.rowLength = rowLength;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RowSizeSubHeader [rowLength=");
		builder.append(rowLength);
		builder.append(", rowCount=");
		builder.append(rowCount);
		builder.append(", deletedRowCount=");
		builder.append(deletedRowCount);
		builder.append(", columnCountP1=");
		builder.append(columnCountP1);
		builder.append(", columnCountP2=");
		builder.append(columnCountP2);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", mixedPageRowCount=");
		builder.append(mixedPageRowCount);
		builder.append(", pageSequence=");
		builder.append(pageSequence);
		builder.append(", pagesWithSubHeadersCount=");
		builder.append(pagesWithSubHeadersCount);
		builder.append(", lastPageSubHeadersCount=");
		builder.append(lastPageSubHeadersCount);
		builder.append(", pagesWithSubHeadersCountDuplicate=");
		builder.append(pagesWithSubHeadersCountDuplicate);
		builder.append(", numLastPageSubHeadersPlusTwo=");
		builder.append(numLastPageSubHeadersPlusTwo);
		builder.append(", pageCount=");
		builder.append(pageCount);
		builder.append(", creatorSoftwareOffset=");
		builder.append(creatorSoftwareOffset);
		builder.append(", creatorSoftwareLength=");
		builder.append(creatorSoftwareLength);
		builder.append(", maxLengthValid=");
		builder.append(maxLengthValid);
		builder.append(", compressionMethodOffset=");
		builder.append(compressionMethodOffset);
		builder.append(", compressionMethodLength=");
		builder.append(compressionMethodLength);
		builder.append(", creatorProcOffset=");
		builder.append(creatorProcOffset);
		builder.append(", creatorProcLength=");
		builder.append(creatorProcLength);
		builder.append(", columnTextHeadersCount=");
		builder.append(columnTextHeadersCount);
		builder.append(", columnNameMaxLength=");
		builder.append(columnNameMaxLength);
		builder.append(", columnLabelMaxLength=");
		builder.append(columnLabelMaxLength);
		builder.append(", fullPageRowsCount=");
		builder.append(fullPageRowsCount);
		builder.append("]");
		return builder.toString();
	}
	
	
	/*	//TODO both need to double for u64
		@StructTokenPrefix({@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000",validate = false)}) //offset 0
		@StructToken(order = -2) 
		public Integer rowLength; //offset 16
		
		
		@StructToken(order = 1)
		@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)}) //offset 24
		public Integer rowCount;  //offset 20
	
		
		@StructToken(order = 2)
		public Integer columnCountP1; //offset 32
		
		@StructToken(order = 3)
		@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)}) //offset 40
		public Integer columnCountP2; //offset 36
		
		@StructToken(order = 4)
		@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "00000000",validate = false)}) //offset 52
		public Integer pageSize; //offset 48
		
		//public byte[] skip3;
		@StructToken(order = 5)
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "FFFFFFFFFFFFFFFF"), //offset 60
			@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"), //offset 68
			})
		public Integer mixedPageRowCount; //offset 56
	
		@StructToken(order = 6) //always equal to pageheader sig
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000000000000000000000000000000000000000000000000000"), //offset 220
		})
		public Integer pageSequence; //offset 216
		
		
		@StructToken(order = 7) //value: 1 //offset 260
		public Integer unkown;
		
		@StructToken(order = 8) //value: 2
		@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000",validate = false)}) //offset 266
		public Short unknown2; //offset 264
		
		//int, number of pages with subheader data
		@StructToken(order = 9)
		public Integer pagesWithSubHeadersCount; //offset 268
		
		//int, number of subheaders with positive length on last page with subheader data
		@StructToken(order = 10)
		@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000",validate = false)}) //offset 274
		public Short lastPageSubHeadersCount; //offset 272
		
		@StructToken(order = 11)
		public Integer pagesWithSubHeadersCountDuplicate; //offset 276
		
		@StructToken(order = 12)
		@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000",validate = false)}) //offset 282
		public Short numLastPageSubHeadersPlusTwo; //offset 280
		
		@StructToken(order = 13) //same has page count
		public Integer pageCount; //offset 284
		
		@StructToken(order = 14) //values 22,26,9,56 observed
		@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000",validate = false)}) //offset 290
		public Short unknown3; //offset 288
		
		@StructToken(order = 15) //value: 1 
		public Integer unknown4; //offset 292
		
		@StructToken(order = 16) //value: 7, 8
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "0000",validate = false), //offset 298 
			@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000000000000000000000000000000000000000000000000000"), //offset 300
			@StructToken(type = TokenType.Bytes,constant = "0000",validate = false) //offset 340
		})
		public Short unknown5; //offset 296
	
		
		@StructToken(order = 17) //values 0,8
		@StructTokenSuffix({
			@StructToken(type = TokenType.Short,constant = "4"), //offset 344
			@StructToken(type = TokenType.Short,constant = "0") //offset 346
		})
		public Short unknown6; //offset 342
		
		@StructToken(order = 18) //values 12,32,0
		public Short unknown7; //offset 348
		
		@StructToken(order = 19)
		@StructTokenSuffix({
			@StructToken(type = TokenType.Short,constant = "0"), //offset 352
			@StructToken(type = TokenType.Short,constant = "20",validate = false) //offset 354
		})
		public Short creatorSoftwareLength; //offset 350
		
	
		
		@StructToken(order = 22)
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "0000000000000000"), //offset 358
			@StructToken(type = TokenType.Short,constant = "12"), //offset 366
			@StructToken(type = TokenType.Short,constant = "8"), //offset 368
			@StructToken(type = TokenType.Short,constant = "0"), //offset 370
			@StructToken(type = TokenType.Short,constant = "28"), //offset 372
		})
		public Short maxLengthValid; //offset 356
	
		
		@StructToken(order = 24)
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000000000000000000000000000000000000000000000000000"), //offset 376
			@StructToken(type = TokenType.Short,constant = "4"), //offset 412
			@StructToken(type = TokenType.Short,constant = "1"),//offset 414
		})
		public Short creatorProcLength; //offset 374
		
		@StructToken(order = 26)
		public Short columnTextHeadersCount; //offset 416
		
		@StructToken(order = 28)
		public Short columnNameMaxLength; //offset 418
		
		@StructToken(order = 30)
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000"), //offset 422
		})
		public Short columnLabelMaxLength; //offset 420
		
		@StructToken(order = 32)
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000000000000000000000000000000000",validate = false), //offset 436
		})
		public Short fullPageRowsCount; //offset 434 
		
		@StructToken(order = 34) //values 1,5
		@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000"), //offset 464
		})
		public Byte unknown8; //offset 463
		
		
		
		
		
		public Long getColumnCount() {
			return columnCountP1.longValue() + columnCountP2.longValue();
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
			builder.append(", pageSize=");
			builder.append(pageSize);
			builder.append(", mixedPageRowCount=");
			builder.append(mixedPageRowCount);
			builder.append(", pageSequence=");
			builder.append(pageSequence);
			builder.append(", unkown=");
			builder.append(unkown);
			builder.append(", unknown2=");
			builder.append(unknown2);
			builder.append(", pagesWithSubHeadersCount=");
			builder.append(pagesWithSubHeadersCount);
			builder.append(", lastPageSubHeadersCount=");
			builder.append(lastPageSubHeadersCount);
			builder.append(", pagesWithSubHeadersCountDuplicate=");
			builder.append(pagesWithSubHeadersCountDuplicate);
			builder.append(", numLastPageSubHeadersPlusTwo=");
			builder.append(numLastPageSubHeadersPlusTwo);
			builder.append(", pageCount=");
			builder.append(pageCount);
			builder.append(", unknown3=");
			builder.append(unknown3);
			builder.append(", unknown4=");
			builder.append(unknown4);
			builder.append(", unknown5=");
			builder.append(unknown5);
			builder.append(", unknown6=");
			builder.append(unknown6);
			builder.append(", unknown7=");
			builder.append(unknown7);
			builder.append(", creatorSoftwareLength=");
			builder.append(creatorSoftwareLength);
			builder.append(", maxLengthValid=");
			builder.append(maxLengthValid);
			builder.append(", creatorProcLength=");
			builder.append(creatorProcLength);
			builder.append(", columnTextHeadersCount=");
			builder.append(columnTextHeadersCount);
			builder.append(", columnNameMaxLength=");
			builder.append(columnNameMaxLength);
			builder.append(", columnLabelMaxLength=");
			builder.append(columnLabelMaxLength);
			builder.append(", fullPageRowsCount=");
			builder.append(fullPageRowsCount);
			builder.append(", unknown8=");
			builder.append(unknown8);
			builder.append("]");
			return builder.toString();
		}
		*/
	
	
}
