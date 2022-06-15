package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class RowSizeSubHeader extends SubHeader {
	
	public static final Struct<RowSizeSubHeader> STRUCT = Struct.create(RowSizeSubHeader.class);
		
	//TODO both need to double for u64
	@StructTokenPrefix({@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000",validate = false)})
	@StructToken(order = 0)
	public Integer rowLength;
	
	@StructToken(order = 1)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)})
	public Integer rowCount;
	
	@StructToken(order = 2)
	public Integer columnCountP1;
	
	@StructToken(order = 3)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)})
	public Integer columnCountP2;
	
	@StructToken(order = 4)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "00000000",validate = false)})
	public Integer pageSize;
	
	//public byte[] skip3;
	@StructToken(order = 5)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "FFFFFFFFFFFFFFFF"),
		@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
		})
	public Integer mixedPageRowCount;

	@StructToken(order = 6) //always equal to pageheader sig
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000000000000000000000000000000000000000000000000000"),
	})
	public Integer pageSequence;
	
	
	@StructToken(order = 7) //value: 1 
	public Integer unkown;
	
	@StructToken(order = 8) //value: 2
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000")})
	public Short unknown2;
	
	//int, number of pages with subheader data
	@StructToken(order = 9)
	public Integer pagesWithSubHeadersCount;
	
	//int, number of subheaders with positive length on last page with subheader data
	@StructToken(order = 10)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000")})
	public Short lastPageSubHeadersCount;
	
	@StructToken(order = 11)
	public Integer pagesWithSubHeadersCountDuplicate;
	
	@StructToken(order = 12)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000")})
	public Short numLastPageSubHeadersPlusTwo;
	
	@StructToken(order = 13) //same has page count
	public Integer pageCount;
	
	@StructToken(order = 14) //values 22,26,9,56 observed
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000")})
	public Short unknown3;
	
	@StructToken(order = 15) //value: 1 
	public Integer unknown4;
	
	@StructToken(order = 16) //value: 7, 8
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "0000"),
		@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000000000000000000000000000000000000000000000000000"),
		@StructToken(type = TokenType.Bytes,constant = "0000")
	})
	public Short unknown5;
	
	@StructToken(order = 17) //values 0,8
	@StructTokenSuffix({
		@StructToken(type = TokenType.Short,constant = "4"),
		@StructToken(type = TokenType.Short,constant = "0")
	})
	public Short unknown6;
	
	@StructToken(order = 18) //values 12,32,0
	public Short unknown7;
	
	@StructToken(order = 19)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Short,constant = "0"),
		@StructToken(type = TokenType.Short,constant = "20")
	})
	public Short creatorSoftwareLength;
	
	@StructToken(order = 20)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "0000000000000000"),
		@StructToken(type = TokenType.Short,constant = "12"),
		@StructToken(type = TokenType.Short,constant = "8"),
		@StructToken(type = TokenType.Short,constant = "0"),
		@StructToken(type = TokenType.Short,constant = "28"),
	})
	public Short maxLengthValid;
	
	@StructToken(order = 21)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000000000000000000000000000000000000000000000000000"),
		@StructToken(type = TokenType.Short,constant = "4"),
		@StructToken(type = TokenType.Short,constant = "1"),
	})
	public Short creatorProcLength;
	
	@StructToken(order = 22)
	public Short columnTextHeadersCount;
	
	@StructToken(order = 23)
	public Short columnNameMaxLength;
	
	@StructToken(order = 24)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000"),
	})
	public Short columnLabelMaxLength;
	
	@StructToken(order = 25)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000000000000000000000000000000000",validate = false),
	})
	public Short fullPageRowsCount;
	
	@StructToken(order = 26) //values 1,5
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000"),
	})
	public Byte unknown8;
	
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
	
	
	
}
