package org.thshsh.sas.bdat;

public class SubHeader {

	protected SubHeaderPointer pointer;
	protected DatasetBdat dataset;

	public DatasetBdat getDataset() {
		return dataset;
	}

	public void setDataset(DatasetBdat dataset) {
		this.dataset = dataset;
	}

	public SubHeaderPointer getPointer() {
		return pointer;
	}

	public void setPointer(SubHeaderPointer pointer) {
		this.pointer = pointer;
		pointer.setSubHeader(this);
	}
	
	
}
