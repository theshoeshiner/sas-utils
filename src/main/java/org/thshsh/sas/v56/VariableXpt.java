package org.thshsh.sas.v56;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Variable;
import org.thshsh.sas.VariableType;
import org.thshsh.struct.StructToken;

public class VariableXpt extends Variable {

	protected static final Logger LOGGER = LoggerFactory.getLogger(VariableXpt.class);

	
	@StructToken(order = 0)
	Short variableTypeId;
	
	@StructToken(order = 1)
	Short none;
	
	@StructToken(order = 2)
	Short length;
	
	@StructToken(order = 3)
	Short number;
	
	@StructToken(order = 4, length = 8)
	String name;
	
	@StructToken(order = 5,length = 40)
	String label;

	@StructToken(order = 6,length = 8)
	String formatTypeString;
	
	@StructToken(order = 7)
	Short formatLength;
	
	@StructToken(order = 8)
	Short formatDecimals;
	
	@StructToken(order = 9)
	Short formatJustifyId;
	
	@StructToken(order = 10,length=2)
	byte[] skip1;
	
	@StructToken(order = 11,length = 8)
	String informatTypeString;
	
	@StructToken(order = 12)
	Short informatLength;
	
	@StructToken(order = 13)
	Short imformatDecimals;
	
	@StructToken(order = 14)
	public Integer position;
	
	byte[] skip2;
	
	public VariableType getVariableType() {
		return 	VariableType.values()[variableTypeId-1];
	}

	public Short getVariableTypeId() {
		return variableTypeId;
	}

	public void setVariableTypeId(Short variableTypeId) {
		this.variableTypeId = variableTypeId;
	}

	public Short getNone() {
		return none;
	}

	public void setNone(Short none) {
		this.none = none;
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

	public byte[] getSkip1() {
		return skip1;
	}

	public void setSkip1(byte[] skip1) {
		this.skip1 = skip1;
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

	
	
	/*public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}*/

	

	public byte[] getSkip2() {
		return skip2;
	}

	public void setSkip2(byte[] skip2) {
		this.skip2 = skip2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VariableXpt [name=");
		builder.append(name);
		builder.append(", length=");
		builder.append(length);
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
		builder.append(getVariableType());
		builder.append("]");
		return builder.toString();
	}
	
	/*public static Map<Integer, Struct> FORMATS = MapUtils.createHashMap(140, Struct.create(
							   ">hhhH8S40S8Shhh2s8Shhl52s", LibraryV56.METADATA_CHARSET),
			136, Struct.create(">hhhH8S40S8Shhh2s8shhl48s", LibraryV56.METADATA_CHARSET));
	
	public static Variable fromBytes(byte[] bts) throws IOException {
	
		//Struct sm = FORMATS.get(140);
		//LOGGER.info("sm: {}",sm.byteCount());
		
		
		Struct.unpack(TokenType.Bytes, ByteOrder.Big, bts);
		
		LOGGER.info("bytes: {}",Hex.encodeHexString(bts));
		
		//Struct<Variable140> s = Struct.create(Variable140.class).byteOrder(ByteOrder.Big);		
		//Variable140 var = s.unpackEntity(bts);
		//LOGGER.info("Variable140: {}",var);
		
		return null;
		
		
		LOGGER.info("length: {}",bts.length);
		List<Object> tokens = FORMATS.get(bts.length).unpack(bts);
		
		Format format = null;
		String formatName = tokens.get(6).toString().trim();
		if(StringUtils.isNotBlank(formatName)) {
			format = new Format(
					FormatType.fromString(tokens.get(6).toString().trim()),
					((Short)tokens.get(7)).intValue(),
					((Short)tokens.get(8)).intValue(),
					Justify.values()[((Short)tokens.get(9)).intValue()]
					);
		}
		
		LOGGER.info("index: {}",tokens.get(3).getClass());
		LOGGER.info("index: {}",tokens.get(3));
		
		return new Variable(
				VariableType.values()[(int)((short)tokens.get(0))-1],
				((Short)tokens.get(2)).intValue(),
				((Integer)tokens.get(3)).intValue(),
				tokens.get(4).toString().trim(),
				tokens.get(5).toString().trim(),
				format,
				new Informat(
						tokens.get(11).toString().trim(), 
						((Short)tokens.get(12)).intValue(), 
						((Short)tokens.get(13)).intValue()),
				null,
				
				((Integer)tokens.get(14)).intValue()
				);
	}
	*/
}
