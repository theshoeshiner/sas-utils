package org.thshsh.sas.bdat;

public interface Compressor {
	
	public byte[] decompressRow(int resultLength, byte[] row);

}
