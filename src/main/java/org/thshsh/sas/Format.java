package org.thshsh.sas;

public class Format {
	
	protected String name;
	protected Integer length;
	protected Integer decimals;
	protected Justify justify;
	
	public Format(String name, Integer length, Integer decimals, Justify justify) {
		super();
		this.name = name;
		this.length = length;
		this.decimals = decimals;
		this.justify = justify;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[name=");
		builder.append(name);
		builder.append(", length=");
		builder.append(length);
		builder.append(", decimals=");
		builder.append(decimals);
		builder.append(", justify=");
		builder.append(justify);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
