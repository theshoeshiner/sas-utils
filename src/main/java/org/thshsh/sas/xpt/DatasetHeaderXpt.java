package org.thshsh.sas.xpt;

import java.time.LocalDateTime;

import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

/**
 * This includes the MEMBER header and the NAMESTR header
 *
 */
@StructEntity(charset = LibraryXpt.METADATA_CHARSET_NAME,trimAndPad = true)
public class DatasetHeaderXpt  {

	public static final String SPACES_2 = "  ";

	public static final String DATASET_HEADER_STRING =    XptConstants.HEADER_TAG+ "MEMBER  HEADER RECORD!!!!!!!00000000000000000160000000";
	
	public static final String DESCRIPTOR_HEADER_STRING = XptConstants.HEADER_TAG+ "DSCRPTR HEADER RECORD!!!!!!!000000000000000000000000000000  SAS     ";
	
	public static final String VARIABLES_HEADER_STRING =  XptConstants.HEADER_TAG+ "NAMESTR HEADER RECORD!!!!!!!000000";
	
	public static final String VARIABLES_FOOTER = "00000000000000000000  ";
	
	public static final String SAS_TYPE = "SASDATA ";

	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = DATASET_HEADER_STRING)})
	@StructToken(order=1,length=4)
	@StructTokenSuffix({@StructToken(type=TokenType.String,constant = SPACES_2)})
	public String variableDescriptorSize;
	
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = DESCRIPTOR_HEADER_STRING)})
	@StructToken(order=3,length=8)
	public String name;
	
	@StructToken(order=4,constant = SAS_TYPE)
	public String sasType;
	
	@StructToken(order=5,length=8)
	public String version;
	
	@StructToken(order=6,length=8)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant =XptConstants.SPACES_24)})
	public String os;
	
	@StructToken(order=7,length=16)
	public String createdString;
	
	@StructToken(order=8,length=16)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant = XptConstants.SPACES_16)})
	public String modifiedString;
	
	@StructToken(order=9,length=40)
	public String label;
	
	//have only ever seen blank
	@StructToken(order=10,length=8)
	public String type;

	
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = VARIABLES_HEADER_STRING)})
	@StructToken(order=12,length=4)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant = VARIABLES_FOOTER)})
	public String variableCountString;
	
	
	
	

	public String getVariableDescriptorSize() {
		return variableDescriptorSize;
	}

	public void setVariableDescriptorSize(String descriptorSizeString) {
		this.variableDescriptorSize = descriptorSizeString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCreatedString() {
		return createdString;
	}

	public void setCreatedString(String createdString) {
		this.createdString = createdString;
	}

	public String getModifiedString() {
		return modifiedString;
	}

	public void setModifiedString(String modifiedString) {
		this.modifiedString = modifiedString;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVariableCountString() {
		return variableCountString;
	}

	public Integer getVariableCount() {
		return Integer.valueOf(variableCountString);
	}
	
	public Boolean getDescriptor140() {
		return Integer.valueOf(variableDescriptorSize).equals(140);
	}
	

	public LocalDateTime getModified() {
		return ParserXpt.parseDateTime(modifiedString);
	}

	public LocalDateTime getCreated() {
		return ParserXpt.parseDateTime(createdString);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatasetXpt [");
	
		builder.append("variableDescriptorSize=");
		builder.append(variableDescriptorSize);
		builder.append(", name=");
		builder.append(name);
		builder.append(", version=");
		builder.append(version);
		builder.append(", os=");
		builder.append(os);
		builder.append(", createdString=");
		builder.append(createdString);
		builder.append(", modifiedString=");
		builder.append(modifiedString);
		builder.append(", label=");
		builder.append(label);
		builder.append(", type=");
		builder.append(type);
		builder.append(", variableCount=");
		builder.append(variableCountString);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
