package org.thshsh.sas;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TestUtils {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class);

	public abstract Library getLibrary(File file) throws IOException;
	
	/*public static LibraryXpt getLibrary(File file) throws IOException {
		LibraryXpt library = ParserXpt.parseLibrary(file);
		return library;
	}*/

	public Library testLibraryToCsv(File file) throws Exception, URISyntaxException {
		return testLibraryToCsv(file, null,null);
	}

	public Library testLibraryToCsv(File file,Integer expectedRows, Integer expectedColumns) throws Exception, URISyntaxException {
		try {
			Library library = getLibrary(file);
			testLibraryToCsv(library, file,expectedRows,expectedColumns);
			return library;
		}
		catch(Exception e) {
			LOGGER.error("",e);
			throw e;
		}
		
	}

	public void testLibraryToCsv(Library library, File file, Integer expectedRows, Integer expectedColumns) throws IOException {
		for(Dataset dataset : library.getDatasets()) {
			testDatasetToCsv(dataset, file, expectedRows, expectedColumns);
		}
	}

	public void testLibrarySingleDatasetToCsv(Library library, File file, Integer expectedRows, Integer expectedColumns) throws IOException {
		testDatasetToCsv(library.getDatasets().get(0), file, expectedRows, expectedColumns);
	}

	public void testDatasetToCsv(Dataset dataset, File file, Integer expectedRows, Integer expectedColumns) throws IOException {
		

		LOGGER.info("file: {} member: {}",file,dataset);
		
		if(expectedColumns != null) Assertions.assertEquals(expectedColumns,dataset.getVariables().size(),"Expected "+expectedColumns+" columns but only found "+dataset.getVariables().size()+" in metadata");

		File outFile = new File("./target/"+file.getName()+"_"+dataset.getName()+"_out.csv");
		
		CSVFormat.Builder b = CSVFormat.Builder.create();
		b.setHeader(
				dataset.getVariables()
				.stream()
				.map(Variable::getName)
				.collect(Collectors.toList())
			.toArray(new String[0]));
		CSVFormat def = b.build();
		
	    CSVPrinter printer = def.print(outFile, Charset.defaultCharset());
	    
	    MutableInt rows = new MutableInt(0);
	    
	    LOGGER.info("iterating observations");
	    
	    dataset.streamObservations(file).forEach(obs -> {
	    	try {
	    		List<Object> vals = obs.getFormattedValues();
	    		if(expectedColumns != null) Assertions.assertEquals(expectedColumns, vals.size(),"Expected "+expectedColumns+" columns but only found "+vals.size()+" at row "+rows.getValue());
	    		for(int i=0;i<vals.size();i++) {
	    			Object val = vals.get(i);
	    			if(val instanceof Double) {
	    				Double d = (Double) val;
	    				String s = new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
	    				vals.set(i, s);
	    			}
	    		}
	    		
				printer.printRecord(vals);
				rows.increment();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
	    });
	    
	    printer.close();
	    
	    if(expectedRows != null) Assertions.assertEquals(expectedRows, rows.getValue(),"Expected "+expectedRows+" rows but found "+rows.getValue());

	   

		
	}

	public void test(TestFile test) throws IOException  {
		Library library = getLibrary(test.file);
		testLibrarySingleDatasetToCsv(library, test.file, test.rows, test.columns);
	}

}
