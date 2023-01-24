package org.thshsh.sas.bdat;

import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class FormatAndLabelSubHeader extends SubHeader {
	
	/*
	 List<Object> formatLengths = s0.unpack(stream);
		LOGGER.info("formatLengths: {}",formatLengths);
		//TODO if the format length is not zero, then parse this as a format
		
		Struct<?> s = Struct.create("14shhhhhh");
		
		List<Object> objects = s.unpack(stream);
	
		LOGGER.info("padding: {}",Hex.encodeHexString((byte[]) objects.get(0)));
		LOGGER.info("objects: {}",objects);
		
		String formatTextString = header.properties.columnTextStrings.get((short)objects.get(1));
		//LOGGER.info("formatTextString: {}",formatTextString);
		String formatString = formatTextString.substring((short)objects.get(2),(short)objects.get(2)+(short) objects.get(3));
		//format of $ = character, but we already know this
		String columnTextString = header.properties.columnTextStrings.get((short)objects.get(4));
		//LOGGER.info("columnTextString: {}",columnTextString);
		LOGGER.info("{} - {}",(short)objects.get(5),(short) objects.get(6));
		String label = columnTextString.substring((short)objects.get(5),(short)objects.get(5)+(short) objects.get(6));
	 */

	//@StructToken(order = -5,length = 8)
	//byte[] skip0;
	
	//@StructToken(order = 0,length = 22)
	//byte[] skip;
	
	@StructTokenPrefix({@StructToken(type=TokenType.Bytes,constant = "0000000000000000")})
	@StructToken(order = -4)
	public Short formatDigits;
	
	@StructToken(order = -3)
	public Short formatDecimals;
	
	@StructToken(order = -2)
	public Short informatDigits;
	
	@StructToken(order = -1)
	@StructTokenSuffix({
		@StructToken(type=TokenType.Bytes,constant = "0000000000000000000000000000",validate = false)
	})
	public Short informatDecimals;
	
	/*@StructToken(order = 0,length = 14)
	byte[] unknown;*/
	
	@StructToken(order=1)
	public Short formatIndex;
	
	@StructToken(order=2)
	public Short formatOffset;
	
	@StructToken(order=3)
	public Short formatLength;
	
	@StructToken(order=4)
	public Short labelIndex;
	
	@StructToken(order=5)
	public Short labelOffset;
	
	@StructToken(order=6)
	@StructTokenSuffix({
		@StructToken(type=TokenType.Bytes,constant = "000000000000",validate = false)
	})
	public Short labelLength;


	public Short getFormatIndex() {
		return formatIndex;
	}

	public void setFormatIndex(Short formatStringIndex) {
		this.formatIndex = formatStringIndex;
	}

	public Short getFormatOffset() {
		return formatOffset;
	}

	public void setFormatOffset(Short formatStart) {
		this.formatOffset = formatStart;
	}

	public Short getFormatLength() {
		return formatLength;
	}

	public void setFormatLength(Short formatLength) {
		this.formatLength = formatLength;
	}

	public Short getLabelIndex() {
		return labelIndex;
	}

	public void setLabelIndex(Short labelStringIndex) {
		this.labelIndex = labelStringIndex;
	}

	public Short getLabelOffset() {
		return labelOffset;
	}

	public void setLabelOffset(Short labelStart) {
		this.labelOffset = labelStart;
	}

	public Short getLabelLength() {
		return labelLength;
	}

	public void setLabelLength(Short labelLength) {
		this.labelLength = labelLength;
	}
	
	public String getFormat() {
		return dataset.getSubHeaderString(formatIndex, formatOffset, formatLength).orElse(null);
	}
	
	public String getLabel() {
		return dataset.getSubHeaderString(labelIndex, labelOffset, labelLength).orElse(null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[formatDigits=");
		builder.append(formatDigits);
		builder.append(", formatDecimals=");
		builder.append(formatDecimals);
		builder.append(", informatDigits=");
		builder.append(informatDigits);
		builder.append(", informatDecimals=");
		builder.append(informatDecimals);
		builder.append(", formatIndex=");
		builder.append(formatIndex);
		builder.append(", formatOffset=");
		builder.append(formatOffset);
		builder.append(", formatLength=");
		builder.append(formatLength);
		builder.append(", labelIndex=");
		builder.append(labelIndex);
		builder.append(", labelOffset=");
		builder.append(labelOffset);
		builder.append(", labelLength=");
		builder.append(labelLength);
		builder.append("]");
		return builder.toString();
	}

	
	
}
