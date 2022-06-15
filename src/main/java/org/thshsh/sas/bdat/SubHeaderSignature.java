package org.thshsh.sas.bdat;

public enum SubHeaderSignature {
	RowSize(-134744073), ColumnSize(-151587082), SubHeaderCount(-1024), String(-3), ColumnName(-1), ColumnAttributes(-4), FormatAndLabel(-1026),
	ColumnList(-2), Data(null);

	Integer id;

	private SubHeaderSignature(Integer id) {
		this.id = id;
	}

	public static SubHeaderSignature fromId(Integer id) {
		for (SubHeaderSignature s : values()) {
			if (id.equals(s.id))
				return s;
		}
		return null;
	}
}