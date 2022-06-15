package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class Header2 {
	
	public static final Struct<Header2> STRUCT = Struct.create(Header2.class);
	
	@StructToken(order = 1)
	public Double created;
	
	@StructToken(order = 2)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000",validate = false)})
	public Double modified;
	
	//@StructToken(order = 3,length = 16)
	//public byte[] skip;
	
	@StructToken(order = 4)
	public Integer headerSize;
	
	@StructToken(order = 5)
	public Integer pageSize;
	
	//TODO needs to be 8 byte Long for 64 bit
	@StructToken(order = 6)
	public Integer pageCount;

	
	
	public Double getCreated() {
		return created;
	}



	public void setCreated(Double created) {
		this.created = created;
	}



	public Double getModified() {
		return modified;
	}



	public void setModified(Double modified) {
		this.modified = modified;
	}



	/*public byte[] getSkip() {
		return skip;
	}
	
	
	
	public void setSkip(byte[] skip) {
		this.skip = skip;
	}
	*/


	public Integer getHeaderSize() {
		return headerSize;
	}



	public void setHeaderSize(Integer headerSize) {
		this.headerSize = headerSize;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}



	public Integer getPageCount() {
		return pageCount;
	}



	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header2 [created=");
		builder.append(created);
		builder.append(", modified=");
		builder.append(modified);
		builder.append(", headerSize=");
		builder.append(headerSize);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageCount=");
		builder.append(pageCount);
		builder.append("]");
		return builder.toString();
	}
	
	
}