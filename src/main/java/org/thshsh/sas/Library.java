package org.thshsh.sas;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Library {
	

	/*protected List<Dataset> datasets;
	protected LocalDateTime created;
	protected LocalDateTime modified;
	protected String os;
	protected String sasVersion;*/
	
	public Library() {}
	
	public abstract LocalDateTime getModified();
	
	public abstract LocalDateTime getCreated();
	
	public abstract List<? extends Dataset> getDatasets();

	public abstract void setDatasets(List<? extends Dataset> ds);
	
	/*	@SuppressWarnings("unchecked")
		public Library(List<? extends Dataset> members, LocalDateTime created, LocalDateTime modified, String os, String version) {
			super();
			this.datasets = (List<Dataset>) members;
			this.created = created;
			this.modified = modified;
			this.os = os;
			this.sasVersion = version;
		}*/

	/*public List<Dataset> getDatasets() {
		return datasets;
	}
	
	public void setDatasets(List<Dataset> members) {
		this.datasets = members;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}
	
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	public LocalDateTime getModified() {
		return modified;
	}
	
	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}
	
	public String getOs() {
		return os;
	}
	
	public void setOs(String os) {
		this.os = os;
	}
	
	public String getVersion() {
		return sasVersion;
	}
	
	public void setSasVersion(String version) {
		this.sasVersion = version;
	}*/
	
	

}
