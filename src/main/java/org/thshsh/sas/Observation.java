package org.thshsh.sas;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.v56.Member;
import org.thshsh.sas.v56.Variable;
import org.thshsh.struct.Struct;

public class Observation {

	
	static final Logger LOGGER = LoggerFactory.getLogger(Observation.class);

	public static final String CHARSET = "ISO-8859-1";
	public static final byte SENTINEL = ' ';
	public static final char NO_VALUE = '.';
	public static final Struct IBM = Struct.create(">Q");
	public static final char[] SPECIAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
	
	Map<Variable,Object> values = new LinkedHashMap<Variable, Object>();

	
	
	public Observation() {}
	
	public void putValue(Variable var, Object val) {
		values.put(var, val);
	}
	
	public Map<Variable, Object> getValues() {
		return values;
	}
	
	public static Stream<Observation> streamObservations(Member member, InputStream is) {
		Stream<Observation> stream= StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ObservationIterator(member, is),Spliterator.NONNULL), false);
		return stream;
	}

	/*public static void loadObservations(Member member, InputStream is) throws IOException {
		
	
		Integer stride = 0;
		StringBuilder formatString = new StringBuilder();
		
		for(VariableMetadata var : member.getHeader().getVariables()) {
			stride+= var.getLength();
			if(var.getType() == VariableType.Numeric) {
				formatString.append(var.getLength());
				formatString.append("s");
			}
			else {
				formatString.append(var.getLength());
				formatString.append("S");
			}
		}
	
		
		Struct.Format format = Struct.createFormat(formatString.toString(),Charset.forName("ISO-8859-1"));
		byte sentinel = ' ';
		
		is.skipNBytes(member.getObservationByte());
		byte[] chunk = new byte[stride];
		
		for(int i=member.getObservationByte();i<member.getEndByte();i+=stride) {
			
			LOGGER.info("chunk: {} - {}",i,i+stride);
			int read = is.read(chunk);
			LOGGER.info("read: {}",read);
			LOGGER.info("read: {}",Hex.encodeHexString(chunk));
	
			boolean end = true;
			for(byte b : chunk) {
				if(b != sentinel) {
					end = false;
					break;
				}
			}
			
			if(end) {
				LOGGER.info("Reached end of Observations");
				break;
			}
			
			List<Object> tokens = Struct.unpack(format, chunk);
			
			if(tokens.size() != member.getHeader().getVariables().size()) throw new IllegalStateException("Token Count: "+tokens.size()+" Not Equal to Header Count: "+member.getHeader().getVariables().size());
	
			Observation ob = new Observation();
			
			Twople.zip(member.getHeader().getVariables(), tokens).forEach(tp -> {
				
				VariableMetadata vm = tp.getOne();
				Object val = tp.getTwo();
				
				if(vm.getType() == VariableType.Numeric) val = ibm_to_ieee((byte[]) val);
				
				LOGGER.info("val: {}",val);
				ob.putValue(vm, val);
				
				
			});
			
			member.getObservations().add(ob);
			
		}
	}
	*/
	
	
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
