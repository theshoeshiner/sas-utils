package org.thshsh.sas.bdat;

enum PageType {
	Meta(0,true,false),Data(256,false,false),Mixed1(512,true,true),Mixed2(640,true,true),Amd(2014,true,false),Metc(16384,false,false),Comp(-28672,false,false);
	int id;
	boolean meta;
	boolean mixed;
	private PageType(int id,boolean meta,boolean mixed) {
		this.id = id;
		this.meta = meta;
		this.mixed = mixed;
	}
	public static PageType fromId(Integer id) {
		for(PageType s : values()) {
			if(id.equals(s.id)) return s;
		}
		return null;
	}
}