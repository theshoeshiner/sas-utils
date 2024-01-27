package org.thshsh.sas.bdat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Library;
import org.thshsh.sas.TestFile;
import org.thshsh.sas.TestUtils;


public class BdatTest extends TestUtils {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BdatTest.class); 

	
	ParserBdat parser = new ParserBdat();
	
	@Test
	public void testNumeric1() throws IOException, URISyntaxException {
		test(new TestFile(findFile("numeric1")));
	}
	
	@Test
	public void testFolder() throws IOException, URISyntaxException {
		File lib = new File("./src/test/resources/org/thshsh/sas/bdatlib");
		LibraryBdat library = parser.parseLibrary(lib);
		
		LOGGER.info("library: {}",library);
		LOGGER.info("created: {}",library.getCreated());
		LOGGER.info("modified: {}",library.getModified());
		
	}
	
	@Test
	public void testDoubles1() throws IOException, URISyntaxException {
		test(new TestFile(findFile("doubles")));
	}
	
	@Test
	public void testDoubles2() throws IOException, URISyntaxException {
		test(new TestFile(findFile("doubles2")));
	}
	
	@Test
	public void testNumeric64BigEndian() throws IOException, URISyntaxException {
		test(new TestFile(findFile("64_numeric4")));
	}
	
	@Test
	@Disabled
	public void testExtend1() throws IOException, URISyntaxException {
		//FIXME
		test(new TestFile(findFile("extend_no")));
	}
	
	@Test
	public void testExtend2() throws IOException, URISyntaxException {
		test(new TestFile(findFile("extend_yes")));
	}
	
	@Test
	public void test64Compressed() throws IOException, URISyntaxException {
		test(new TestFile(findFile("64_comp")));
	}
	
	@Test
	public void testLarge() throws IOException, URISyntaxException {
		test(new TestFile(findFile("large")),false);
	}
	
	@Test
	public void testNumeric2() throws IOException, URISyntaxException {
		test(new TestFile(findFile("numeric2")),false);
	}
	
	@Test
	public void testNumeric3() throws IOException, URISyntaxException {
		test(new TestFile(findFile("numeric3")));
	}
	
	@Test
	public void testPercents() throws IOException, URISyntaxException {
		test(new TestFile(findFile("percents")),true);
	}
	
	@Test
	public void fileWithLabel() throws IOException, URISyntaxException {
		test(new TestFile(findFile("file_with_label")));
	}
	
	@Test
	@Disabled()
	public void testCompDeleted() throws IOException, URISyntaxException {
		//FIXME
		test(new TestFile(findFile("comp_deleted")));
	}
	
	@Test
	@Disabled()
	public void testDeleted() throws IOException, URISyntaxException {
		//FIXME
		test(new TestFile(findFile("data_page_with_deleted")));
	}
	

	
	@Test
	public void testMixed1() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mixed1")));
	}
	
	@Test
	public void testMixed3() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mixed3")));
	}
	
	@Test
	public void testMixed4() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mixed4")),false);
	}
	
	@Test
	public void testMixedMisc() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mix_data_misc")),false);
	}
	
	@Test
	public void testMixedFormats() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mixed_formats")));
	}
	
	@Test
	public void testMixedFormats2() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mixed_formats2")));
	}
	
	@Test
	public void testMixed2() throws IOException, URISyntaxException {
		test(new TestFile(findFile("mixed2")),true);
	}
	
	@Test
	public void testDatasetWithTime() throws IOException, URISyntaxException {
		test(new TestFile(findFile("time")));
	}
	
	@Test
	public void testTimestamps() throws IOException, URISyntaxException {
		List<File> files = findFiles("section5");
		for(File file : files) {
			try {
				Library library = getLibrary(file);
				LocalDateTime created = library.getCreated();
				LocalDateTime modified = library.getModified();
				LOGGER.info("file: {} created: {} modified: {}",file,created,modified);
				/*library.getDatasets().forEach(d -> {
					//d.getModified();
				});*/
			} catch (NotImplementedException e) {
				LOGGER.warn("error on "+file,e);
			}
		}
		//LOGGER.info("names: {}",datasetnames);
	}
	
	@Test
	public void testDatasetNames() throws IOException, URISyntaxException {
		List<File> files = findFiles("");
		List<String> datasetnames = new ArrayList<String>();
		for(File file : files) {
			try {
				Library library = getLibrary(file);
				library.getDatasets().forEach(d -> {
					datasetnames.add(file.getName()+" = "+d.getName());
				});
			} catch (NotImplementedException e) {
				LOGGER.warn("error on "+file,e);
			}
		}
		LOGGER.info("names: {}",datasetnames);
	}

	@Override
	public Library getLibrary(File file) throws IOException {
		return parser.parseLibrary(file);
	}
	
	public File findFile(String prefix) {
		List<File> files = findFiles(prefix);
		return files.get(0);
		/*File folder = new File("./src/test/resources/org/thshsh/sas/bdat");
		for(File file : folder.listFiles()) {
			if(file.getName().startsWith(prefix)) return file;
		}
		folder = new File("./src/ignore/resources/org/thshsh/sas/bdat");
		for(File file : folder.listFiles()) {
			if(file.getName().startsWith(prefix)) return file;
		}
		
		throw new IllegalArgumentException("File with prefix: "+prefix+" not found");*/
	}
	
	public List<File> findFiles(String prefix) {
		List<File> files = new ArrayList<>();
		File folder = new File("./src/test/resources/org/thshsh/sas/bdat");
		for(File file : folder.listFiles()) {
			if(file.getName().startsWith(prefix)) {
				//return file;
				files.add(file);
			}
		}
		folder = new File("./src/ignore/resources/org/thshsh/sas/bdat");
		for(File file : folder.listFiles()) {
			if(file.getName().startsWith(prefix)) {
				files.add(file);
			}
		}
		if(files.size() == 0) throw new IllegalArgumentException("File with prefix: "+prefix+" not found");
		return files;
	}
	
	
}
