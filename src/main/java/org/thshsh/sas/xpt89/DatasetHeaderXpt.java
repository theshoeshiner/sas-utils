package org.thshsh.sas.xpt89;

import org.thshsh.sas.xpt.XptConstants;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

public class DatasetHeaderXpt extends org.thshsh.sas.xpt.DatasetHeaderXpt {

	public static final String DATASET_HEADER_STRING =    XptConstants.HEADER_TAG+ "MEMBV8  HEADER RECORD!!!!!!!00000000000000000160000000";
	
	public static final String DESCRIPTOR_HEADER_STRING = XptConstants.HEADER_TAG+ "DSCPTV8 HEADER RECORD!!!!!!!000000000000000000000000000000  SAS     ";
	
	public static final String VARIABLES_HEADER_STRING =  XptConstants.HEADER_TAG+ "NAMSTV8 HEADER RECORD!!!!!!!0000";
	
	@Override
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = DATASET_HEADER_STRING)})
	@StructToken(order=1,length=4)
	@StructTokenSuffix({@StructToken(type=TokenType.String,constant = SPACES_2)})
	public String getVariableDescriptorSize() {
		return super.getVariableDescriptorSize();
	}

	@Override
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = DESCRIPTOR_HEADER_STRING)})
	@StructToken(order=3,length=8)
	public String getName() {
		return super.getName();
	}

	@Override
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = VARIABLES_HEADER_STRING)})
	@StructToken(order=12,length=6)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant = VARIABLES_FOOTER)})
	public String getVariableCountString() {
		return super.getVariableCountString();
	}

	
	
}
