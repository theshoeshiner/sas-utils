package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class SubHeaderPointer {
	
	public static final Struct<SubHeaderPointer> STRUCT = Struct.create(SubHeaderPointer.class);
	
	@StructToken(order=0) //TODO long when 64
	public Integer pageOffset;
	
	@StructToken(order=1)
	public Integer length; //TODO long when 64

	
	//protected byte[] prefix;
	
	@StructToken(order=2)
	public Byte compressionTypeId; //0=none,1=truncated(ignore data),4= compressed row data with control byte
	
	
	/**
	 * 0 Row Size, Column Size, Subheader Counts, Column Format and Label, in Uncompressed file
		1 Column Text, Column Names, Column Attributes, Column List
		1 all subheaders (including row data), in Compressed file.
	 */
	@StructToken(order=3)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000",validate = false)}) //TODO 6 bytes when 64
	public Byte categoryId;
	
	public SubHeaderSignature signature;

	public SubHeader subHeader;
	
	public Integer getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(Integer offset) {
		this.pageOffset = offset;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Byte getCompressionTypeId() {
		return compressionTypeId;
	}

	public void setCompressionTypeId(Byte compressionTypeId) {
		this.compressionTypeId = compressionTypeId;
	}
	
	public CompressionType getCompressionType() {
		return CompressionType.fromId(compressionTypeId.intValue());
	}

	/*public Byte getTypeId() {
		return typeId;
	}
	
	public void setTypeId(Byte typeId) {
		this.typeId = typeId;
	}*/

	public SubHeaderSignature getSignature() {
		return signature;
	}

	public void setSignature(SubHeaderSignature signature) {
		this.signature = signature;
	}

	public SubHeader getSubHeader() {
		return subHeader;
	}

	public void setSubHeader(SubHeader subHeader) {
		this.subHeader = subHeader;
	}
	
	

	public Byte getCategoryId() {
		return categoryId;
	}

	public void setCompressed(Byte compressed) {
		this.categoryId = compressed;
	}


	public SubHeaderCategory getCategory() {
		return SubHeaderCategory.fromId(categoryId.intValue());
	}

	



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubHeaderPointer [pageOffset=");
		builder.append(pageOffset);
		builder.append(", length=");
		builder.append(length);
		builder.append(", compressionTypeId=");
		builder.append(compressionTypeId);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", signature=");
		builder.append(signature);
		builder.append(", subHeader=");
		builder.append(subHeader);
		builder.append(", getCompressionType()=");
		builder.append(getCompressionType());
		builder.append(", getCategory()=");
		builder.append(getCategory());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	/*public static Byte COMPRESSED_SUBHEADER_TYPE = 1;
	
	public static Byte COMPRESSED_SUBHEADER_ID = 4;
	
	@StructToken(order=0,length = 2)
	protected byte[] prefix;
	
	@StructToken(order=3)
	protected Byte type;
	
	@StructToken(order=4)
	protected Byte compression;
	
	protected SubHeaderSignature signature;
	
	protected SubHeader subHeader;
	
	protected Number offset;
	
	protected Number length;
	
	
	public SubHeader getSubHeader() {
		return subHeader;
	}
	
	public void setSubHeader(SubHeader subHeader) {
		this.subHeader = subHeader;
	}
	
	public byte[] getPrefix() {
		return prefix;
	}
	
	public void setPrefix(byte[] prefix) {
		this.prefix = prefix;
	}
	
	public Byte getType() {
		return type;
	}
	
	public void setType(Byte type) {
		this.type = type;
	}
	
	public Byte getCompression() {
		return compression;
	}
	
	public void setCompression(Byte compression) {
		this.compression = compression;
	}
	
	public Number getOffset() {
		return offset;
	}
	
	public void setOffset(Number offset) {
		this.offset = offset;
	}
	
	public Number getLength() {
		return length;
	}
	
	public void setLength(Number length) {
		this.length = length;
	}
	
	public void setSignature(SubHeaderSignature signature) {
		this.signature = signature;
	}
	
	public SubHeaderSignature getSignature() {
		return signature;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubHeaderPointer [type=");
		builder.append(type);
		builder.append(", compression=");
		builder.append(compression);
		builder.append(", offset=");
		builder.append(offset);
		builder.append(", length=");
		builder.append(length);
		builder.append(", signature=");
		builder.append(signature);
		builder.append(", subHeader=");
		builder.append(subHeader);
		builder.append("]");
		return builder.toString();
	}
	*/
	
	
}
