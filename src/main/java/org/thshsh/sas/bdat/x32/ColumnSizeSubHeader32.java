package org.thshsh.sas.bdat.x32;

import org.thshsh.sas.bdat.ColumnSizeSubHeader;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class ColumnSizeSubHeader32 extends ColumnSizeSubHeader {

	@StructToken(order = 0)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "00000000",validate = false)})
	public Integer numColumns;

	public Long getNumColumns() {
		return numColumns.longValue();
	}

	public void setNumColumns(Integer numColumns) {
		this.numColumns = numColumns;
	}
	
	
}
