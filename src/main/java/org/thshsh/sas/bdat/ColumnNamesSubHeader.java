package org.thshsh.sas.bdat;

import java.util.ArrayList;
import java.util.List;

import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class ColumnNamesSubHeader extends SubHeader {
	
	@StructToken(order=0)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Short,constant = "0",validate = false),
		@StructToken(type = TokenType.Short,constant = "0",validate = false),
		@StructToken(type = TokenType.Short,constant = "0",validate = false)
	})
	public Short remainingLength;
	
	protected List<ColumnName> columnNames;

	public List<ColumnName> getColumnNames() {
		if(columnNames == null) columnNames = new ArrayList<ColumnName>();
		return columnNames;
	}

	public void setColumnNames(List<ColumnName> columnNames) {
		this.columnNames = columnNames;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnNamesSubHeader [remainingLength=");
		builder.append(remainingLength);
		builder.append(", columnNames=");
		builder.append(columnNames);
		builder.append("]");
		return builder.toString();
	}
	
	

}
