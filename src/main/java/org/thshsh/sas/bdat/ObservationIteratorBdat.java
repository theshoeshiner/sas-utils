package org.thshsh.sas.bdat;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Observation;
import org.thshsh.sas.VariableType;
import org.thshsh.struct.ByteOrder;
import org.thshsh.struct.Struct;
import org.thshsh.struct.TokenType;

public class ObservationIteratorBdat implements Iterator<Observation> {

	
static final Logger LOGGER = LoggerFactory.getLogger(ObservationIteratorBdat.class);
	
/*	public static final String CHARSET = "ISO-8859-1";
	public static final byte SENTINEL = ' ';
	public static final char NO_VALUE = '.';
	public static final Struct<?> IBM = Struct.create(">Q");
	public static final char[] SPECIAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();*/
	
	//int stride = 0;
	//byte[] chunk;
	RandomAccessFileInputStream stream;
	DatasetBdat member;
	Struct<?> struct;
	Boolean hasNext = null;
	Boolean needToRead = true;
//	Stream<Page> pages;
	Iterator<Page> pages;
	Page currentPage;
	int currentBlock = 0;
	int currentBlockCount = 0;
	Observation currentObservation;
	
	
	public ObservationIteratorBdat(DatasetBdat m,RandomAccessFileInputStream in)  {
		
		this.member = m;
		this.stream = in;
		
		
		this.pages = m.getPages().stream().filter(p -> m.getObservationCount(p) > 0).iterator();
		
		
		//StringBuilder formatString = new StringBuilder();
		
		/*for(VariableBdat var : member.getVariables()) {
			stride+= var.getLength();
			formatString.append(var.getLength());
			formatString.append(var.getVariableType() == VariableType.Numeric?"s":"S");
		}
		
		struct = Struct.create(formatString.toString(),Charset.forName(CHARSET));
		
		try {
			IOUtils.skip(is, member.getObservationStartByte());
		} 
		catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		chunk = new byte[stride];
		*/
	}
	
	
	@Override
	public boolean hasNext() {
		readIfNecessary();
		return hasNext;
	}
	
	
	
	protected void readIfNecessary() {
		try {
			if(needToRead) {
				
				if(currentPage == null || currentBlock == currentBlockCount) {
					if(pages.hasNext()) {
						currentPage = pages.next();
						stream.getRandomAccessFile().seek(currentPage.startByte);
						IOUtils.skipFully(stream, 24);
						currentBlock = 0;
						currentBlockCount = member.getObservationCount(currentPage);
						hasNext = true;
					}
					else {
						currentPage = null;
						hasNext = false;
					}
				}
				
				if(currentPage != null) {
					
					
					
					LOGGER.info("reading block {} of page {}",currentBlock,currentPage);
					
					currentObservation = new Observation();
					
					byte[] rowBytes = new byte[member.rowSizeSubHeader.rowLength.intValue()];
					IOUtils.readFully(stream, rowBytes);
					
					//ByteBuffer buffer = ByteBuffer.wrap(row);

					ByteArrayInputStream rowStream = new ByteArrayInputStream(rowBytes);
					
					for(VariableBdat var : member.getVariables()) {
						
						LOGGER.info("Variable: {}",var);
						//buffer.position(var.attributes.offset.intValue());
						rowStream.reset();
						//row.skip(currentBlock)
						
						Object value;
						Integer length = var.getLength();
						VariableType type =  var.getType();
						
						//skip to proper offset
						IOUtils.skipFully(rowStream, var.attributes.offset.longValue());
						
						switch(type) {
						
							case Character:
								
								Struct<?> s = Struct.create(length+"S");
								List<Object> ob = s.unpack(rowStream);
								value = ob.get(0).toString().trim();
								
								break;
							case Numeric:
								
								if(length == 1) {
									value = Struct.unpack(TokenType.Boolean, member.getByteOrder(),rowStream);
								}
								else if(length == 2) {
									value = Struct.unpack(TokenType.Short,member.getByteOrder(), rowStream);
								}
								else if(length == 8) {
									value = Struct.unpack(TokenType.Double,member.getByteOrder(), rowStream);
								}
								else if(length < 8) {
									byte[] src = new byte[length];
									IOUtils.readFully(rowStream, src);
									byte[] full = new byte[] {0,0,0,0,0,0,0,0};
									System.arraycopy(src, 0, full, member.getByteOrder()==ByteOrder.Big?0:8-length, length);
									value = Struct.unpack(TokenType.Double,member.getByteOrder(), full);
								}
								else throw new IllegalArgumentException("Numeric length is > 8");
								
								break;
							default:
								throw new IllegalStateException();
						
						}
						
						LOGGER.info("Value: {}",value);			
						
						currentObservation.putValue(var, value);
						
						
					}
					
					
					currentBlock++;
					
					
				}
				needToRead = false;
				/*int read = is.read(chunk);
				if(read != stride) {
					LOGGER.info("Only read {} bytes",read);
					hasNext = false;
				}
				else {
					hasNext = false;
					for(byte b : chunk) {
						if(b != SENTINEL) {
							hasNext = true;
							break;
						}
					}
				}
				needToRead = false;*/
			}
		} 
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Observation next() {
		
		readIfNecessary();
		needToRead = true;
		
		//Observation ob = new Observation();
		
		
		
		/*List<Object> tokens = struct.unpack(chunk);
		
		if(tokens.size() != member.getVariables().size()) throw new IllegalStateException("Token Count: "+tokens.size()+" Not Equal to Header Count: "+member.getVariables().size());
		
		Observation ob = new Observation();
		
		for(int i=0;i<member.getVariables().size();i++) {
			VariableXpt vm = this.member.getVariables().get(i);
			Object val = tokens.get(i);
			if(vm.getVariableType() == VariableType.Numeric) val = ObservationIteratorXpt.ibm_to_ieee((byte[]) val);
			ob.putValue(vm, val);
		}*/
		
		return currentObservation;

	}
	
}
