package org.thshsh.sas;

import java.io.File;
import java.net.URI;

public class TestFile {
	
	public File file;
	public Long rows;
	public Integer columns;
	public TestFile(URI uri) {
		this(new File(uri));
	}
	public TestFile(File f) {
		this.file = f;
		String[] parts = file.getName().split("\\.")[0].split("_");
		if(parts.length > 0) {
			String[] size = parts[parts.length-1].split("x");
			if(size.length == 2) {
				this.columns = Integer.valueOf(size[0]);
				this.rows = Long.valueOf(size[1]);
			}
		}
	}
	public TestFile(URI uri, Long rows, Integer columns) {
		super();
		this.file = new File(uri);
		this.rows = rows;
		this.columns = columns;
	}
	
}