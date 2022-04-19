package org.thshsh.sas.v56;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Observation;

public class Member extends Dataset {

	protected static final Logger LOGGER = LoggerFactory.getLogger(Member.class);

	
	// the header record
	protected MemberHeader header;
	//the byte in the file where this member starts
	protected int startByte;
	//the byte where the member header ends and the observations start
	protected int observationByte;
	//the byte where the observations end
	protected int endByte;
	//the records
	protected List<Observation> observations = new ArrayList<Observation>();
	

	public Member(MemberHeader header,int startByte, int observationByte) {
		super();
		this.header = header;
		this.startByte = startByte;
		this.observationByte = observationByte;

	}

	public Stream<Observation> streamObservations(InputStream cs) throws IOException {
		return Observation.streamObservations(this, cs);
	}

	public int getStartByte() {
		return startByte;
	}

	public void setStartByte(int startByte) {
		this.startByte = startByte;
	}

	public int getObservationByte() {
		return observationByte;
	}

	public void setObservationByte(int observationByte) {
		this.observationByte = observationByte;
	}

	public int getEndByte() {
		return endByte;
	}

	public void setEndByte(int endByte) {
		this.endByte = endByte;
	}

	public MemberHeader getHeader() {
		return header;
	}

	public void setHeader(MemberHeader header) {
		this.header = header;
	}
	
	

	public List<Observation> getObservations() {
		return observations;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[header=");
		builder.append(header);
		builder.append(", startByte=");
		builder.append(startByte);
		builder.append(", observationByte=");
		builder.append(observationByte);
		builder.append(", endByte=");
		builder.append(endByte);
		builder.append("]");
		return builder.toString();
	}



	public static Member from_bytes(Matcher matcher) {
		
		MemberHeader memberHeader = MemberHeader.from_matcher(matcher);
		
		Member m = new Member(memberHeader,matcher.start(),matcher.end());

		return m;
	}
	
}
