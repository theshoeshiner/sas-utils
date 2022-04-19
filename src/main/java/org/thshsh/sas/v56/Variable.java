package org.thshsh.sas.v56;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Format;
import org.thshsh.sas.Informat;
import org.thshsh.sas.Justify;
import org.thshsh.sas.VariableType;
import org.thshsh.struct.Struct;
import org.thshsh.util.MapUtils;

public class Variable {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Variable.class);
	
	public static Map<Integer,Struct> FORMATS = MapUtils.createHashMap(
			140, Struct.create(">hhhh8S40S8Shhh2s8Shhl52s",LibraryV56.METADATA_CHARSET), 
			136, Struct.create(">hhhh8S40S8Shhh2s8shhl48s",LibraryV56.METADATA_CHARSET)
	);
	
	protected VariableType type;
	protected Integer length;
	protected Integer number;
	protected String name;
	protected String label;
	protected Informat informat;
	protected Format format;
	protected Integer position;

	public Variable(VariableType type, Integer length, Integer number, String name, String label, Format format,Informat informat,
			Integer position) {
		super();
		this.type = type;
		this.length = length;
		this.number = number;
		this.name = name;
		this.label = label;
		this.informat = informat;
		this.format = format;
		this.position = position;
	}
	

	public VariableType getType() {
		return type;
	}


	public void setType(VariableType type) {
		this.type = type;
	}


	public Integer getLength() {
		return length;
	}


	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
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

	public Informat getInformat() {
		return informat;
	}


	public void setInformat(Informat informat) {
		this.informat = informat;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
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
		builder.append("[type=");
		builder.append(type);
		builder.append(", length=");
		builder.append(length);
		builder.append(", number=");
		builder.append(number);
		builder.append(", name=");
		builder.append(name);
		builder.append(", label=");
		builder.append(label);
		builder.append(", informat=");
		builder.append(informat);
		builder.append(", format=");
		builder.append(format);
		builder.append(", position=");
		builder.append(position);
		builder.append("]");
		return builder.toString();
	}



	
	
	public static Variable fromBytes(byte[] bts) {
		
		List<Object> tokens = FORMATS.get(bts.length).unpack(bts);
		
		
		
		return new Variable(
				VariableType.values()[(int)((short)tokens.get(0))-1],
				((Short)tokens.get(2)).intValue(),
				((Short)tokens.get(3)).intValue(),
				tokens.get(4).toString().trim(),
				tokens.get(5).toString().trim(),
				new Format(
						tokens.get(6).toString().trim(),
						((Short)tokens.get(7)).intValue(),
						((Short)tokens.get(8)).intValue(),
						Justify.values()[((Short)tokens.get(9)).intValue()]
						
						),
				new Informat(
						tokens.get(11).toString().trim(), 
						((Short)tokens.get(12)).intValue(), 
						((Short)tokens.get(13)).intValue()),
				((Integer)tokens.get(14)).intValue()
				);
	}
	
	

}
