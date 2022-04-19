package org.thshsh.sas;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.thshsh.sas.v56.Member;
import org.thshsh.sas.v56.Variable;
import org.thshsh.struct.Struct;
import org.thshsh.tuples.Twople;

/**
 * This class is used so that we can stream Observations into memory and not have to read them all at once
 * @author daniel.watson
 *
 */
public class ObservationIterator implements Iterator<Observation> {

	
	int stride = 0;
	byte[] chunk;
	InputStream is;
	Member member;
	Struct struct;
	Boolean hasNext = null;
	Boolean needToRead = true;
	
	
	public ObservationIterator(Member m,InputStream in)  {
		
		this.member = m;
		this.is = in;
		StringBuilder formatString = new StringBuilder();
		
		for(Variable var : member.getHeader().getVariables()) {
			stride+= var.getLength();
			formatString.append(var.getLength());
			formatString.append(var.getType() == VariableType.Numeric?"s":"S");
		}

		struct = Struct.create(formatString.toString(),Charset.forName(Observation.CHARSET));
		
		try {
			IOUtils.skip(is, member.getObservationByte());
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
					Observation.LOGGER.info("Only read {} bytes",read);
					hasNext = false;
				}
				else {
					hasNext = false;
					for(byte b : chunk) {
						if(b != Observation.SENTINEL) {
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
		
		if(tokens.size() != member.getHeader().getVariables().size()) throw new IllegalStateException("Token Count: "+tokens.size()+" Not Equal to Header Count: "+member.getHeader().getVariables().size());

		Observation ob = new Observation();
		
		Twople.zip(member.getHeader().getVariables(), tokens).forEach(tp -> {
			
			Variable vm = tp.getOne();
			Object val = tp.getTwo();
			
			if(vm.getType() == VariableType.Numeric) val = Observation.ibm_to_ieee((byte[]) val);

			ob.putValue(vm, val);
			
			
		});
		
		return ob;

	}
	
	
	
}