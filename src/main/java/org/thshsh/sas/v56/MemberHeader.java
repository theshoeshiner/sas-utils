package org.thshsh.sas.v56;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MemberHeader {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberHeader.class);

	
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
	
	protected String name;
	protected String label;
	protected String type;
	protected String os;
	protected String version;
	protected LocalDateTime created;
	protected LocalDateTime modified;
	protected List<Variable> variables;
	
	
	
	public MemberHeader(String name, String label, String type, String os, String version, LocalDateTime created,
			LocalDateTime modified, List<Variable> names) {
		super();
		this.name = name.trim();
		this.label = label.trim();
		this.type = type.trim();
		this.os = os.trim();
		this.version = version.trim();
		this.created = created;
		this.modified = modified;
		this.variables = names;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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



	public String getOs() {
		return os;
	}



	public void setOs(String os) {
		this.os = os;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public LocalDateTime getCreated() {
		return created;
	}



	public void setCreated(LocalDateTime created) {
		this.created = created;
	}



	public LocalDateTime getModified() {
		return modified;
	}



	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}



	public List<Variable> getVariables() {
		return variables;
	}



	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}



	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[name=");
		builder.append(name);
		builder.append(", label=");
		builder.append(label);
		builder.append(", type=");
		builder.append(type);
		builder.append(", os=");
		builder.append(os);
		builder.append(", version=");
		builder.append(version);
		builder.append(", created=");
		builder.append(created);
		builder.append(", modified=");
		builder.append(modified);
		builder.append("]");
		return builder.toString();
	}



	public static MemberHeader from_matcher(Matcher matcher) {
		
		//Matcher memHeadMatcher = MemberHeader.pattern.matcher(cs);
		int numvars = Integer.valueOf(matcher.group("nvariables"));
		LOGGER.info("numvars: {}",numvars);
		
		String namstrs = matcher.group("namestrs");
		//LOGGER.info("namstrs: {}",namstrs);
		LOGGER.info("length: {}",namstrs.length());
		
		int desc_size = Integer.valueOf(matcher.group("descriptorsize"));
		LOGGER.info("descriptorsize: {}",desc_size);
		
		List<Variable> variables = new ArrayList<Variable>();
		
		for(int i=0;i<numvars;i++) {
			String chunk = namstrs.substring(i*desc_size, i*desc_size+desc_size);
			Variable ns =Variable.fromBytes(chunk.getBytes());
			LOGGER.info("Variable: {}",ns);
			variables.add(ns);
		}
		
		
		MemberHeader mh = new MemberHeader(
				matcher.group("name"), 
				matcher.group("label"), 
				matcher.group("type"), 
				matcher.group("os"), 
				matcher.group("version"), 
				LibraryV56.parseDateTime(matcher.group("created")),
				LibraryV56.parseDateTime(matcher.group("modified")),
				variables);
		
		return mh;
	}
	
	/*	public static MemberHeader from_bytes(CharSequence cs) {
			
			Matcher memHeadMatcher = MemberHeader.pattern.matcher(cs);
			 int n = Integer.valueOf(memHeadMatcher.group("nvnvariables"));
		}*/
	
		/*int startByte;
		int observationByte;
		int endByte;
		
		
		public Member(int startByte, int observationByte) {
			super();
			this.startByte = startByte;
			this.observationByte = observationByte;
		}
		
		
		public static Member from_bytes(CharSequence is,int startByte, int obsByte) {
			 Member m = new Member(startByte,obsByte);
			 return m;
		}*/
	
	
}
