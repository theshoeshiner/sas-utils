package org.thshsh.sas.xpt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Observation;
import org.thshsh.sas.VariableType;
import org.thshsh.struct.Struct;
import org.thshsh.struct.TokenType;

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
	//IBM numeric values are big endian unsigned longs
	public static final Struct<?> IBM = Struct.create(">Q");
	
	protected int observationSize = 0;
	protected byte[] buffer;
	protected InputStream input;
	protected DatasetXpt member;
	protected Struct<?> struct;
	
	protected Boolean hasNext = null;
	protected Boolean needToRead = true;
	
	public ObservationIteratorXpt(DatasetXpt m,InputStream in)  {
		
		this.member = m;
		this.input = in;
				
		//observations are stored as a packed struct consisting of either bytes or characters for each variable
		struct = new Struct<>();
		for(VariableXpt var : member.getVariables()) {
			struct.appendToken(var.getType() == VariableType.Numeric?TokenType.Bytes:TokenType.String, var.getLength());
		}
		observationSize = struct.byteCount();
		
		try {
			IOUtils.skip(input, member.getObservationStartByte());
		} 
		catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		buffer = new byte[observationSize];
		
	}
	
	
	@Override
	public boolean hasNext() {
		readIfNecessary();
		return hasNext;
	}
	
	protected void readIfNecessary() {
		try {
			if(needToRead) {
				int read = IOUtils.read(input, buffer);
				if(read != observationSize) {
					hasNext = false;
				}
				else {
					hasNext = false;
					for(byte b : buffer) {
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
		
		List<Object> tokens = struct.unpack(buffer);
		
		if(tokens.size() != member.getVariables().size()) throw new IllegalStateException("Token Count: "+tokens.size()+" Not Equal to Header Count: "+member.getVariables().size());

		Observation ob = new Observation();
		
		for(int i=0;i<member.getVariables().size();i++) {
			VariableXpt vm = this.member.getVariables().get(i);
			Object val = tokens.get(i);
			if(vm.getType() == VariableType.Numeric) {
				val = ObservationIteratorXpt.ibm_to_ieee((byte[]) val);
			}
			ob.putValue(vm, val);
		}
		
		return ob;

	}


	@SuppressWarnings("unused")
	public static Object ibm_to_ieee(byte[] bytes) {
		
		
		byte[] padded = java.util.Arrays.copyOf(bytes , 8);
		
		List<Object> tokens = IBM.unpack(padded);
		Long val = (Long) tokens.get(0);
		long sign = val & 0x8000000000000000l;
		long exponent = (val & 0x7f00000000000000l) >> 56;
		long mantissa = (val & 0x00ffffffffffffffl);
	
		if(mantissa == 0) {
			MissingValue mv;
			if(bytes[0] == 0x00) return 0d;
			else if(bytes[0] == 0x80) return -0d;
			else if(bytes[0] == NO_VALUE) return null;
			//TODO figure out how to handle missing characters in a more generic way
			//this code would work for numeric values, but there is no way to know for certain
			//if a character value represented a MissingValue or just actual text without the 
			//user specifying the collection of valid missing value chars
			else if((mv =  MissingValue.fromCharacter((char) bytes[0])) != null) return null;
			else throw new IllegalArgumentException("Zero Mantissa Value was not readable");
		}
		
		int shift;
		
		if ( (val & 0x0080000000000000l) > 0) shift = 3;
		else if( (val & 0x0040000000000000l) > 0) shift = 2;
		else if( (val & 0x0020000000000000l) > 0) shift = 1;
	    else shift = 0;
				
		mantissa = mantissa >> shift;
		mantissa = mantissa & 0xffefffffffffffffl;
		exponent -= 65;
		exponent <<= 2;
		exponent += shift + 1023;
		long ieee = sign | (exponent << 52) | mantissa;
		
				
		return Double.longBitsToDouble(ieee);
		
	}
	
	
	
}