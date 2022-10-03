package org.thshsh.sas.xpt;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;

public class LibraryXpt extends org.thshsh.sas.Library {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(LibraryXpt.class);
	
	public static final String METADATA_CHARSET_NAME = "US-ASCII";
	public static Charset METADATA_CHARSET = Charset.forName("US-ASCII");
	public static DateTimeFormatter DATE_TIME_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("ddMMMyy:HH:mm:ss")
            .toFormatter();

	protected LibraryHeaderXpt header;
	protected List<DatasetXpt> datasets;
	
	public LibraryXpt() {
		super();
	}
	
	


	public LibraryHeaderXpt getHeader() {
		return header;
	}


	public void setHeader(LibraryHeaderXpt header) {
		this.header = header;
	}


	public List<DatasetXpt> getDatasets() {
		if(datasets ==null) datasets = new ArrayList<DatasetXpt>();
		return datasets;
	}


	
	@Override
	public LocalDateTime getModified() {
		return ParserXpt.parseDateTime(header.modifiedString);
	}


	@Override
	public LocalDateTime getCreated() {
		return ParserXpt.parseDateTime(header.createdString);
	}


	@Override
	public void setDatasets(List<? extends Dataset> ds) {
		this.datasets = (List<DatasetXpt>)datasets;
		
	}
	

}
