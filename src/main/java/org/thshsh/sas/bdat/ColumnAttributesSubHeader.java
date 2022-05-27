package org.thshsh.sas.bdat;

import java.util.ArrayList;
import java.util.List;

public class ColumnAttributesSubHeader extends SubHeader{
	
	protected List<ColumnAttributes> columnAttributes;

	public List<ColumnAttributes> getColumnAttributes() {
		if(columnAttributes == null) columnAttributes = new ArrayList<ColumnAttributes>();
		return columnAttributes;
	}

	public void setColumnAttributes(List<ColumnAttributes> columnNames) {
		this.columnAttributes = columnNames;
	}

}
