package org.thshsh.sas;

import java.io.File;
import java.io.IOException;

public interface Parser {

	public Library parseLibrary(File file) throws IOException;
	
}
