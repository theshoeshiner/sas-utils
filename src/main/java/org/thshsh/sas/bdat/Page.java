package org.thshsh.sas.bdat;

import java.util.ArrayList;
import java.util.List;

public class Page {
	
	long startByte;
	/*PageType pageType;
	int blockCount;
	int subHeaderCount;*/
	
	PageHeader header;
	
	List<SubHeaderPointer> subHeaderPointers;
	
	public void setHeader(PageHeader header) {
		this.header = header;
	}

	public PageType getPageType() {
		return header.getPageType();
	}
	
	public int getBlockCount() {
		return header.blockCount;
	}
	
	public int getSubHeaderCount() {
		return header.subHeaderCount;
	}

	public List<SubHeaderPointer> getSubHeaderPointers() {
		if(subHeaderPointers == null) subHeaderPointers = new ArrayList<SubHeaderPointer>();
		return subHeaderPointers;
	}

	public void setSubHeaderPointers(List<SubHeaderPointer> subHeaderPointers) {
		this.subHeaderPointers = subHeaderPointers;
	}
	
	public Boolean hasObservations() {
		return getPageType() == PageType.Data && header.blockCount > 0;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[getPageType()=");
		builder.append(getPageType());
		builder.append(", getBlockCount()=");
		builder.append(getBlockCount());
		builder.append(", getSubHeaderCount()=");
		builder.append(getSubHeaderCount());
		builder.append(", startByte=");
		builder.append(startByte);
		builder.append(", subHeaderPointers=");
		builder.append(subHeaderPointers);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
