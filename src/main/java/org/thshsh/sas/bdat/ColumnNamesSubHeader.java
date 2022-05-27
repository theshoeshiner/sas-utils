package org.thshsh.sas.bdat;

import java.util.ArrayList;
import java.util.List;

public class ColumnNamesSubHeader extends SubHeader {
	
	protected List<ColumnName> columnNames;

	public List<ColumnName> getColumnNames() {
		if(columnNames == null) columnNames = new ArrayList<ColumnName>();
		return columnNames;
	}

	public void setColumnNames(List<ColumnName> columnNames) {
		this.columnNames = columnNames;
	}
	
	

}
