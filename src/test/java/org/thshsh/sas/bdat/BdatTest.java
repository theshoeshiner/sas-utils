package org.thshsh.sas.bdat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.thshsh.sas.Library;
import org.thshsh.sas.TestFile;
import org.thshsh.sas.TestUtils;
import org.thshsh.sas.xpt.XptTest;

public class BdatTest extends TestUtils {
	
	
	@Test
	public void testNumeric1() throws IOException, URISyntaxException {
		test(new TestFile(findFile("numeric1")));
	}
	
	@Test
	public void testNumeric2() throws IOException, URISyntaxException {
		test(new TestFile(findFile("numeric2")));
	}
	
	@Test
	public void testNumeric3() throws IOException, URISyntaxException {
		test(new TestFile(findFile("numeric3")));
	}
	
	@Test
	public void testPercents() throws IOException, URISyntaxException {
		test(new TestFile(findFile("percents")));
	}
	
	@Test
	public void fileWithLabel() throws IOException, URISyntaxException {
		test(new TestFile(findFile("file_with_label")));
	}
	
	@Test
	public void testCompDeleted() throws IOException, URISyntaxException {
		test(new TestFile(findFile("comp_deleted")));
	}
	
	/*@Test
	public void testNumeric4() throws IOException, URISyntaxException {
		test(new TestFile(findFile("numeric4")));
	}*/
	
	@Test
	public void testMixed1() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mixed1")));
	}
	
	@Test
	public void testMixed2() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mix_data_misc")));
	}

	@Override
	public Library getLibrary(File file) throws IOException {
		return ParserBdat.parseLibrary(file);
	}
	
	public File findFile(String prefix) {
		File folder = new File("./src/test/resources/org/thshsh/sas/bdat");
		for(File file : folder.listFiles()) {
			if(file.getName().startsWith(prefix)) return file;
		}
		
		throw new IllegalArgumentException("File with prefix: "+prefix+" not found");
	}
	
}
