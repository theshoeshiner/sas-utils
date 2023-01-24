package org.thshsh.sas.bdat;

import java.time.LocalDateTime;

import org.thshsh.sas.SasConstants;
import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

@StructEntity(trimAndPad=true)
public class Header4 {

	
	@StructTokenPrefix({@StructToken(type = TokenType.Bytes,constant = "0000000000000000",validate = false)})
	@StructToken(order = 2,length = 8)
	public String sasRelease;
	
	@StructToken(order = 3,length = 16)
	public String sasServer;
	
	@StructToken(order = 4,length = 16)
	public String osVersion;
	
	@StructToken(order = 5,length = 16)
	public String osVendor;
	
	@StructToken(order = 6,length = 16)
	@StructTokenSuffix({
			@StructToken(type = TokenType.Bytes,constant = "0000000000000000000000000000000000000000000000000000000000000000",validate = false),
			@StructToken(type = TokenType.Bytes,constant = "00000000",validate = false), //TODO int, page sequence signature? (value is close to the value at start of each Page	Offset Table)
			@StructToken(type = TokenType.Bytes,constant = "00000000",validate = false) 
	})
	public String osName;
	
	
	@StructToken(order = 7)
	public Double otherTimestamp;



	public String getSasRelease() {
		return sasRelease;
	}



	public void setSasRelease(String sasRelease) {
		this.sasRelease = sasRelease;
	}



	public String getSasServer() {
		return sasServer;
	}



	public void setSasServer(String sasServer) {
		this.sasServer = sasServer;
	}



	public String getOsVersion() {
		return osVersion;
	}



	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}



	public String getOsVendor() {
		return osVendor;
	}



	public void setOsVendor(String osVendor) {
		this.osVendor = osVendor;
	}



	public String getOsName() {
		return osName;
	}



	public void setOsName(String osName) {
		this.osName = osName;
	}



	public LocalDateTime getTimestamp() {
		return SasConstants.toDateTime(otherTimestamp);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header4 [sasRelease=");
		builder.append(sasRelease);
		builder.append(", sasServer=");
		builder.append(sasServer);
		builder.append(", osVersion=");
		builder.append(osVersion);
		builder.append(", osVendor=");
		builder.append(osVendor);
		builder.append(", osName=");
		builder.append(osName);
		builder.append(", timestamp=");
		builder.append(getTimestamp());
		builder.append("]");
		return builder.toString();
	}
	
	
}