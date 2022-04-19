package org.thshsh.sas.v56;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * A CharSequence that reads from an input stream and into a buffer so that we don't
 * have to read the entire string into memory before scanning
 * 
 *
 */
public class InputStreamCharSequence implements CharSequence {

	protected InputStreamReader reader;
	protected StringBuffer buffer;
	protected long length;
	protected int position;
	
	public InputStreamCharSequence(File file) throws FileNotFoundException {
		this(new FileInputStream(file),file.length(),null);
	}
	
	public InputStreamCharSequence(InputStream io, long length,Charset cs) {
		reader = new InputStreamReader(io, cs!=null?cs:Charset.defaultCharset());
		buffer = new StringBuffer();
		this.length = length;
		this.position = 0;
		
	}
	
	@Override
	public int length() {
		return (int) length;
	}

	@Override
	public char charAt(int index) {
		if(buffer.length()<=index) {
			char[] cbuf = new char[index-buffer.length()+1];
			try {
				int read = reader.read(cbuf);
				position += read;
			} 
			catch (IOException e) {
				throw new IllegalStateException(e);
			}
			buffer.append(cbuf);
		}
		return buffer.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		charAt(end);
		return buffer.subSequence(start, end);
	}

	public StringBuffer getBuffer() {
		return buffer;
	}

	public int getPosition() {
		return position;
	}
	

}
