package org.thshsh.sas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Formats tell SAS how to DISPLAY the data
 * @author daniel.watson
 *
 */
public abstract class Format {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(Format.class);
	
	/*protected FormatType type;
	protected Integer length;
	protected Integer decimals;
	protected Justify justify;*/
	
	public abstract FormatType getType();
	
	/*public Format(FormatType type, Integer length, Integer decimals, Justify justify) {
		super();
		this.type = type;
		this.length = length;
		this.decimals = decimals;
		this.justify = justify;
		LOGGER.info("new format: {} , {} , {} , {}",type,length,decimals,justify);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[type=");
		builder.append(type);
		builder.append(", length=");
		builder.append(length);
		builder.append(", decimals=");
		builder.append(decimals);
		builder.append(", justify=");
		builder.append(justify);
		builder.append("]");
		return builder.toString();
	}*/

	
	
	
}
