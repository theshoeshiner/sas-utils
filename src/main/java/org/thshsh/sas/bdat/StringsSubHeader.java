package org.thshsh.sas.bdat;

public class StringsSubHeader extends SubHeader {
	
	public String string;

	public StringsSubHeader(String string) {
		super();
		this.string = string;
	}

	public String getString() {
		return string;
	}
	
	public String getSubString(int start, int length) {
		//we go back 2 bytes to account for the length value of this header
		start = start-2;
		return string.substring(start, start+length);
	}

}
