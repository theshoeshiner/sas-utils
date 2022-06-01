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
		
		LibraryBdat library = LibraryBdat.from_file(file);
		
		XptTest.libraryToCsv(library, file);
	}
	
	@Test
	public void testBdatFormats() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("formats.sas7bdat").toURI());
		
		LibraryBdat library = LibraryBdat.from_file(file);
		
		XptTest.libraryToCsv(library, file);
		
	}
	
	@Test
	public void testBasicBdat() throws Exception, URISyntaxException {
		
		File file = new File(LibraryBdat.class.getResource("lib.sas7bdat").toURI());
		
		LibraryBdat library = LibraryBdat.from_file(file);
		
		XptTest.libraryToCsv(library, file);
	}

}
