package org.thshsh.sas.bdat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Page {
	
	DatasetBdat dataset;
	long startByte;
	PageHeader header;
	List<SubHeaderPointer> subHeaderPointers;
	
	public Page(DatasetBdat dataset) {
		this.dataset = dataset;
	}
	
	public void setHeader(PageHeader header) {
		this.header = header;
	}

	public PageType getPageType() {
		return header.getPageType();
	}
	
	public long getBlockCount() {
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
	
	public Stream<SubHeaderPointer> getDataSubHeaderPointers(){
		return getSubHeaderPointers().stream().filter(shp -> shp.getSignature() == SubHeaderSignature.Data);
	}
	
	public Boolean hasObservations() {
		return getPageType() == PageType.Data && header.blockCount > 0;
	}

	
	public Long getTotalObservationCount() {
		return getBlockObservationCount() + getHeaderObservationCount();
	}
	
	public Long getBlockObservationCount() {
		
		if (getPageType().mixed()) return Math.min(dataset.rowSizeSubHeader.getMixedPageRowCount(), dataset.rowSizeSubHeader.getRowCount());
		else if (getPageType().data) return getBlockCount();
		return 0l;
	}
	
	public Integer getHeaderObservationCount() {
		
		//LOGGER.info("shps: {}",page.getSubHeaderPointers().size());
		int count = (int)getDataSubHeaderPointers().count();
		//LOGGER.debug("getHeaderObservationCount: {} = {}",page,count);
		//LOGGER.info("data sub headers: {}",count);
		return count;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		//builder.append("[getPageType()=");
		//builder.append(getPageType());
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
