package org.thshsh.sas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.primitives.Bytes;

@Deprecated
public class ByteBuffer {

	protected List<Array> arrays;
	
	public ByteBuffer() {
		arrays = new ArrayList<ByteBuffer.Array>();
	}
	
	public void append(byte[] array, int start, int length) {
		byte[] dest = new byte[length];
		System.arraycopy(array, start, dest, 0, length);
		arrays.add(new Array(dest));
	}
	
	public Stream<Byte> byteStream(){
		return arrays.stream().map(Array::getByteList).flatMap(List::stream);
	}
	
	public Iterator<Byte> byteIterator(){
		return arrays.stream().map(Array::getByteList).flatMap(List::stream).iterator();
	}
	
	public class Array {
		byte[] array;
		List<Byte> list;
		public Array(byte[] array) {
			this.array = array;
			this.list = Bytes.asList(array);
		}
		public List<Byte> getByteList(){
			return list;
		}
	}
	
}
