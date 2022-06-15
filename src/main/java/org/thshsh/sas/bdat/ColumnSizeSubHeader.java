package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class ColumnSizeSubHeader {

	@StructToken(order = 0)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)})
	public Integer numColumns;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnSizeSubHeader [numColumns=");
		builder.append(numColumns);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
