package org.thshsh.sas.bdat;

import java.time.LocalDateTime;

import org.thshsh.sas.SasConstants;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public abstract class Header3 {

	@StructToken(order = 1)
	public Double createdTimestamp;
	
	@StructToken(order = 2)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "00000000000000000000000000000000",validate = false)})
	public Double modifiedTimestamp;
	
	@StructToken(order = 4)
	public Integer headerSize;
	
	@StructToken(order = 5)
	public Integer pageSize;
	

	public Header3() {}
	
	
	public Double getCreatedTimestamp() {
		return createdTimestamp;
	}



	public void setCreatedTimestamp(Double created) {
		this.createdTimestamp = created;
	}



	public Double getModifiedTimestamp() {
		return modifiedTimestamp;
	}



	public void setModifiedTimestamp(Double modified) {
		this.modifiedTimestamp = modified;
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

	public abstract Long getPageCount();

	/*public Integer getPageCount() {
		return pageCount;
	}
	
	
	
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}*/


	public LocalDateTime getCreated() {
		return SasConstants.toDateTime(createdTimestamp);
	}
	
	public LocalDateTime getModified() {
		return SasConstants.toDateTime(modifiedTimestamp);
	}



	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header3 [created=");
		builder.append(getCreated());
		builder.append(", modified=");
		builder.append(getModified());
		builder.append(", headerSize=");
		builder.append(headerSize);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageCount=");
		builder.append(getPageCount());
		builder.append("]");
		return builder.toString();
	}
	
	
}