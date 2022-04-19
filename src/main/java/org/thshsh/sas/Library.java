package org.thshsh.sas;

import java.time.LocalDateTime;
import java.util.List;

import org.thshsh.sas.v56.Member;

public abstract class Library {
	
	protected List<Member> members;
	protected LocalDateTime created;
	protected LocalDateTime modified;
	protected String os;
	protected String version;
	
	public Library(List<Member> members, LocalDateTime created, LocalDateTime modified, String os, String version) {
		super();
		this.members = members;
		this.created = created;
		this.modified = modified;
		this.os = os;
		this.version = version;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
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
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
