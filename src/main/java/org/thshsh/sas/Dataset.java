package org.thshsh.sas;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.input.RandomAccessFileInputStream;

public abstract class Dataset {

	/*protected String name;
	protected String label;
	protected String type;
	protected LocalDateTime created;
	protected LocalDateTime modified;
	protected List<? extends Variable> variables;*/
	
	/*	public Dataset(String name, String label, String type, LocalDateTime created, LocalDateTime modified, List<Variable> variables) {
			super();
			this.name = name;
			this.label = label;
			this.type = type;
			this.created = created;
			this.modified = modified;
			this.variables = variables;
		}*/

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getLabel();

	public abstract void setLabel(String label);

	public abstract String getType();

	public abstract void setType(String type);

	/*	public abstract LocalDateTime getCreated();
	
		public abstract void setCreated(LocalDateTime created);
	
		public abstract LocalDateTime getModified();
	
		public abstract void setModified(LocalDateTime modified);*/

	public abstract List<? extends Variable> getVariables();

	public abstract void setVariables(List<? extends Variable> variables);
	
	public abstract Stream<Observation> streamObservations(RandomAccessFileInputStream stream) throws IOException;
	
	public Stream<Observation> streamObservations(File file) throws IOException {
		return streamObservations(new RandomAccessFileInputStream(new RandomAccessFile(file, "r")));
	}
	
	
}
