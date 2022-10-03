package org.thshsh.sas.bdat;

public enum SubHeaderCategory {

	A(0), //Row Size, Column Size, Subheader Counts, Column Format and Label, in Uncompressed file

	B(1); //Column Text, Column Names, Column Attributes, Column List in uncompressed and ALL subheaders in compressed file
	
	int id;
	private SubHeaderCategory(int id) {
		this.id = id;
	}
	public static SubHeaderCategory fromId(Integer id) {
		for(SubHeaderCategory s : values()) {
			if(id.equals(s.id)) return s;
		}
		return null;
	}
	
}
