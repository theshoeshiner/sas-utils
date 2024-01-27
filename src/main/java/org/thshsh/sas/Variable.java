package org.thshsh.sas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Variable {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(Variable.class);
	
	public Variable() {}

	public abstract String getName();
	
	public abstract String getLabel();
	
	public abstract VariableType getType();
	
	public abstract Format getFormat();

}
