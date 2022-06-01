package org.thshsh.sas;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PagedInputStream extends FilterInputStream {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PagedInputStream.class);

	protected int pageSize;
	protected int page;
	protected int position;
	protected int mark;

	public PagedInputStream(InputStream in, int size) {
		super(in);
		this.pageSize = size;
	}

	public synchronized long getPosition() {
		return position;
	}

	public void nextPage() throws IOException {
		int skip = position%pageSize;
		LOGGER.info("skipping: {}",skip);
		IOUtils.skip(this,skip );
	}
	
	@Override
	public synchronized int read() throws IOException {
		int b = super.read();
		if (b >= 0)
			position += 1;
		return b;
	}

	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		int n = super.read(b, off, len);
		if (n > 0)
			position += n;
		return n;
	}

	@Override
	public synchronized long skip(long skip) throws IOException {
		long n = super.skip(skip);
		if (n > 0)
			position += n;
		return n;
	}

	@Override
	public synchronized void mark(int readlimit) {
		super.mark(readlimit);
		mark = position;
	}

	@Override
	public synchronized void reset() throws IOException {
		if (!markSupported())
			throw new IOException("Mark not supported.");
		super.reset();
		position = mark;
	}
}
