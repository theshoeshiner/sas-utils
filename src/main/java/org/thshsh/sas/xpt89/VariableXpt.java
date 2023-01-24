package org.thshsh.sas.xpt89;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Format;
import org.thshsh.sas.FormatType;
import org.thshsh.sas.Variable;
import org.thshsh.sas.VariableType;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class VariableXpt extends Variable {

	protected static final Logger LOGGER = LoggerFactory.getLogger(VariableXpt.class);

	
	@StructToken(order = 0)
	public Short variableTypeId;
	
	//Always zero
	@StructToken(order = 1) 
	public Short nameHash;
	
	@StructToken(order = 2)
	public Short length;
	
	/**
	 * The index of this variable (starts at 1)
	 */
	@StructToken(order = 3)
	public Short number;
	
	@StructToken(order = 4, length = 8)
	public String name;
	
	@StructToken(order = 5,length = 40)
	public String label;

	@StructToken(order = 6,length = 8)
	public String formatTypeString;
	
	@StructToken(order = 7)
	public Short formatLength;
	
	@StructToken(order = 8)
	public Short formatDecimals;
	
	@StructToken(order = 9)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "0000")}) //2 empty byte suffix
	public Short formatJustifyId; //always zero
	
	@StructToken(order = 11,length = 8)
	public String informatTypeString;
	
	@StructToken(order = 12)
	public Short informatLength;
	
	@StructToken(order = 13)
	public Short imformatDecimals;
	
	/**
	 * the byte position of this variable in the observation block
	 */
	@StructToken(order = 14)
	public Integer position;
	
	@StructToken(order = 15,length = 32)
	public String longName;
	
	@StructToken(order = 16)
	@StructTokenSuffix({@StructToken(type = TokenType.Bytes,constant = "000000000000000000000000000000000000000000000000000000000000000000000000")})
	public Short labelLength;

	public VariableType getType() {
		return 	VariableType.values()[variableTypeId-1];
	}
	
	public Format getFormat() {
		return new FormatXpt();
	}

	public Short getVariableTypeId() {
		return variableTypeId;
	}

	public void setVariableTypeId(Short variableTypeId) {
		this.variableTypeId = variableTypeId;
	}

	public Short getNameHash() {
		return nameHash;
	}

	public void setNameHash(Short none) {
		this.nameHash = none;
	}

	public Short getLength() {
		return length;
	}

	public void setLength(Short length) {
		this.length = length;
	}

	public Short getNumber() {
		return number;
	}

	public void setNumber(Short number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFormatTypeString() {
		return formatTypeString;
	}

	public void setFormatTypeString(String formatTypeString) {
		this.formatTypeString = formatTypeString;
	}

	public Short getFormatLength() {
		return formatLength;
	}

	public void setFormatLength(Short formatLength) {
		this.formatLength = formatLength;
	}

	public Short getFormatDecimals() {
		return formatDecimals;
	}

	public void setFormatDecimals(Short formatDecimals) {
		this.formatDecimals = formatDecimals;
	}

	public Short getFormatJustifyId() {
		return formatJustifyId;
	}

	public void setFormatJustifyId(Short formatJustifyId) {
		this.formatJustifyId = formatJustifyId;
	}


	public String getInformatTypeString() {
		return informatTypeString;
	}

	public void setInformatTypeString(String informatTypeString) {
		this.informatTypeString = informatTypeString;
	}

	public Short getInformatLength() {
		return informatLength;
	}

	public void setInformatLength(Short informatLength) {
		this.informatLength = informatLength;
	}

	public Short getImformatDecimals() {
		return imformatDecimals;
	}

	public void setImformatDecimals(Short imformatDecimals) {
		this.imformatDecimals = imformatDecimals;
	}


	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VariableXpt [name=");
		builder.append(name);
		builder.append(", length=");
		builder.append(length);
		builder.append(", nameHash=");
		builder.append(nameHash);
		builder.append(", number=");
		builder.append(number);
		builder.append(", label=");
		builder.append(label);
		builder.append(", formatTypeString=");
		builder.append(formatTypeString);
		builder.append(", formatLength=");
		builder.append(formatLength);
		builder.append(", formatDecimals=");
		builder.append(formatDecimals);
		builder.append(", formatJustifyId=");
		builder.append(formatJustifyId);
		builder.append(", informatTypeString=");
		builder.append(informatTypeString);
		builder.append(", informatLength=");
		builder.append(informatLength);
		builder.append(", imformatDecimals=");
		builder.append(imformatDecimals);
		builder.append(", position=");
		builder.append(position);
		builder.append(", getVariableType()=");
		builder.append(getType());
		builder.append("]");
		return builder.toString();
	}

	public class FormatXpt extends Format {

		@Override
		public FormatType getType() {
			if(formatTypeString == null) return null;
			return FormatType.fromString(formatTypeString);
		}
		
		
		
	}

}
