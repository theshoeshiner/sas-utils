package org.thshsh.sas.bdat;

public abstract class ColumnSizeSubHeader extends SubHeader {
	
	public abstract Long getNumColumns();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnSizeSubHeader [getNumColumns()=");
		builder.append(getNumColumns());
		builder.append("]");
		return builder.toString();
	}

	/*@StructToken(order = 0)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)})
	public Integer numColumns;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnSizeSubHeader [numColumns=");
		builder.append(numColumns);
		builder.append("]");
		return builder.toString();
	}*/
	
	
	
}
