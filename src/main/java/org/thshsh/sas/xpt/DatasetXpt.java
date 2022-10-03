package org.thshsh.sas.xpt;

import java.io.IOException;
import java.io.InputStream;
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

	protected DatasetHeaderXpt header;
	
	protected List<VariableXpt> variables;
	
	protected long observationStartByte;

	public DatasetXpt(DatasetHeaderXpt header) {
		super();
		this.header = header;
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

	public Stream<Observation> streamObservations(RandomAccessFileInputStream cs) throws IOException {
		return streamObservations(this, cs);
	}
	
	public static Stream<Observation> streamObservations(DatasetXpt member, InputStream is) {
		Stream<Observation> stream= StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ObservationIteratorXpt(member, is),Spliterator.NONNULL), false);
		return stream;
	}
}
