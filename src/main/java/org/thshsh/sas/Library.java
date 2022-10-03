package org.thshsh.sas;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class Library {
	
	public Library() {}
	
	public abstract LocalDateTime getModified();
	
	public abstract LocalDateTime getCreated();
	
	public abstract List<? extends Dataset> getDatasets();

	public abstract void setDatasets(List<? extends Dataset> ds);
	
	public Optional<? extends Dataset> getDataset(String name) {
		return getDatasets().stream().filter(d -> d.getName().equals(name)).findFirst();
	}
	

}
