package org.thshsh.sas.bdat;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Library;

/**
 * A Bdat "library" is actually just a folder containing sas7bdat files
 * Each file is a single dataset
 * TODO handle the case where the file passed in to the library is a directory or a single dataset
 * 
 * @author daniel.watson
 *
 */
public class LibraryBdat extends Library {


	protected static final Logger LOGGER = LoggerFactory.getLogger(LibraryBdat.class);

	
	List<DatasetBdat> datasets = new ArrayList<DatasetBdat>();
	
	public LibraryBdat(File f) {
		super(f);
	}
	
	@Override
	public LocalDateTime getModified() {
		Optional<LocalDateTime> zdt = datasets.stream().map(DatasetBdat::getHeader3).map(Header3::getModified).max(Comparator.comparing(Function.identity()));
		return zdt.orElse(null);
	}

	@Override
	public LocalDateTime getCreated() {
		Optional<LocalDateTime> zdt = datasets.stream().map(DatasetBdat::getHeader3).map(Header3::getCreated).min(Comparator.comparing(Function.identity()));
		return zdt.orElse(null);
	}

	@Override
	public List<? extends Dataset> getDatasets() {
		return datasets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setDatasets(List<? extends Dataset> ds) {
		this.datasets = (List<DatasetBdat>) ds;
		
	}

}
