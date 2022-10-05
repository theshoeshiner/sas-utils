package org.thshsh.sas.xpt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An input stream that keeps track of our position and allows us to jump forward to a new "page" defined by a page size parameter
 * @author daniel.watson
 *
 */
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
		return nextPage(false);
	}
	
	/**
	 * Jumps to the next page if we are not at a page start
	 * @param force forces the jump to happen even if we are at page start already
	 * @return
	 * @throws IOException
	 */
	public boolean nextPage(boolean force) throws IOException {
		if(getPagePosition() != 0 || force) {
			int skip = pageSize - position%pageSize;
			long actual = skip(skip);
			return actual > 0;
		}
		else return false;
	}
	
	@Override
	public synchronized int read() throws IOException {
		int b = super.read();
		if (b >= 0) position += 1;
		return b;
	}

	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		int n = super.read(b, off, len);
		if (n > 0) position += n;
		return n;
	}
	
	public synchronized long getPage() {
		return position/pageSize;
	}

	public synchronized long getPageStart() {
		return position - position%pageSize;
	}
	
	public synchronized long getPagePosition() {
		return position % pageSize;
	}
	
	public boolean isHeader() throws IOException {
		return isHeader(null);
	}
	
	public boolean isHeader(String type) throws IOException {
		mark(pageSize);
		String string = XptConstants.HEADER_TAG + (type != null ? type : "");
		byte[] buffer = new byte[string.length()];
		this.read(buffer);
		boolean found = Arrays.equals(buffer, string.getBytes());
		reset();
		return found;
	}
	
	@Override
	public synchronized long skip(long skip) throws IOException {
		long n = super.skip(skip);
		if (n > 0) {
			position += n;
		}
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
