package org.thshsh.sas.bdat.x64;

import org.thshsh.sas.bdat.ColumnSizeSubHeader;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class ColumnSizeSubHeader64 extends ColumnSizeSubHeader {
	
	@StructToken(order = 0)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)})
	public Long numColumns;

	public Long getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(Long numColumns) {
		this.numColumns = numColumns;
	}
	
	

}
