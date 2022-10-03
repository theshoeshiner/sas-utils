package org.thshsh.sas.bdat;

enum PageType {
	
	//metadata with potentially compressed row data in subheaders
	Meta(0,true,false),
	Meta2(16384,true,false),
	CMeta(128,true,false),
	
	//data pages storing uncompressed row data with no subheaders
	Data(256,false,true),
	Data2(384,false,true),
	
	Mixed1(512,true,true), // Mix pages that contain all valid records
	Mixed2(640,true,true),  // Mix pages that contain valid and deleted records
	
	Amd(2014,true,false), //amended metadata information
	
	//unknown
	Comp(-28672,false,false);
	
	int id;
	boolean meta;
	boolean data;
	private PageType(int id,boolean meta,boolean data) {
		this.id = id;
		this.meta = meta;
		this.data = data;
	}
	public boolean mixed() {
		return data && meta;
	}
	public static PageType fromId(Integer id) {
		for(PageType s : values()) {
			if(id.equals(s.id)) return s;
		}
		//throw new ElementNotFoundException();
		throw new IllegalArgumentException("PageType "+id+" not found");
	}
}