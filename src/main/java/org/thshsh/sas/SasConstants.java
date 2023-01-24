package org.thshsh.sas;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SasConstants {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(SasConstants.class);
	
	//public static final ZonedDateTime EPOCH = ZonedDateTime.of(1960, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
	public static final LocalDateTime EPOCH = LocalDateTime.of(1960, 1, 1, 0, 0, 0, 0);

	public static void debugBytes(InputStream input, int num) throws IOException {
		//long mark = input.getRandomAccessFile().getFilePointer();
		input.mark(num);
		byte[] bytes = new byte[num];
		IOUtils.read(input, bytes);
		LOGGER.debug("Next {} Bytes String: '{}'",num,new String(bytes));
		LOGGER.debug("Next {} Bytes Hex: {}",num,Hex.encodeHexString(bytes));
		//input.getRandomAccessFile().seek(mark);
		input.reset();
	
	}

	public static void debugBytes(RandomAccessFileInputStream input, int num) throws IOException {
		LOGGER.debug("Position: {}",input.getRandomAccessFile().getFilePointer());
		long mark = input.getRandomAccessFile().getFilePointer();
		byte[] bytes = new byte[num];
		IOUtils.read(input, bytes);
		LOGGER.debug("Next {} Bytes String: '{}'",num,new String(bytes));
		LOGGER.debug("Next {} Bytes Hex: {}",num,Hex.encodeHexString(bytes));
		input.getRandomAccessFile().seek(mark);
		
		
	}
	
	public static LocalDateTime toDateTime(Double d) {
		return SasConstants.EPOCH.plusSeconds(((Double)d).longValue());
	}
	
}
