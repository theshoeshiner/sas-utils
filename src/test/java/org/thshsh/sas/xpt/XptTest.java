package org.thshsh.sas.xpt;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Library;
import org.thshsh.sas.Variable;

public class XptTest {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(XptTest.class);
	
	static List<File> files = new ArrayList<>();
	static File twoTables;
	static File large;
	static File numeric;
	static File small;
	static {
		try {
			twoTables = new File(XptTest.class.getResource("twotables.xpt").toURI());
			large = new File(XptTest.class.getResource("large.xpt").toURI());
			numeric = new File(XptTest.class.getResource("numeric.xpt").toURI());
			small = new File(XptTest.class.getResource("small.xpt").toURI());
			files.add(twoTables);
			files.add(large);
			files.add(numeric);
			files.add(small);
		} 
		catch (URISyntaxException e) {}
	}
	
	/*@Test
	public void testAllMetadata() throws IOException {
		for(File file : files) {
			LOGGER.info("File: {}",file);
			LibraryXpt lib = getLibrary(file);
		}
	}
	*/
	@Test
	public void testTwoTables() throws Exception, URISyntaxException {
		LibraryXpt library = getLibrary(twoTables);
		Assertions.assertEquals(2, library.getDatasets().size());
		Assertions.assertEquals(2, library.getDatasets().get(0).getVariables().size());
		Assertions.assertEquals(5, library.getDatasets().get(1).getVariables().size());
		Assertions.assertEquals(library.getDatasets().get(0).streamObservations(twoTables).count(),144);
		Assertions.assertEquals(library.getDatasets().get(1).streamObservations(twoTables).count(),19);
		libraryToCsv(library, twoTables);
	}
	
	@Test
	public void testSmall() throws Exception, URISyntaxException {
		LibraryXpt library = getLibrary(small);
		Assertions.assertEquals(1, library.getDatasets().size());
		Assertions.assertEquals(5, library.getDatasets().get(0).getVariables().size());		
		Assertions.assertEquals(library.getDatasets().get(0).streamObservations(small).count(),7557);
		libraryToCsv(library, small);
	}
	
	@Test
	public void testNumeric() throws Exception, URISyntaxException {
		LibraryXpt library = getLibrary(numeric);
		Assertions.assertEquals(1, library.getDatasets().size());
		Assertions.assertEquals(17, library.getDatasets().get(0).getVariables().size());
		Assertions.assertEquals(library.getDatasets().get(0).streamObservations(numeric).count(),10109);
		libraryToCsv(library, numeric);
	}
	
	@Test
	public void testLargeXpt() throws Exception, URISyntaxException {
		LibraryXpt library = getLibrary(large);
		Assertions.assertEquals(1, library.getDatasets().size());
		Assertions.assertEquals(193, library.getDatasets().get(0).getVariables().size());
	}
	
	/*@Test
	public void testBasicXpt() throws Exception, URISyntaxException {
		File file = new File(XptTest.class.getResource("test.xpt").toURI());
		testFile(file);
	}*/
	
	/*@Test
	public void testAcqXpt() throws Exception, URISyntaxException {
		File file = new File(XptTest.class.getResource("acq_f.xpt").toURI());
		testFile(file);
	}*/

	public static LibraryXpt getLibrary(File file) throws IOException {
		LibraryXpt library = ParserXpt.parserLibrary(file);
		return library;
	}
	
	public static LibraryXpt testLibraryToCsv(File file) throws Exception, URISyntaxException {
		try {
			LibraryXpt library = getLibrary(file);
			libraryToCsv(library, file);
			return library;
		}
		catch(Exception e) {
			LOGGER.error("",e);
			throw e;
		}
		
	}
	
	
	public static void libraryToCsv(Library library, File file) throws IOException {
		

		for(Dataset m : library.getDatasets()) {
			
			RandomAccessFileInputStream stream = new RandomAccessFileInputStream(new RandomAccessFile(file, "r"));
			
			//FileInputStream fis = new FileInputStream(file);
			//Member m = library.getMembers().get(0);
			LOGGER.info("member: {}",m);
			//m.loadObservations(fis);
			
			File outFile = new File("./target/"+file.getName()+"-"+m.getName()+"-out.csv");
			
			CSVFormat.Builder b = CSVFormat.Builder.create();
			b.setHeader(
					m.getVariables()
					.stream()
					.map(Variable::getName)
					.collect(Collectors.toList())
				.toArray(new String[0]));
			CSVFormat def = b.build();
			//def.withHeader("");
			
		    CSVPrinter printer = def.print(outFile, Charset.defaultCharset());
		    
		    m.streamObservations(stream).forEach(obs -> {
		    	try {
					printer.printRecord(obs.getFormattedValues());
					
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
		    });
		    

		    printer.close();
			
		}
		
	}
	
	/*@Test
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
	}*/
	
	
	
	
	
}
