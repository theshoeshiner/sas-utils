package org.thshsh.sas;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Observation {

	public static final Logger LOGGER = LoggerFactory.getLogger(Observation.class);

	protected Map<Variable,Object> values = new LinkedHashMap<Variable, Object>();

	public Observation() {}
	
	public void putValue(Variable var, Object val) {
		values.put(var, val);
	}
	
	public Map<Variable, Object> getValues() {
		return values;
	}
	
	public List<Object> getFormattedValues() {
		return values.keySet().stream().map(v -> getFormattedValue(v)).collect(Collectors.toList());
	}
	
	public Object getValue(Variable v) {
		return values.get(v);
	}
	
	public Object getFormattedValue(Variable vm) {
		Object val = getValue(vm);
		LOGGER.trace("getFormattedValue value: {}",val);
		if(val == null) return null;
		else if(vm.getType() == VariableType.Numeric) {
			//val = ObservationIteratorXpt.ibm_to_ieee((byte[]) val);
			if(vm.getFormat()!=null) {
				switch(vm.getFormat().getType()) {
					//TODO need to not show time zone for these since SAS doesnt show them either 
					case DATE:
					case YYMMDD:
					case DDMMYY:
					case MMDDYY:
					case MONYY:
						val = SasConstants.EPOCH.toLocalDate().plusDays(((Double)val).longValue());
						break;
					case DATETIME:
						val = SasConstants.EPOCH.plusSeconds(((Double)val).longValue());
						break;
					case TIME:
						val = LocalTime.MIDNIGHT.plusSeconds(((Double)val).longValue());
						break;
					case NUMERIC:
						break;
					//case JULIAN:
					default:
						throw new IllegalArgumentException("Unhandled case");				
				}
			}
			
		}
		
		return val;
	}
	
}
