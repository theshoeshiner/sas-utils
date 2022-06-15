package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class PageHeader {
	
	public static final Struct<PageHeader> STRUCT = Struct.create(PageHeader.class);
	
	@StructToken(order = 0)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000",validate = false)}) //TODO needs to be 24 bytes for 64bit
	public Integer pageSequence;
	
	@StructToken(order = 1)
	protected Short pageTypeId;
	
	@StructToken(order = 2)
	protected Short blockCount;
	
	@StructToken(order = 3)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000",validate = false)}) 
	protected Short subHeaderCount;
	
	public PageType getPageType() {
		return PageType.fromId(pageTypeId.intValue());
	}

	public Short getPageTypeId() {
		return pageTypeId;
	}



	public void setPageTypeId(Short pageType) {
		this.pageTypeId = pageType;
	}



	public Short getBlockCount() {
		return blockCount;
	}



	public void setBlockCount(Short blockCount) {
		this.blockCount = blockCount;
	}



	public Short getSubHeaderCount() {
		return subHeaderCount;
	}



	public void setSubHeaderCount(Short subHeaderCount) {
		this.subHeaderCount = subHeaderCount;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageHeader [pageSequence=");
		builder.append(pageSequence);
		builder.append(", pageTypeId=");
		builder.append(pageTypeId);
		builder.append(", blockCount=");
		builder.append(blockCount);
		builder.append(", subHeaderCount=");
		builder.append(subHeaderCount);
		builder.append(", getPageType()=");
		builder.append(getPageType());
		builder.append("]");
		return builder.toString();
	}
	
	
	
}