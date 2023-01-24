package org.thshsh.sas.bdat;

import java.util.Arrays;
import java.util.List;

public enum SubHeaderSignature {
	RowSize(0x00000000F7F7F7F7L,0xF7F7F7F700000000L,0xF7F7F7F7FFFFFBFEL,(long)0xF7F7F7F7), 
	ColumnSize(0x00000000F6F6F6F6L,0xF6F6F6F600000000L,0xF6F6F6F6FFFFFBFEL,(long)0xF6F6F6F6), 
	SubHeaderCount(0x00FCFFFFFFFFFFFFL,(long)0xFFFFFC00), 
	String(0xFDFFFFFFFFFFFFFFL,(long)0xFFFFFFFD), 
	ColumnName(0xFFFFFFFFFFFFFFFFL,(long)0xFFFFFFFF), 
	ColumnAttributes(0xFCFFFFFFFFFFFFFFL,(long)0xFFFFFFFC), 
	FormatAndLabel(0xFEFBFFFFFFFFFFFFL,(long)0xFFFFFBFE),
	ColumnList(0xFEFFFFFFFFFFFFFFL,(long)0xFFFFFFFE),
	Data((Long)null);

	protected List<Long> ids;

	private SubHeaderSignature(Long... ids) {
		this.ids = Arrays.asList(ids);
	}

	public static SubHeaderSignature fromId(Long id) {
		for (SubHeaderSignature s : values()) {
			if (s.ids.contains(id)) return s;
		}
		return null;
	}
}