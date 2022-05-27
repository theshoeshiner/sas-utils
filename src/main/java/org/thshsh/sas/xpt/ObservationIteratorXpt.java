package org.thshsh.sas.xpt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Observation;
import org.thshsh.sas.VariableType;
import org.thshsh.struct.Struct;

/**
 * This class is used so that we can stream Observations into memory and not have to read them all at once
 * @author daniel.watson
 *
 */
public class ObservationIteratorXpt implements Iterator<Observation> {

	static final Logger LOGGER = LoggerFactory.getLogger(ObservationIteratorXpt.class);
	
	public static final String CHARSET = "ISO-8859-1";
	public static final byte SENTINEL = ' ';
	public static final char NO_VALUE = '.';
	public static final Struct<?> IBM = Struct.create(">Q");
	public static final char[] SPECIAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
	
	int stride = 0;
	byte[] chunk;
	InputStream is;
	DatasetXpt member;
	Struct<?> struct;
	Boolean hasNext = null;
	Boolean needToRead = true;
	
	public ObservationIteratorXpt(DatasetXpt m,InputStream in)  {
		
		this.member = m;
		this.is = in;
		StringBuilder formatString = new StringBuilder();
		
		for(VariableXpt var : member.getVariables()) {
			stride+= var.getLength();
			formatString.append(var.getLength());
			formatString.append(var.getVariableType() == VariableType.Numeric?"s":"S");
		}

		struct = Struct.create(formatString.toString(),Charset.forName(CHARSET));
		
		try {
			IOUtils.skip(is, member.getObservationStartByte());
		} 
		catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		chunk = new byte[stride];
		
	}
	
	
	@Override
	public boolean hasNext() {
		readIfNecessary();
		return hasNext;
	}
	
	protected void readIfNecessary() {
		try {
			if(needToRead) {
				int read = is.read(chunk);
				if(read != stride) {
					LOGGER.info("Only read {} bytes",read);
					hasNext = false;
				}
				else {
					hasNext = false;
					for(byte b : chunk) {
						if(b != SENTINEL) {
							hasNext = true;
							break;
						}
					}
				}
				needToRead = false;
			}
		} 
		catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Observation next() {
		
		readIfNecessary();
		needToRead = true;
		
		List<Object> tokens = struct.unpack(chunk);
		
		if(tokens.size() != member.getVariables().size()) throw new IllegalStateException("Token Count: "+tokens.size()+" Not Equal to Header Count: "+member.getVariables().size());

		Observation ob = new Observation();
		
		for(int i=0;i<member.getVariables().size();i++) {
			VariableXpt vm = this.member.getVariables().get(i);
			Object val = tokens.get(i);
			if(vm.getVariableType() == VariableType.Numeric) val = ObservationIteratorXpt.ibm_to_ieee((byte[]) val);
			ob.putValue(vm, val);
		}
		
		return ob;

	}


	public static Double ibm_to_ieee(byte[] bytes) {
		
		
		byte[] padded = java.util.Arrays.copyOf(bytes , 8);
		
		List<Object> tokens = IBM.unpack(padded);
		Long val = (Long) tokens.get(0);
		long sign = val & 0x8000000000000000l;
		long exp = (val & 0x7f00000000000000l) >> 56;
		long man = (val & 0x00ffffffffffffffl);
	
		if(man == 0) {
			if(bytes[0] == 0x00) return 0d;
			else if(bytes[0] == 0x80) return -0d;
			else if(bytes[0] == NO_VALUE) return null;
			else if(ArrayUtils.contains(SPECIAL, (char)bytes[0])){
				//TODO handle
				throw new IllegalArgumentException("Special Missing Value not handled");
			}
			else throw new IllegalArgumentException("Zero Mantissa Value was not readable");
		}
		
		int shift;
		
		if ( (val & 0x0080000000000000l) > 0) shift = 3;
		else if( (val & 0x0040000000000000l) > 0) shift = 2;
		else if( (val & 0x0020000000000000l) > 0) shift = 1;
	    else shift = 0;
		
		man = man >> shift;
		man = man & 0xffefffffffffffffl;
		exp -= 65;
		exp <<= 2;
		exp += shift + 1023;
		long ieee = sign | (exp << 52) | man;
		
				
		return Double.longBitsToDouble(ieee);
		
	}
	
	
	
}