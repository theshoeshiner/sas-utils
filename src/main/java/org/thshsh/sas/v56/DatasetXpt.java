package org.thshsh.sas.v56;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.thshsh.sas.Dataset;
import org.thshsh.sas.Observation;
import org.thshsh.sas.Variable;
import org.thshsh.struct.StructEntity;
import org.thshsh.struct.StructToken;

@StructEntity(charset = LibraryXpt.METADATA_CHARSET_NAME,trimAndPad = true)
public class DatasetXpt extends Dataset {
	
	/*
	 public static Pattern pattern = Pattern.compile(
	        //# Header line 1
	        "HEADER RECORD\\*{7}MEMBER  HEADER RECORD\\!{7}0{17}160{8}(?<descriptorsize>140|136)  HEADER RECORD\\*{7}DSCRPTR HEADER RECORD\\!{7}0{30} {2}"
	        //# Header line 3
	        +"SAS {5}(?<name>.{8})SASDATA (?<version>.{8})(?<os>.{8}) {24}(?<created>.{16})"
	        //# Header line 4
	        +"(?<modified>.{16}) {16}"
	        +"(?<label>.{40})(?<type>    DATA|    VIEW| {8})"
	        //# Namestrs
	        +"HEADER RECORD\\*{7}NAMESTR HEADER RECORD\\!{7}0{6}"
	        +"(?<nvariables>.{4})0{20} {2}"
	        +"(?<namestrs>.*?)"
	        +"HEADER RECORD\\*{7}OBS {5}HEADER RECORD\\!{7}0{30} {2}",
	       // # Observations ... until EOF or another Member.
	        Pattern.DOTALL
	    );
	 */

	public static final String HEADER_STRING = "                HEADER RECORD*******MEMBER  HEADER RECORD!!!!!!!000000000000000001600000000";
	
	@StructToken(order=0,length=75,prefix = 16)
	public String header;
	
	@StructToken(order=1,length=3,suffix = 90)
	public String descriptorSizeString;
	
	@StructToken(order=2,length=8,suffix = 8)
	public String name;
	
	@StructToken(order=3,length=8)
	public String version;
	
	@StructToken(order=4,length=8,suffix = 24)
	public String os;
	
	@StructToken(order=5,length=16)
	public String createdString;
	
	@StructToken(order=6,length=16,suffix = 16)
	public String modifiedString;
	
	@StructToken(order=7,length=40)
	public String label;
	
	@StructToken(order=8,length=8,suffix = 54)
	public String type;
	
	@StructToken(order=9,length=4,suffix = 22)
	public String variableCount;
	
	protected List<VariableXpt> variables;
	
	protected long observationStartByte;
	
	public List<VariableXpt> getVariables() {
		if(variables == null) variables = new ArrayList<VariableXpt>();
		return variables;
	}
	
	@Override
	public void setVariables(List<? extends Variable> variables) {
		this.variables = (List<VariableXpt>) variables;
	}
	
	

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
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

	public Stream<Observation> streamObservations(InputStream cs) throws IOException {
		return streamObservations(this, cs);
	}
	
	public static Stream<Observation> streamObservations(DatasetXpt member, InputStream is) {
		Stream<Observation> stream= StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ObservationIteratorXpt(member, is),Spliterator.NONNULL), false);
		return stream;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatasetXpt [header=");
		builder.append(header);
		builder.append(", descriptorSizeString=");
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
