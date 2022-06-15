package org.thshsh.sas.xpt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Observation;
import org.thshsh.sas.Variable;
import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;
import org.thshsh.struct.StructTokenPrefix;
import org.thshsh.struct.StructTokenSuffix;
import org.thshsh.struct.TokenType;

/**
 * This includes the MEMBER header and the NAMESTR header
 *
 */
@StructEntity(charset = LibraryXpt.METADATA_CHARSET_NAME,trimAndPad = true)
public class DatasetXpt extends Dataset {

	public static final String DATASET_HEADER_STRING = "HEADER RECORD*******MEMBER  HEADER RECORD!!!!!!!000000000000000001600000000";
	
	public static final String DESCRIPTOR_HEADER_STRING = "HEADER RECORD*******DSCRPTR HEADER RECORD!!!!!!!000000000000000000000000000000  SAS     ";
	
	public static final String VARIABLES_HEADER_STRING = "HEADER RECORD*******NAMESTR HEADER RECORD!!!!!!!000000";
	
	private static final String VARIABLES_FOOTER = "00000000000000000000  ";

	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = DATASET_HEADER_STRING)})
	@StructToken(order=1,length=3)
	@StructTokenSuffix({@StructToken(type=TokenType.String,constant = "  ")})
	public String descriptorSizeString;
	
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = DESCRIPTOR_HEADER_STRING)})
	@StructToken(order=3,length=8)
	public String name;
	
	@StructToken(order=4,constant = "SASDATA ")
	public String sasType;
	
	@StructToken(order=5,length=8)
	public String version;
	
	@StructToken(order=6,length=8)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant =XptConstants.SPACES_24)})
	public String os;
	
	@StructToken(order=7,length=16)
	public String createdString;
	
	@StructToken(order=8,length=16)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant = XptConstants.SPACES_16)})
	public String modifiedString;
	
	@StructToken(order=9,length=40)
	public String label;
	
	@StructToken(order=10,length=8)
	public String type;

	
	@StructTokenPrefix({@StructToken(type=TokenType.String,constant = VARIABLES_HEADER_STRING)})
	@StructToken(order=12,length=4)
	@StructTokenSuffix({@StructToken(type = TokenType.String,constant = VARIABLES_FOOTER)})
	public String variableCount;
	
	protected List<VariableXpt> variables;
	
	protected long observationStartByte;
	
	public List<VariableXpt> getVariables() {
		if(variables == null) variables = new ArrayList<VariableXpt>();
		return variables;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setVariables(List<? extends Variable> variables) {
		this.variables = (List<VariableXpt>) variables;
	}

	public String getDescriptorSizeString() {
		return descriptorSizeString;
	}

	public void setDescriptorSizeString(String descriptorSizeString) {
		this.descriptorSizeString = descriptorSizeString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCreatedString() {
		return createdString;
	}

	public void setCreatedString(String createdString) {
		this.createdString = createdString;
	}

	public String getModifiedString() {
		return modifiedString;
	}

	public void setModifiedString(String modifiedString) {
		this.modifiedString = modifiedString;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVariableCount() {
		return Integer.valueOf(variableCount);
	}
	
	public Boolean getDescriptor140() {
		return descriptorSizeString.equals("140");
	}
	
	public long getObservationStartByte() {
		return observationStartByte;
	}

	public void setObservationStartByte(long observationStartByte) {
		this.observationStartByte = observationStartByte;
	}

	public Stream<Observation> streamObservations(RandomAccessFileInputStream cs) throws IOException {
		return streamObservations(this, cs);
	}
	
	public static Stream<Observation> streamObservations(DatasetXpt member, InputStream is) {
		Stream<Observation> stream= StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ObservationIteratorXpt(member, is),Spliterator.NONNULL), false);
		return stream;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatasetXpt [");
	
		builder.append("descriptorSizeString=");
		builder.append(descriptorSizeString);
		builder.append(", name=");
		builder.append(name);
		builder.append(", version=");
		builder.append(version);
		builder.append(", os=");
		builder.append(os);
		builder.append(", createdString=");
		builder.append(createdString);
		builder.append(", modifiedString=");
		builder.append(modifiedString);
		builder.append(", label=");
		builder.append(label);
		builder.append(", type=");
		builder.append(type);
		builder.append(", variableCount=");
		builder.append(variableCount);
		builder.append(", observationStartByte=");
		builder.append(observationStartByte);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
