package org.thshsh.sas.bdat;

import org.thshsh.sas.VariableType;
import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class ColumnAttributes {
	
	public static final Struct<ColumnAttributes> STRUCT = Struct.create(ColumnAttributes.class);
	
	@StructToken(order=0)
	public Integer offset;
	
	@StructToken(order=1)
	public Integer width;
	
	/*	@StructToken(order=2)
		public Integer length;*/
	
	@StructToken(order=3) 
	public Short nameLengthId;
	
	@StructToken(order=4)
	@StructTokenSuffix({@StructToken(type = TokenType.Byte,constant = "0",validate = false)})
	public Byte variableTypeId;

	

	public void setVariableTypeId(Byte variableType) {
		this.variableTypeId = variableType;
	}
	
	public VariableType getVariableType() {
		return VariableType.values()[variableTypeId-1];
	}

	/*public Byte getSkip2() {
		return skip2;
	}
	
	public void setSkip2(Byte skip2) {
		this.skip2 = skip2;
	}*/

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnAttributes [offset=");
		builder.append(offset);
		builder.append(", width=");
		builder.append(width);
		builder.append(", nameLengthId=");
		builder.append(nameLengthId);
		builder.append(", variableTypeId=");
		builder.append(variableTypeId);
		builder.append("]");
		return builder.toString();
	}
	
	

}
