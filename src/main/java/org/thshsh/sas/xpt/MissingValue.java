package org.thshsh.sas.xpt;

/**
 * missing value characters allow XPTs to imply different meanings behind missing data. The meaning of each character is not well 
 * defined across all files e.g. 'I' might mean "Incomplete" while 'A might mean "Absent"
 * Since the list of possible characters is short we simply return an enumeration in place of these
 * @author daniel.watson
 *
 */
public enum MissingValue {
	A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,UC('_');
	char character;
	private MissingValue() {
		this.character = this.name().charAt(0); 
	}
	private MissingValue(char c) {
		this.character = c;
	}
	public static MissingValue fromCharacter(char c) {
		for(MissingValue mv: values()) {
			if(mv.character == c) return mv;
		}
		return null;
	}
	
}
