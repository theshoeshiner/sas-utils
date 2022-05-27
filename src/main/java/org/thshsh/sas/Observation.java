package org.thshsh.sas;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Observation {

	
	static final Logger LOGGER = LoggerFactory.getLogger(Observation.class);

	protected Map<Variable,Object> values = new LinkedHashMap<Variable, Object>();

	public Observation() {}
	
	public void putValue(Variable var, Object val) {
		values.put(var, val);
	}
	
	public Map<Variable, Object> getValues() {
		return values;
	}
	

	
}
