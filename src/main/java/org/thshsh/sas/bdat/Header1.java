package org.thshsh.sas.bdat;

import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;

@StructEntity(trimAndPad = true)
public class Header1 {
	
	@StructToken(order=0,length = 32)
	public byte[] magic;
	
	@StructToken(order=1)
	public Byte align1;
	
	@StructToken(order=2, length = 2)
	public byte[] skip1;
	
	@StructToken(order=3)
	public Byte align2;
	
	@StructToken(order=4)
	public Byte skip2;
	
	@StructToken(order=5)
	public Boolean littleEndian;
	
	@StructToken(order=6)
	public Byte skip3;
	
	@StructToken(order=7,length = 1)
	public String platform;
	
	@StructToken(order=8,length = 52)
	public byte[] skip4;
	
	@StructToken(order=9,length = 64)
	public String datasetName;
	
	@StructToken(order=10,length = 8)
	public String fileType;
	
	

	public byte[] getMagic() {
		return magic;
	}

	public void setMagic(byte[] magic) {
		this.magic = magic;
	}

	public Byte getAlign1() {
		return align1;
	}

	public void setAlign1(Byte align1) {
		this.align1 = align1;
	}

	public byte[] getSkip1() {
		return skip1;
	}

	public void setSkip1(byte[] skip1) {
		this.skip1 = skip1;
	}

	public Byte getAlign2() {
		return align2;
	}

	public void setAlign2(Byte align2) {
		this.align2 = align2;
	}

	public Byte getSkip2() {
		return skip2;
	}

	public void setSkip2(Byte skip2) {
		this.skip2 = skip2;
	}

	public Boolean getLittleEndian() {
		return littleEndian;
	}

	public void setLittleEndian(Boolean littleEndian) {
		this.littleEndian = littleEndian;
	}

	public Byte getSkip3() {
		return skip3;
	}

	public void setSkip3(Byte skip3) {
		this.skip3 = skip3;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public byte[] getSkip4() {
		return skip4;
	}

	public void setSkip4(byte[] skip4) {
		this.skip4 = skip4;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header1 [magic=");
		//builder.append(Arrays.toString(magic));
		builder.append(magic);
		builder.append(", align1=");
		builder.append(align1);
		builder.append(", skip1=");
		builder.append(skip1);
		builder.append(", align2=");
		builder.append(align2);
		builder.append(", skip2=");
		builder.append(skip2);
		builder.append(", littleEndian=");
		builder.append(littleEndian);
		builder.append(", skip3=");
		builder.append(skip3);
		builder.append(", platform=");
		builder.append(platform);
		builder.append(", skip4=");
		builder.append(skip4);
		builder.append(", datasetName=");
		builder.append(datasetName);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append("]");
		return builder.toString();
	}
	
	
}