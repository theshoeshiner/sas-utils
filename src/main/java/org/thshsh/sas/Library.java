package org.thshsh.sas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.input.RandomAccessFileInputStream;

public abstract class Library {
	
	protected File file;
	
	public Library(File f) {
		this.file = f;	
	}
	
	public RandomAccessFileInputStream getRandomAccessFileInputStream() throws FileNotFoundException {
		return new RandomAccessFileInputStream(new RandomAccessFile(getFile(), "r"), true);
	}
	
	public abstract LocalDateTime getModified();
	
	public abstract LocalDateTime getCreated();
	
	public abstract List<? extends Dataset> getDatasets();

	public abstract void setDatasets(List<? extends Dataset> ds);
	
	public Optional<? extends Dataset> getDataset(String name) {
		return getDatasets().stream().filter(d -> d.getName().equals(name)).findFirst();
	}

	public File getFile() {
		return file;
	}
	

}
