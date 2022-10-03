package org.thshsh.sas.bdat;

public enum CompressionType {

	None(0),Truncated(1),Compressed(4);
	int id;
	//boolean meta;
	private CompressionType(int id) {
		this.id = id;
	}
	public static CompressionType fromId(Integer id) {
		for(CompressionType s : values()) {
			if(id.equals(s.id)) return s;
		}
		return null;
	}
	
}
