package org.thshsh.sas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogConverter {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogConverter.class);

	
	@Test
	public void test() throws URISyntaxException, IOException, DecoderException {
		List<String> lines = new ArrayList<>();
		List<byte[]> bytes = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(new File(LogConverter.class.getResource("v8log.txt").toURI())));
		
		String line;
		while((line = br.readLine()) != null) {
			String[] parts = line.split("( )+",2);
			LOGGER.info("parts: {}",new Object[] {parts});
			try {
				Integer num = Integer.valueOf(parts[0]);
				if(parts[1].startsWith("CHAR")) {
					String hex1 = br.readLine().split("( )+")[2];
					String hex2 = br.readLine().split("( )+")[2];
					//LOGGER.info("hex1: {}",hex1);
					//LOGGER.info("hex2: {}",hex2);
					StringBuilder hexString = new StringBuilder();
					for(int i=0;i<hex1.length();i++) {
						//String hex = hex1.charAt(i)+""+hex2.charAt(i);
						hexString.append(hex1.charAt(i));
						hexString.append(hex2.charAt(i));
					}
					LOGGER.info("hex: {}",hexString);
					byte[] ar = Hex.decodeHex(hexString.toString());
					bytes.add(ar);
				}
				else {
					String fullLine = StringUtils.rightPad(parts[1], 80);
					LOGGER.info("line: {}",fullLine);
					lines.add(fullLine);
					bytes.add(fullLine.getBytes());
				}
			} catch (NumberFormatException e) {
			
			}
			
			FileOutputStream fos = new FileOutputStream(new File("target/v8file.xpt"));
			
			for(byte[] b : bytes) {
				fos.write(b);
			}
			fos.flush();
			fos.close();
		}
		
	}

}
