package org.thshsh.sas;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.input.RandomAccessFileInputStream;

public abstract class Dataset {


	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getLabel();

	public abstract void setLabel(String label);

	public abstract String getType();

	public abstract void setType(String type);

	public abstract List<? extends Variable> getVariables();

	public abstract void setVariables(List<? extends Variable> variables);
	
	public abstract Stream<Observation> streamObservations(RandomAccessFileInputStream stream) throws IOException;
	
	public Stream<Observation> streamObservations(File file) throws IOException {
		return streamObservations(new RandomAccessFileInputStream(new RandomAccessFile(file, "r")));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dataset [getName()=");
		builder.append(getName());
		builder.append(", getLabel()=");
		builder.append(getLabel());
		builder.append(", getType()=");
		builder.append(getType());
		builder.append("]");
		return builder.toString();
	}
	
	
}
