package org.thshsh.sas.xpt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Observation;
import org.thshsh.sas.Variable;

public class DatasetXpt extends Dataset {

	//protected LibraryXpt library;
	protected DatasetHeaderXpt header;
	protected List<VariableXpt> variables;
	protected long observationStartByte;

	public DatasetXpt(LibraryXpt lib,DatasetHeaderXpt header) {
		super(lib);
		this.header = header;
		//this.library = lib;
	}

	public String getName() {
		return header.getName();
	}

	public void setName(String name) {
		header.setName(name);
	}

	public String getLabel() {
		return header.getLabel();
	}

	public void setLabel(String label) {
		header.setLabel(label);
	}

	public String getType() {
		return header.getType();
	}

	public void setType(String type) {
		header.setType(type);
	}

	public List<VariableXpt> getVariables() {
		if(variables == null) variables = new ArrayList<VariableXpt>();
		return variables;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setVariables(List<? extends Variable> variables) {
		this.variables = (List<VariableXpt>) variables;
	}
	
	public long getObservationStartByte() {
		return observationStartByte;
	}

	public void setObservationStartByte(long observationStartByte) {
		this.observationStartByte = observationStartByte;
	}

	@Override
	public LocalDateTime getModified() {
		return header.getModified();
	}

	@Override
	public LocalDateTime getCreated() {
		return header.getCreated();
	}

	protected Stream<Observation> createObservationStream(RandomAccessFileInputStream is) {
		Stream<Observation> stream= StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ObservationIteratorXpt(this, is),Spliterator.NONNULL), false);
		return stream;
	}

	@Override
	public LibraryXpt getLibrary() {
		return (LibraryXpt) super.getLibrary();
	}

	@Override
	public Long getRowCount() {
		throw new UnsupportedOperationException();
	}
}
