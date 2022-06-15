package org.thshsh.sas.bdat;

import org.thshsh.sas.Format;
import org.thshsh.sas.Variable;
import org.thshsh.sas.VariableType;

public class VariableBdat extends Variable {
	
	protected FormatAndLabelSubHeader formatAndLabel;
	protected ColumnName columnName;
	protected ColumnAttributes attributes;
	
	public VariableBdat(FormatAndLabelSubHeader formatAndLabel, ColumnName columnName, ColumnAttributes atts) {
		super();
		this.formatAndLabel = formatAndLabel;
		this.columnName = columnName;
		this.attributes = atts;
	}

	@Override
	public String getName() {
		return columnName.getName();
	}

	public Integer getLength() {
		return attributes.width.intValue();
	}
	
	public VariableType getType() {
		return attributes.getVariableType();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VariableBdat [formatAndLabel=");
		builder.append(formatAndLabel);
		builder.append(", columnName=");
		builder.append(columnName);
		builder.append(", attributes=");
		builder.append(attributes);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public Format getFormat() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
