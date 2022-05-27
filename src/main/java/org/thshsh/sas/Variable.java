package org.thshsh.sas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Variable {
	
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(Variable.class);
	

	
	/*	protected VariableType type;
		protected Integer length;
		protected Integer number;
		protected String name;
		protected String label;
		protected Format informat;
		protected Format format;
		protected Integer position;*/
	
	public Variable() {}

	public abstract String getName();
	
	/*public Variable(VariableType type, Integer length, Integer number, String name, String label, Format format,Format informat,
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
	}*/
	
	/*
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
	
		public Format getInformat() {
			return informat;
		}
	
	
		public void setInformat(Format informat) {
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
		*/
	

}
