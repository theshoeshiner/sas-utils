package org.thshsh.sas.bdat;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.thshsh.sas.xpt.XptTest;

public class BdatTest {
	
	@Test
	public void testMetadata() {
		
	}
	
	@Test
	public void testBdatWithTime() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("dataset-with-time.sas7bdat").toURI());
		
		LibraryBdat library = ParserBdat.parseLibrary(file);
		
		XptTest.libraryToCsv(library, file);
	}

	

	@Test
	public void testEventrepository() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("eventrepository.sas7bdat").toURI());
		
		LibraryBdat library = ParserBdat.parseLibrary(file);
		
		XptTest.libraryToCsv(library, file);
	}
	
	
	@Test
	public void testCrime() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("crime.sas7bdat").toURI());
		
		LibraryBdat library = ParserBdat.parseLibrary(file);
		
		XptTest.libraryToCsv(library, file);
	}
	
	@Test
	public void testDCSKINPRODUCT() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("DCSKINPRODUCT.sas7bdat").toURI());
		
		LibraryBdat library = ParserBdat.parseLibrary(file);
		
		XptTest.libraryToCsv(library, file);
	}
	
	@Test
	public void testCps() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("cps.sas7bdat").toURI());
		
		LibraryBdat library = ParserBdat.parseLibrary(file);
		
		XptTest.libraryToCsv(library, file);
	}
	
	
	@Test
	public void testBdatFormats() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("formats.sas7bdat").toURI());
		
		LibraryBdat library = ParserBdat.parseLibrary(file);
		
		XptTest.libraryToCsv(library, file);
		
	}
	
	@Test
	public void testBasicBdat() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("lib.sas7bdat").toURI());
		
		LibraryBdat library = ParserBdat.parseLibrary(file);
		
		XptTest.libraryToCsv(library, file);
	}

}
