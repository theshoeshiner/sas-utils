package org.thshsh.sas.xpt;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Library;
import org.thshsh.sas.TestFile;
import org.thshsh.sas.TestUtils;

public class XptTest extends TestUtils {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(XptTest.class);
	

	File folder = new File("./src/test/resources/org/thshsh/sas/xpt");
	File folder2 = new File("./src/ignore/resources/org/thshsh/sas/xpt");
	
	@Test
	public void testTwoTables() throws Exception, URISyntaxException {
		
		File file = findFile("twotables");
		
		Library library = getLibrary(file);
		
		Dataset air = library.getDataset("AIR").get();
		testDatasetToCsv(air, file, 144, 2);
		
		Dataset class1 = library.getDataset("CLASS1").get();
		testDatasetToCsv(class1, file, 19, 5);

	}
	
	@Test
	public void testSmall() throws Exception, URISyntaxException {
		test(new TestFile(findFile("small")));
	}
	
	@Test
	public void testNumeric() throws Exception, URISyntaxException {
		test(new TestFile(findFile("numeric")));
	}
	
	@Test
	public void testMixed() throws Exception, URISyntaxException {
		test(new TestFile(findFile("mixed")));
	}

	/*@Test
	public void testPfizerv9() throws Exception, URISyntaxException {
		test(new TestFile(findFile("pfizer15")));
	}*/
	
	
	@Test
	public void testPfizer() throws Exception, URISyntaxException {
		List<File> files = findFiles("pfizer");
		for(File file : files) {
			test(new TestFile(file));
		}
	}
	
	
	
	@Test
	@Disabled("Takes to long to run on regular basis")
	public void testLarge() throws Exception, URISyntaxException {
		List<File> files = findFiles("large");
		for(File file : files) {
			test(new TestFile(file));
		}
	}
	
	
	public Library getLibrary(File file) throws IOException {
		LOGGER.info("getLibrary: {}",file);
		Library library = ParserXpt.parseLibrary(file);
		return library;
	}
	
	public List<File> findFiles(String prefix) {
		List<File> files = new ArrayList<>();
		for(File file : folder.listFiles()) {
			if(file.getName().startsWith(prefix)) files.add(file);
		}
		for(File file : folder2.listFiles()) {
			if(file.getName().startsWith(prefix)) files.add(file);
		}
		return files;
	}
	
	public File findFile(String prefix) {
		for(File file : folder.listFiles()) {
			if(file.getName().startsWith(prefix)) return file;
		}
		for(File file : folder2.listFiles()) {
			if(file.getName().startsWith(prefix)) return file;
		}
		throw new IllegalArgumentException("File with prefix: "+prefix+" not found");
	}
}
