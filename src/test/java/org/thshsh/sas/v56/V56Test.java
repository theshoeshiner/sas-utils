package org.thshsh.sas.v56;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;

public class V56Test {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(V56Test.class);

	
	@Test
	public void test() throws Exception, URISyntaxException {
		
		//File file = new File(V56Test.class.getResource("large.xpt").toURI());
		File file = new File(V56Test.class.getResource("twotables.xpt").toURI());
		//File file = new File(V56Test.class.getResource("test.xpt").toURI());


		LibraryV56 library = LibraryV56.from_file(file);
		
		for(Member m : library.getMembers()) {
			
			FileInputStream fis = new FileInputStream(file);
			//Member m = library.getMembers().get(0);
			LOGGER.info("member: {}",m);
			//m.loadObservations(fis);
			
			File outFile = new File("./target/"+file.getName()+"-"+m.getHeader().getName()+"-out.csv");
			
			CSVFormat.Builder b = CSVFormat.Builder.create();
			b.setHeader(m.getHeader().getVariables().stream().map(Variable::getName).collect(Collectors.toList()).toArray(new String[0]));
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
	
}
