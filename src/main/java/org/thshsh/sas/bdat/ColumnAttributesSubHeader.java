package org.thshsh.sas.bdat;

import java.util.ArrayList;
import java.util.List;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class ColumnAttributesSubHeader extends SubHeader{
	
	public static final Struct<ColumnAttributesSubHeader> STRUCT = Struct.create(ColumnAttributesSubHeader.class);
	
	@StructToken(order=0)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Short,constant = "0",validate = false),
		@StructToken(type = TokenType.Short,constant = "0",validate = false),
		@StructToken(type = TokenType.Short,constant = "0",validate = false)
	})
	public Short remainingLength;
	
	protected List<ColumnAttributes> columnAttributes;

	public List<ColumnAttributes> getColumnAttributes() {
		if(columnAttributes == null) columnAttributes = new ArrayList<ColumnAttributes>();
		return columnAttributes;
	}

	public void setColumnAttributes(List<ColumnAttributes> columnNames) {
		this.columnAttributes = columnNames;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnAttributesSubHeader [remainingLength=");
		builder.append(remainingLength);
		builder.append(", columnAttributes=");
		builder.append(columnAttributes);
		builder.append("]");
		return builder.toString();
	}

}
