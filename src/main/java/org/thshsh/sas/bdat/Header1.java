package org.thshsh.sas.bdat;

import org.thshsh.struct.Struct;
import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

@StructEntity(trimAndPad = true)
public class Header1 {
	
	public static final Struct<Header1> STRUCT = Struct.create(Header1.class);

	static byte U64_BYTE_CHECKER_VALUE = 51;
	
	static byte ALIGN_1_CHECKER_VALUE = 51;

	static int ALIGN_1_DEFAULT = 4;
	static int ALIGN_2_DEFAULT = 4;
	
	@StructTokenPrefix({@StructToken(type = TokenType.Bytes,validate=false,constant = "000000000000000000000000c2ea8160b31411cfbd92080009c7318c181f1011")})
	@StructToken(order=1)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000",validate = false)})
	public Byte align1;
	

	@StructToken(order=3)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "00",validate = false)})
	public Byte align2;
	

	@StructToken(order=5)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "00",validate = false)})
	public Boolean littleEndian;
	

	@StructToken(order=7,length = 1)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false),
		@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false),
		@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false), //supposedly a repeat of first 8 bytes
		@StructToken(type = TokenType.Bytes,constant = "000000000000",validate = false)
		})
	public String platform;
	

	@StructToken(order = 8)
	@StructTokenSuffix({
		@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000",validate = false),
		@StructToken(type = TokenType.String,constant = "SAS FILE")
	})
	public Short encoding;
	
	@StructToken(order=9,length = 64)
	public String datasetName;
	
	@StructToken(order=10,length = 8)
	public String fileType;
	
	

	public Byte getAlign1() {
		return align1;
	}

	public void setAlign1(Byte align1) {
		this.align1 = align1;
	}


	public Byte getAlign2() {
		return align2;
	}

	public void setAlign2(Byte align2) {
		this.align2 = align2;
	}

	public Boolean getLittleEndian() {
		return littleEndian;
	}

	public void setLittleEndian(Boolean littleEndian) {
		this.littleEndian = littleEndian;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
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


	public Boolean get64Bit() {
		return align1 == U64_BYTE_CHECKER_VALUE;
	}
	
	public Integer getIntegerLength() {
		return get64Bit() ? 8 : 4;
	}

	public int getHeader1Padding() {
		return (align2 == ALIGN_1_CHECKER_VALUE) ? ALIGN_1_DEFAULT : 0;
	}

	public int getHeader2Padding() {
		return (align1 == U64_BYTE_CHECKER_VALUE) ? ALIGN_2_DEFAULT : 0;
	}
	
	public int getSubHeaderPointerLength() {
		return get64Bit() ? 24 : 12;
	}

	public TokenType getIntegerTokenType() {
		return get64Bit() ? TokenType.Long : TokenType.Integer;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header1 [align1=");
		builder.append(align1);
		builder.append(", align2=");
		builder.append(align2);
		builder.append(", littleEndian=");
		builder.append(littleEndian);
		builder.append(", platform=");
		builder.append(platform);
		builder.append(", encoding=");
		builder.append(encoding);
		builder.append(", datasetName=");
		builder.append(datasetName);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", 64Bit=");
		builder.append(get64Bit());
		builder.append(", getHeader1Padding=");
		builder.append(getHeader1Padding());
		builder.append(", getHeader1Padding=");
		builder.append(getHeader2Padding());
		builder.append("]");
		return builder.toString();
	}
	
	
}