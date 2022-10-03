package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.TokenType;

@Deprecated
public class Compression {
	
	
	public static final Struct<Compression> STRUCT = Struct.create(Compression.class);
	
	@StructTokenPrefix({
		@StructToken(type = TokenType.Bytes,constant = "00000000", validate = false)
		})
	@StructToken(order = 0,length = 16)
	public String compression;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Compression [compression=");
		builder.append(compression);
		builder.append("]");
		return builder.toString();
	} 
	

}
