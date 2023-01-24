package org.thshsh.sas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.input.RandomAccessFileInputStream;

public abstract class Dataset {

	protected Library library;
	
	protected Map<String,Variable> variableMap;
	protected Map<String,Variable> variableMapIgnoreCase;
	
	public Dataset(Library l) {
		this.library = l;
	}
	
	public Optional<? extends Variable> getVariable(String name) {
		return Optional.ofNullable(getVariableMap().get(name));
	}
	
	public Optional<? extends Variable> getVariableIgnoreCase(String name) {
		return Optional.ofNullable(getVariableMapIgnoreCase().get(name.toLowerCase()));
	}
	
	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getType();

	public abstract void setType(String type);
	
	public abstract LocalDateTime getModified();

	public abstract LocalDateTime getCreated();

	public abstract Long getRowCount();
	
	public abstract List<? extends Variable> getVariables();
	
	public Map<String,Variable> getVariableMap() {
		if(variableMap == null) initVariableMaps();
		return variableMap;
	}
	
	public Map<String,Variable> getVariableMapIgnoreCase() {
		if(variableMapIgnoreCase == null) initVariableMaps();
		return variableMapIgnoreCase;
	}
	
	protected void initVariableMaps() {
		variableMap = new HashMap<>();
		variableMapIgnoreCase = new HashMap<>();
		getVariables().forEach(var -> {
			variableMapIgnoreCase.put(var.getName().toLowerCase(), var);
			variableMap.put(var.getName(), var);
		});
	}
	
	public Library getLibrary() {
		return library;
	}

	public abstract void setVariables(List<? extends Variable> variables);
	
	public Stream<Observation> streamObservations() throws FileNotFoundException  {
		if(getLibrary().getFile() == null) throw new IllegalStateException("Library does not hold a reference to a file");
		return streamObservations(getLibrary().getRandomAccessFileInputStream());
	}
	
	public Stream<Observation> streamObservations(File file) throws FileNotFoundException  {
		return streamObservations(new RandomAccessFileInputStream(new RandomAccessFile(file, "r"),true));
	}
	
	public Stream<Observation> streamObservations(RandomAccessFileInputStream is){
		Stream<Observation> stream = createObservationStream(is).onClose(() -> {
			try {
				//NOTE this ensures that we close the file if we close the obs stream
				is.close();
			} 
			catch (IOException e) {
				throw new IllegalStateException(e);
			}
		});
		return stream;
	}

	protected abstract Stream<Observation> createObservationStream(RandomAccessFileInputStream is);
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dataset [getName()=");
		builder.append(getName());
		builder.append(", getType()=");
		builder.append(getType());
		builder.append("]");
		return builder.toString();
	}
	
	
}
