package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class RowSizeSubHeader2 {

	//public static final Struct<RowSizeSubHeader2> STRUCT = Struct.create(RowSizeSubHeader2.class);
	
	@StructToken(offset=16)
	public Integer rowLength;
	
	@StructToken(offset = 20)
	public Integer rowCount;
	
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
	
	@StructToken(offset =260) //value: 1 //offset 260
	public Integer unkown;
	
	@StructToken(offset =264)
	public Short unknown2;
	
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
	
	@StructToken(offset = 288) //values 22,26,9,56 observed
	public Short unknown3; //offset 288
	
	@StructToken(offset = 292) //value: 1 
	public Integer unknown4; //offset 292
	
	@StructToken(offset = 350)
	public Short creatorSoftwareLength; //offset 350
	
	@StructToken(offset = 356)
	public Short maxLengthValid;
	
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
}
