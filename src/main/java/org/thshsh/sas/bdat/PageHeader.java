package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;

public class PageHeader {
	
	@StructToken(order = 1)
	protected Short pageTypeId;
	
	@StructToken(order = 2)
	protected Short blockCount;
	
	@StructToken(order = 3)
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
		builder.append("PageHeader [blockCount=");
		builder.append(blockCount);
		builder.append(", subHeaderCount=");
		builder.append(subHeaderCount);
		builder.append(", getPageType()=");
		builder.append(getPageType());
		builder.append("]");
		return builder.toString();
	}
	
	
	
}