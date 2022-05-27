package org.thshsh.sas.xpt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Library;
import org.thshsh.sas.Variable;
import org.thshsh.sas.bdat.LibraryBdat;
import org.thshsh.sas.xpt.LibraryXpt;
import org.thshsh.struct.Struct;

public class V56Test {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(V56Test.class);

	@Test
	public void testTwoTableXpt() throws Exception, URISyntaxException {
		File file = new File(V56Test.class.getResource("twotables.xpt").toURI());
		testFile(file);
	}
	
	@Test
	public void testLargeXpt() throws Exception, URISyntaxException {
		File file = new File(V56Test.class.getResource("large.xpt").toURI());
		testFile(file);
	}
	
	@Test
	public void testBasicXpt() throws Exception, URISyntaxException {
		File file = new File(V56Test.class.getResource("test.xpt").toURI());
		testFile(file);
	}

	public void testFile(File file) throws Exception, URISyntaxException {
		
		LibraryXpt library = LibraryXpt.fromFile(file);
		
		output(library, file);
		
	}
	
	
	public static void output(Library library, File file) throws IOException {
		
		for(Dataset m : library.getDatasets()) {
			
			FileInputStream fis = new FileInputStream(file);
			//Member m = library.getMembers().get(0);
			LOGGER.info("member: {}",m);
			//m.loadObservations(fis);
			
			File outFile = new File("./target/"+file.getName()+"-"+m.getName()+"-out.csv");
			
			CSVFormat.Builder b = CSVFormat.Builder.create();
			b.setHeader(m.getVariables().stream().map(Variable::getName).collect(Collectors.toList()).toArray(new String[0]));
			CSVFormat def = b.build();
			//def.withHeader("");
			
		    CSVPrinter printer = def.print(outFile, Charset.defaultCharset());
		    
		    m.streamObservations(fis).forEach(obs -> {
		    	try {
					printer.printRecord(obs.getValues().values());
					
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
		    });
		    

		    printer.close();
			
		}
		
	}
	
	@Test
	public void byteTest() throws DecoderException {
		
		String[] ids = new String[] {
				"f7f7f7f7",
				"f6f6f6f6",
				"00fcffff",
				"fdffffff",
				"ffffffff",
				"fcffffff",
				//"fffffffc",
				"fefbffff",
				"feffffff"
				
		};
		
		for(String ss : ids) {
		
			LOGGER.info("HEX: {}",ss);
			byte[] ar = Hex.decodeHex(ss);
			LOGGER.info("bytes: {}",new Object[] {ar});
			Struct s = Struct.create("<i");
			Number i = (Number) s.unpack(ar).get(0);
			LOGGER.info("unpacked: {}",i);
			
			//Integer p = i.intValue();
			//byte[] packed = s.pack(p);
			//Struct s2 = Struct.create("<I");
			//LOGGER.info("packed: {}",Hex.encodeHexString(packed));
			
			Struct s2 = Struct.create("<q");
			LOGGER.info("s2 packed: {}",Hex.encodeHexString(s2.pack(i.longValue())));
		}
	}
	
	
	
	
	
}
