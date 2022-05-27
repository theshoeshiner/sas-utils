package org.thshsh.sas.bdat;

import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.thshsh.struct.Struct;
import org.thshsh.struct.StructToken;

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

	@StructToken(order = -1,length = 8)
	byte[] skip0;
	
	@StructToken(order = 0,length = 22)
	byte[] skip;
	
	@StructToken(order=1)
	Short formatStringIndex;
	
	@StructToken(order=2)
	Short formatStart;
	
	@StructToken(order=3)
	Short formatLength;
	
	@StructToken(order=4)
	Short labelStringIndex;
	
	@StructToken(order=5)
	Short labelStart;
	
	@StructToken(order=6)
	Short labelLength;

	
	
	public byte[] getSkip0() {
		return skip0;
	}

	public void setSkip0(byte[] skip0) {
		this.skip0 = skip0;
	}

	public byte[] getSkip() {
		return skip;
	}

	public void setSkip(byte[] skip) {
		this.skip = skip;
	}

	public Short getFormatStringIndex() {
		return formatStringIndex;
	}

	public void setFormatStringIndex(Short formatStringIndex) {
		this.formatStringIndex = formatStringIndex;
	}

	public Short getFormatStart() {
		return formatStart;
	}

	public void setFormatStart(Short formatStart) {
		this.formatStart = formatStart;
	}

	public Short getFormatLength() {
		return formatLength;
	}

	public void setFormatLength(Short formatLength) {
		this.formatLength = formatLength;
	}

	public Short getLabelStringIndex() {
		return labelStringIndex;
	}

	public void setLabelStringIndex(Short labelStringIndex) {
		this.labelStringIndex = labelStringIndex;
	}

	public Short getLabelStart() {
		return labelStart;
	}

	public void setLabelStart(Short labelStart) {
		this.labelStart = labelStart;
	}

	public Short getLabelLength() {
		return labelLength;
	}

	public void setLabelLength(Short labelLength) {
		this.labelLength = labelLength;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormatAndLabelSubHeader [formatStringIndex=");
		builder.append(formatStringIndex);
		builder.append(", formatStart=");
		builder.append(formatStart);
		builder.append(", formatLength=");
		builder.append(formatLength);
		builder.append(", labelStringIndex=");
		builder.append(labelStringIndex);
		builder.append(", labelStart=");
		builder.append(labelStart);
		builder.append(", labelLength=");
		builder.append(labelLength);
		builder.append("]");
		return builder.toString();
	}

	
	
}
