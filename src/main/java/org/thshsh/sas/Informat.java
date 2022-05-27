package org.thshsh.sas;

/**
 * Informats tell SAS how to READ the data
 * @author daniel.watson
 *
 */
public class Informat {

	protected String name;
	protected Integer length;
	protected Integer decimals;
	
	public Informat(String name, Integer length, Integer decimals) {
		super();
		this.name = name;
		this.length = length;
		this.decimals = decimals;
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
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
