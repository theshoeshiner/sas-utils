package org.thshsh.sas.bdat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
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

	protected RandomAccessFileInputStream stream;
	protected DatasetBdat dataset;
	
	protected Iterator<Page> pageIterator;
	protected Iterator<Observation> currentPageObservationIterator;
	
	
	
	@SuppressWarnings("unchecked")
	public ObservationIteratorBdat(DatasetBdat dataset,RandomAccessFileInputStream stream)  {
		
		this.dataset = dataset;
		this.stream = stream;
		this.pageIterator = dataset.getPages().stream().filter(p -> p.getTotalObservationCount() > 0).iterator();
		
		if(pageIterator.hasNext()) {
			nextPage();
		}
		else {
			currentPageObservationIterator = IteratorUtils.EMPTY_ITERATOR;
		}
		
	}

	
	@Override
	public boolean hasNext() {
		return pageIterator.hasNext() || currentPageObservationIterator.hasNext();
	}

	@Override
	public Observation next() {
		Observation observation = currentPageObservationIterator.next();
		if(!currentPageObservationIterator.hasNext()) {
			if(pageIterator.hasNext()) {
				nextPage();
			}
		}
		return observation;
	}
	
	@SuppressWarnings("unchecked")
	protected void nextPage() {
		Page currentPage = pageIterator.next();
		LOGGER.info("nextPage: {}",currentPage);
		DataSubHeaderIterator dshi = new DataSubHeaderIterator(dataset, currentPage, stream);
		DataBlockIterator dbi = new DataBlockIterator(dataset,currentPage,stream);
		currentPageObservationIterator = IteratorUtils.chainedIterator(dshi, dbi);
	}
	
	
	
	public static Observation readRowFromStream(DatasetBdat member, RandomAccessFileInputStream stream, Integer rowDataLength) throws IOException {
		
		LOGGER.info("readRowFromStream comp: {}",member.getCompression());
		
		byte[] rowBytes = new byte[rowDataLength];
		IOUtils.readFully(stream, rowBytes);
		
		if(rowDataLength < member.getRowLength()) {
			if(member.getCompressed()) {
				//this data is compressed
				Compressor compressor;
				switch(member.getCompressionAlgorithm()) {
				case SASYZCR2:
					compressor = new RdcCompressor();
					break;
				case SASYZCRL:
					compressor = new RleCompressor();
					break;
				default:
					throw new IllegalArgumentException("Compression unknown");
				}
				
				LOGGER.info("decompressing data using: {}",compressor);
				
				rowBytes = compressor.decompressRow(member.getRowLength(), rowBytes);
			}
			else {
				throw new IllegalArgumentException("Row data length ("+rowDataLength+") should equal dataset row length ("+member.getRowLength()+") for uncompressed files");
			}

		}
		
		Observation currentObservation = new Observation();
		
		
		
		//ByteBuffer buffer = ByteBuffer.wrap(row);

		ByteArrayInputStream rowStream = new ByteArrayInputStream(rowBytes);
		
		for(VariableBdat var : member.getVariables()) {
			
			//LOGGER.info("Variable: {}",var);
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
			
			LOGGER.info("Value: {} for Variable: {}",value,var);			
			
			currentObservation.putValue(var, value);
			
			
		}
		
		
		
		return currentObservation;
	}
	
	public static class DataBlockIterator implements Iterator<Observation> {
		
		private static final Logger LOGGER = LoggerFactory.getLogger(ObservationIteratorBdat.DataBlockIterator.class);

		protected DatasetBdat dataset;
		protected Page page;
		protected RandomAccessFileInputStream stream;
		protected Integer blockCount;
		protected Integer blockIndex = 0;
		
		Observation currentObservation;
		
		public DataBlockIterator(DatasetBdat dataset,Page page,RandomAccessFileInputStream in)  {
			this.dataset = dataset;
			this.page = page;
			this.stream = in;
			this.blockCount = page.getBlockObservationCount();
		
		}

		@Override
		public boolean hasNext() {
			//as long as there are more data subheaders then we have more observations
			return blockIndex < blockCount;
		}

		@Override
		public Observation next() {
			try {
				
				
				stream.getRandomAccessFile().seek(
						page.startByte + 
						PageHeader.STRUCT.byteCount() + 
						(SubHeaderPointer.STRUCT.byteCount() * page.getSubHeaderCount()) +
						(dataset.rowSizeSubHeader.rowLength.intValue() * blockIndex)
						);
				
				LOGGER.info("next block: {}",blockIndex);
				
				currentObservation = readRowFromStream(dataset, stream,dataset.getRowLength());
				blockIndex++;
				return currentObservation;
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		
	}
	
	public static class DataSubHeaderIterator implements Iterator<Observation> {
		
		
		private static final Logger LOGGER = LoggerFactory.getLogger(ObservationIteratorBdat.DataSubHeaderIterator.class);

		
		protected DatasetBdat dataset;
		protected Page page;
		protected RandomAccessFileInputStream stream;
		protected Iterator<SubHeaderPointer> dataSubHeadersIterator;

		public DataSubHeaderIterator(DatasetBdat dataset,Page page,RandomAccessFileInputStream in)  {
			this.dataset = dataset;
			this.page = page;
			this.stream = in;
			this.dataSubHeadersIterator = page.getDataSubHeaderPointers().iterator();
		}

		@Override
		public boolean hasNext() {
			//as long as there are more data subheaders then we have more observations
			return dataSubHeadersIterator.hasNext();
		}

		@Override
		public Observation next() {

			try {
				SubHeaderPointer pointer = dataSubHeadersIterator.next();
				stream.getRandomAccessFile().seek(page.startByte + pointer.getPageOffset());
		
				LOGGER.info("reading row from subheader: {}",pointer);
				
				return readRowFromStream(dataset, stream,pointer.length);
				
			} 
			catch (IOException e) {
				throw new IllegalStateException(e);
			}
			
		}
		
	}
}
