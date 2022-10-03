package org.thshsh.sas.bdat;

import java.util.Objects;

public enum SubHeaderSignature {
	RowSize(0xF7F7F7F7), 
	ColumnSize(0xF6F6F6F6), 
	SubHeaderCount(0xFFFFFC00), 
	String(0xFFFFFFFD), 
	ColumnName(0xFFFFFFFF), 
	ColumnAttributes(0xFFFFFFFC), 
	FormatAndLabel(0xFFFFFBFE),
	ColumnList(0xFFFFFFFE),
	Data(null);

	Integer id;

	private SubHeaderSignature(Integer id) {
		this.id = id;
	}

	public static SubHeaderSignature fromId(Integer id) {
		for (SubHeaderSignature s : values()) {
			if (Objects.equals(id, s.id)) return s;
		}
		return null;
	}
}