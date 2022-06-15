package org.thshsh.sas;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SasConstants {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(SasConstants.class);
	
	public static final ZonedDateTime EPOCH = ZonedDateTime.of(1960, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

	public static void debugBytes(InputStream input, int num) throws IOException {
		//long mark = input.getRandomAccessFile().getFilePointer();
		input.mark(num);
		byte[] bytes = new byte[num];
		IOUtils.read(input, bytes);
		LOGGER.info("Next {} Bytes String: '{}'",num,new String(bytes));
		LOGGER.info("Next {} Bytes Hex: {}",num,Hex.encodeHexString(bytes));
		//input.getRandomAccessFile().seek(mark);
		input.reset();
	
	}

	public static void debugBytes(RandomAccessFileInputStream input, int num) throws IOException {
		LOGGER.info("Position: {}",input.getRandomAccessFile().getFilePointer());
		long mark = input.getRandomAccessFile().getFilePointer();
		byte[] bytes = new byte[num];
		IOUtils.read(input, bytes);
		LOGGER.info("Next {} Bytes String: '{}'",num,new String(bytes));
		LOGGER.info("Next {} Bytes Hex: {}",num,Hex.encodeHexString(bytes));
		input.getRandomAccessFile().seek(mark);
		
		
	}
	
}
