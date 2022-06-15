package org.thshsh.sas.xpt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XptInputStream extends BufferedInputStream {

	protected static final Logger LOGGER = LoggerFactory.getLogger(XptInputStream.class);

	protected int pageSize;
	protected int position;
	protected int mark;

	public XptInputStream(InputStream in, int size) {
		super(in,size);
		this.pageSize = size;
	}

	public synchronized long getPosition() {
		return position;
	}

	public boolean nextPage() throws IOException {
		int skip = pageSize - position%pageSize;
		//LOGGER.info("skip: {}",skip);
		long actual = skip(skip);
		return actual > 0;
	}
	
	@Override
	public synchronized int read() throws IOException {
		int b = super.read();
		if (b >= 0)position += 1;
		return b;
	}

	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		int n = super.read(b, off, len);
		if (n > 0)position += n;
		return n;
	}
	
	public synchronized long getPage() {
		return position/pageSize;
	}

	public synchronized long getPageStart() {
		return position - position%pageSize;
	}
	
	public boolean isHeader() throws IOException {
		mark(pageSize);
		byte[] buffer = new byte[XptConstants.HEADER_TAG.length()];
		this.read(buffer);
		boolean found = Arrays.equals(buffer, XptConstants.HEADER_TAG.getBytes());
		reset();
		return found;
	}
	
	@Override
	public synchronized long skip(long skip) throws IOException {
		long n = super.skip(skip);
		if (n > 0)position += n;
		return n;
	}

	@Override
	public synchronized void mark(int readlimit) {
		super.mark(readlimit);
		mark = position;
	}

	@Override
	public synchronized void reset() throws IOException {
		if (!markSupported())throw new IOException("Mark not supported.");
		super.reset();
		position = mark;
	}
	
	
	
}
