package org.thshsh.sas.bdat;

import org.thshsh.sas.bdat.DatasetBdat.SubHeaderSignature;
import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;

public abstract class SubHeaderPointer {
	
	public static Byte COMPRESSED_SUBHEADER_TYPE = 1;

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
	
	
	
}
