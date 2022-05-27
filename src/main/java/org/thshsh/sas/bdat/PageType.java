package org.thshsh.sas.bdat;

enum PageType {
	Meta(0,true),Data(256,false),Mixed1(512,true),Mixed2(640,true),Amd(2014,true),Metc(16384,false),Comp(-28672,false);
	int id;
	boolean meta;
	private PageType(int id,boolean meta) {
		this.id = id;
		this.meta = meta;
	}
	public static PageType fromId(Integer id) {
		for(PageType s : values()) {
			if(id.equals(s.id)) return s;
		}
		return null;
	}
}