package org.thshsh.sas.bdat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.struct.Struct;
import org.thshsh.struct.TokenType;

public class ParserBdat {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ParserBdat.class);

	public static LibraryBdat parseLibrary(File f) throws IOException, InstantiationException, IllegalAccessException {

		RandomAccessFile raf = new RandomAccessFile(f, "r");
		RandomAccessFileInputStream stream = new RandomAccessFileInputStream(raf);

		return parseLibrary(stream);
	}

		public static LibraryBdat parseLibrary(RandomAccessFileInputStream stream) throws IOException, InstantiationException, IllegalAccessException {
	
			//RandomAccessFile raf = stream.getRandomAccessFile();
			
			DatasetBdat header = parseDataset(stream);
			
			LibraryBdat lib = new LibraryBdat();
			lib.datasets.add(header);
	
			return lib;
	
		
		}
	
	public static DatasetBdat parseDataset(RandomAccessFileInputStream stream) throws IOException, InstantiationException, IllegalAccessException {

		try {

			DatasetBdat dataset = new DatasetBdat();

			RandomAccessFile raf = stream.getRandomAccessFile();

			//byte[] buffer;

			dataset.setHeader1(Header1.STRUCT.unpackEntity(stream));
			//if(!Arrays.equals(MAGIC, dataset.header1.magic)) throw new InvalidFormatException("Invalid Header for SAS7BDAT format");

			IOUtils.skip(stream, dataset.getHeader1Padding());

			dataset.setHeader2(Header2.STRUCT.unpackEntity(stream));
			LOGGER.info("Header2: {}", dataset.header2);

			IOUtils.skip(stream, dataset.getHeader2Padding());

			Header3 h3 = Header3.STRUCT.unpackEntity(stream);
			LOGGER.info("h3: {}", h3);

			//python parse_metadata

			LOGGER.info("jumping to end of page header: from {} to {}", raf.getFilePointer(), dataset.header2.headerSize);

			byte[] padding = (byte[]) Struct.unpack(TokenType.Bytes, (int) (dataset.header2.headerSize - raf.getFilePointer()),
					dataset.getByteOrder(), Charset.defaultCharset(), stream);

			LOGGER.info("Header Padding: {}", padding.length);

			for (int p = 0; p < dataset.header2.pageCount; p++) {

				long mark = raf.getFilePointer();
				LOGGER.info("page: {}", p);

				Page page = new Page();
				page.startByte = raf.getFilePointer();
				dataset.getPages().add(page);
				LOGGER.info("page start: {}", page.startByte);
				LOGGER.info("page end: {}", page.startByte+dataset.header2.pageSize);
				
				LOGGER.info("potential sub headers: {}", dataset.header2.pageSize);

				
				
				//python process_page_meta
				{

					//python read_page_header
					{

						//IOUtils.skip(stream, dataset.getPageOffset());

						PageHeader pageHeader = PageHeader.STRUCT.unpackEntity(stream);
						page.setHeader(pageHeader);

						LOGGER.info("PageHeader: {}", pageHeader);

					}

					LOGGER.info("Page: {}", page);

					if (page.getPageType().meta) {
						
						
						if(page.getSubHeaderCount()>0) {
						
							LOGGER.info("process {} sub header pointers",page.getSubHeaderCount());
	
							for (int i = 0; i < page.getSubHeaderCount(); i++) {
							
								page.getSubHeaderPointers().add(processSubHeaderPointer(dataset, stream));
							
							}
						
						}
						else {
							LOGGER.info("Processing missing sub header pointers");
							/*
							for (int i = 0; i < 10; i++) {
								
								page.getSubHeaderPointers().add(processSubHeaderPointer(dataset, stream));
							
							}*/
						}
						

						LOGGER.info("current position: {}", raf.getFilePointer());

						//processSubHeaders(stream, dataset, raf, page);

						//process subheaderpointers
						for (SubHeaderPointer pointer : page.getSubHeaderPointers()) {

							LOGGER.info("POINTER: {}", pointer);
							

							raf.seek(page.startByte + pointer.getOffset().longValue());
							
							LOGGER.info("position: {} length: {} to: {}", raf.getFilePointer(),pointer.getLength(),pointer.getLength()+raf.getFilePointer());
							//LOGGER.info("length: {}", pointer.getLength());
							//LOGGER.info("to: {}", pointer.getLength()+raf.getFilePointer());

							//unpack signature
							Number signature = (Number) Struct.unpack(dataset.getIntegerTokenType(), stream);
							SubHeaderSignature index = SubHeaderSignature.fromId(signature.intValue());

							LOGGER.info("Signature: {}", index);

							//LOGGER.info("position: {}", raf.getFilePointer());
							
							/*if(dataset.compression != null && index == null 
									&& (pointer.compressionTypeId ==  SubHeaderPointer.COMPRESSED_SUBHEADER_ID || pointer.compressionTypeId  == 0)
									&& pointer.typeId == SubHeaderPointer.COMPRESSED_SUBHEADER_TYPE
									) {
								index = SubHeaderSignature.Data;
							}*/
							//subHeaderProps.index = index;
							pointer.setSignature(index);

							if (pointer.getSignature() != null) {

								if (pointer.getSignature() != SubHeaderSignature.Data) {

									//NOTE these methods MUST NOT alter the file stream position
									switch (pointer.getSignature()) {
									case ColumnAttributes:
										processColumnAttributesSubHeader(dataset, page, pointer, stream);
										break;
									case ColumnName:
										processColumnNameSubHeader(dataset, page, pointer, stream);
										break;
									case String:
										processTextSubHeader(dataset, page, pointer, stream);
										break;
									case ColumnSize:
										processColumnCountSubHeader(dataset, page, pointer, stream);
										break;
									case RowSize:
										processRowSizeSubHeader(dataset, page, pointer, stream);
										break;
									case FormatAndLabel:
										processFormatAndLabelSubHeader(dataset, page, pointer, stream);
										break;
									case Data:
										break;
									/*case DATA_SUBHEADER_INDEX:
										processDataSubHeader(dataset,page,subHeaderProps,stream);
										break;*/
									//NOOPS
									case SubHeaderCount:
										break;
									case ColumnList:
										break;
									default:
										break;

									}

									/*if(subHeader.index.subHeaderClass!=null) {
										SubHeader sh = subHeader.index.subHeaderClass.newInstance();
										sh.process(header,page, subHeader, stream);
									}*/

								} else {

								}
							}

							//LOGGER.info("index: {}",index);

						}

					}

					//python readlines
					//for(int i=0;i<header.rowCount;i++) {

					//TODO handle mixed type page
					//TODO skip data pages unless we are iterating observations

					if (page.getPageType() == PageType.Data || page.getPageType() == PageType.Mixed1 || page.getPageType() == PageType.Mixed2) {

						//processDataSubHeader(dataset, page, null, stream);

					}
					/*if(page.pageType == PageType.Mixed1 || page.pageType == PageType.Mixed2) {
						
					}*/

					long endOfPage = mark + dataset.header2.pageSize;
					LOGGER.info("moving file pointer from {} to {}", raf.getFilePointer(), endOfPage);
					raf.seek(endOfPage);
					//LOGGER.info("pointer1: {}",raf.getFilePointer());
					//stream.reset();
					//jump to end of page
					//IOUtils.skip(stream, dataset.header2.pageSize);
					//LOGGER.info("pointer2: {}",raf.getFilePointer());

				}

			}

			//LOGGER.info("columnNames: {}",dataset.columnNames);
			//LOGGER.info("columnLabels: {}",dataset.columnLabels);
			//LOGGER.info("columnFormats: {}",dataset.columnFormats);

			return dataset;

		} finally {
			//input.close();
		}

	}

	/*private static void processSubHeaders(RandomAccessFileInputStream stream, DatasetBdat dataset, RandomAccessFile raf, Page page)
			throws IOException {
		//python process_page_metadata
		LOGGER.info("process metadata");
	
		for(int i=0;i<page.getSubHeaderCount();i++) {
			
			long subHeaderMark = raf.getFilePointer();
			
			LOGGER.info("sub header: {}",i);
			
			//SubHeaderProperties subHeaderProps = new SubHeaderProperties();
			
			SubHeaderPointer subHeaderProps;
			//python process_subheader_pointers
			{
			
				Struct<? extends SubHeaderPointer> subHeaderStruct = Struct.create((Class<? extends SubHeaderPointer>)(dataset.get64Bit()?SubHeaderPointer64.class:SubHeaderPointer32.class));
				//Struct<? extends SubHeader> subHeaderStruct = Struct.create(SubHeader64.class);
				LOGGER.info("subHeaderStruct: {}",subHeaderStruct);
				
				subHeaderProps = subHeaderStruct.unpackEntity(stream);
				LOGGER.info("subHeader: {}",subHeaderProps);
				
				//Struct<?> s = Struct.create("2s"+dataset.properties.integerStructFormat+dataset.properties.integerStructFormat+"cc");
				//List<Object> subHeaderUnpacked = s.unpack(stream);
				//LOGGER.info("subHeaderUnpacked: {}",subHeaderUnpacked);
				
	
				subHeaderProps.offset = ((Number) subHeaderUnpacked.get(1)).longValue();
				subHeaderProps.length = ((Number) subHeaderUnpacked.get(2)).longValue();
				subHeaderProps.compression = (Byte) subHeaderUnpacked.get(3);
				subHeaderProps.type = (Byte) subHeaderUnpacked.get(4);
				
			}
			
			LOGGER.info("position: {}",raf.getFilePointer());
			
			if(subHeaderProps.compressionTypeId != TRUNCATED_SUBHEADER_ID) {
				
				//python read_subheader_signature
				List<Object> signature;
				{
					
					long seekTo = subHeaderProps.getOffset().longValue()+page.startByte;
					LOGGER.info("Seek from: {} to: {}",raf.getFilePointer(),seekTo);
					
					//Struct<?> sigTestStruct =Struct.create("4s");
					
					//signature = LibraryBdat.seekAndRead(stream,seekTo,sigTestStruct);
					//LOGGER.info("signature bytes: {}",Hex.encodeHexString((byte[]) signature.get(0)));
					
					
					
					Struct<?> sigStruct =Struct.create(dataset.properties.integerStructFormat);
					//Struct<?> sigStruct =Struct.create("i");
					
					signature = LibraryBdat.seekAndRead(stream,subHeaderProps.getOffset().longValue()+page.startByte,sigStruct);
					
					LOGGER.info("signature: {}",signature);
					
				}
				
				
				
				//python get_subheader_class
				{
					SubHeaderSignature index = SubHeaderSignature.fromId(((Number)signature.get(0)).intValue());
					LOGGER.info("signature index: {}:",index);
					
					if(dataset.compression != null && index == null 
							&& (subHeaderProps.compressionTypeId ==  SubHeaderPointer.COMPRESSED_SUBHEADER_ID || subHeaderProps.compressionTypeId  == 0)
							&& subHeaderProps.typeId == SubHeaderPointer.COMPRESSED_SUBHEADER_TYPE
							) {
						index = SubHeaderSignature.Data;
					}
					//subHeaderProps.index = index;
					subHeaderProps.setSignature(index);
				}
				
				
				if(subHeaderProps.getSignature() != null) {
					
					if(subHeaderProps.getSignature() != SubHeaderSignature.Data) {
						
						//NOTE these methods MUST NOT alter the file stream position
						switch(subHeaderProps.getSignature()) {
							case ColumnAttributes:
								processColumnAttributesSubHeader(dataset, page, subHeaderProps, stream);
								break;
							case ColumnName:
								processColumnNameSubHeader(dataset, page, subHeaderProps, stream);
								break;
							case ColumnSize:
								processColumnCountSubHeader(dataset, page, subHeaderProps, stream);
								break;
							case String:
								processStringsSubHeader(dataset, page, subHeaderProps, stream);
								break;
							case FormatAndLabel:
								processFormatAndLabelSubHeader(dataset, page, subHeaderProps, stream);
								break;
							case RowSize:
								processRowSizeSubHeader(dataset, page, subHeaderProps, stream);
								break;
							case Data:
								processDataSubHeader(dataset,page,subHeaderProps,stream);
								break;
							//NOOPS
							case SubHeaderCount:
								break;
							case ColumnList:
								break;
							default:
								break;
						
						}
						
						if(subHeader.index.subHeaderClass!=null) {
							SubHeader sh = subHeader.index.subHeaderClass.newInstance();
							sh.process(header,page, subHeader, stream);
						}
						
						
					}
					else {
						
					}
				}
				
				
				
				
			}
	
		}
	}*/
	
	protected static SubHeaderPointer processSubHeaderPointer(DatasetBdat dataset,RandomAccessFileInputStream stream) throws IOException {
		
		SubHeaderPointer subHeaderPointer;
		//python process_subheader_pointers
		{


			subHeaderPointer = SubHeaderPointer.STRUCT.unpackEntity(stream);
			LOGGER.info("subHeader: {}", subHeaderPointer);

			return subHeaderPointer;

		}
		
	}

	protected static void processRowSizeSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {

		RowSizeSubHeader rowSize = RowSizeSubHeader.STRUCT.unpackEntity(stream);

		LOGGER.info("rowSize: {}", rowSize);

		pointer.subHeader = rowSize;
		dataset.rowSizeSubHeader = rowSize;

	}

	protected static void processColumnCountSubHeader(DatasetBdat header, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {
		
		ColumnSizeSubHeader subheader = Struct.create(ColumnSizeSubHeader.class).unpackEntity(stream);

		LOGGER.info("ColumnSizeSubHeader: {}", subheader);
		if (subheader.numColumns.intValue() != header.rowSizeSubHeader.getColumnCount()) {
			throw new IllegalArgumentException("Column Count Mismatch");
		}


	}

	protected static void processTextSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {

		LOGGER.info("processTextSubHeader");
		
		//SasConstants.debugBytes(stream, 100);
		

		TextSubHeader subHeader = TextSubHeader.STRUCT.unpackEntity(stream);
		subHeader.dataset = dataset;
		LOGGER.info("TextSubHeader: {}", subHeader);
		pointer.subHeader = subHeader;
		Compression comp = Compression.STRUCT.unpackEntity(stream);
		subHeader.compression = comp;
		
		LOGGER.info("compression: {}", comp);
		
		//LOGGER.info("Compression: {}", );

		Integer stringLength;
		Integer stringOffset = TextSubHeader.STRUCT.byteCount() + Compression.STRUCT.byteCount();
		
		if(StringUtils.isBlank(comp.compression)) {
			//file is not compressed, LCS = 0;
			if(dataset.rowSizeSubHeader.creatorProcLength > 0) {
				subHeader.creatorProcess =  (String) Struct.unpack(TokenType.String, dataset.rowSizeSubHeader.creatorProcLength, StandardCharsets.US_ASCII, stream);
				//start = start - (TextSubHeader.STRUCT.byteCount() + Compression.STRUCT.byteCount() + dataset.rowSizeSubHeader.creatorProcLength);
				//int offset = (TextSubHeader.STRUCT.byteCount() + Compression.STRUCT.byteCount() + dataset.rowSizeSubHeader.creatorProcLength);
				stringOffset += dataset.rowSizeSubHeader.creatorProcLength;
				stringLength = subHeader.length - Compression.STRUCT.byteCount() - dataset.rowSizeSubHeader.creatorProcLength;
			}
			else {
				throw new NotImplementedException("Compression was: "+subHeader.compression);
			}
		}
		else {
			//throw new NotImplementedException("Compression was: "+subHeader.compression);
			if(dataset.rowSizeSubHeader.creatorSoftwareLength > 0) {
				String creatorSoftware = comp.compression.substring(0, dataset.rowSizeSubHeader.creatorSoftwareLength);
				LOGGER.info("creatorSoftware: {}",creatorSoftware);
				//int offset = (TextSubHeader.STRUCT.byteCount() + Compression.STRUCT.byteCount());
				//subHeader.offset = 0-offset;
				stringLength = subHeader.length - Compression.STRUCT.byteCount() - dataset.rowSizeSubHeader.creatorSoftwareLength;
			}
			else throw new NotImplementedException("Compression was: "+subHeader.compression);
			
		}

		subHeader.offset = 0-stringOffset;
		String strings =  (String) Struct.unpack(TokenType.String, stringLength, StandardCharsets.US_ASCII, stream);
		LOGGER.info("text: '{}'",strings);
		subHeader.string = strings;

	}

	//public static int SUB_HEADER_POINTER_LENGTH = 8;

	protected static void processColumnNameSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {


		ColumnNamesSubHeader subHeader = Struct.create(ColumnNamesSubHeader.class).unpackEntity(stream);
		pointer.subHeader = subHeader;
		
		LOGGER.info("ColumnNamesSubHeader: {}", subHeader);

		dataset.columnNamesSubHeader = subHeader;

		LOGGER.info("header length: {}", pointer.length);

		int count = (subHeader.remainingLength-8)/ColumnName.STRUCT.byteCount();

		LOGGER.info("column name count: {}", count);

		//skip the first 2 integers
		//IOUtils.skipFully(stream, dataset.getIntegerLength() * 2);

		for (int i = 0; i < count; i++) {
			ColumnName cns = ColumnName.STRUCT.unpackEntity(stream);
			cns.dataset = dataset;
			LOGGER.info("ColumnName: {}", cns);
			LOGGER.info("Name: {}", cns.getName());
			subHeader.getColumnNames().add(cns);

		}

	}

	protected static void processColumnAttributesSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer,RandomAccessFileInputStream stream) throws IOException {

		//		long mark = stream.getRandomAccessFile().getFilePointer();

		LOGGER.info("processColumnAttributesSubHeader");
		
		

		ColumnAttributesSubHeader subHeader = ColumnAttributesSubHeader.STRUCT.unpackEntity(stream);
		dataset.columnAttributesSubHeader = subHeader;
		pointer.subHeader = subHeader;
		
		LOGGER.info("ColumnAttributesSubHeader {}",subHeader);

		
		//int count = (int) ((pointer.getLength().longValue() - 2 * dataset.getIntegerLength() - 12) / s.byteCount());
		int count = (subHeader.remainingLength-8)/ColumnAttributes.STRUCT.byteCount();

		LOGGER.info("count: {}", count);
		
	

		//stream.getRandomAccessFile().seek(pointer.getOffset().longValue() + page.startByte + header.properties.integerBytesLength+8);

		//skip the first 2 integers
		//IOUtils.skipFully(stream, dataset.getIntegerLength() * 2);

		//LOGGER.info("page.startByte: {}",page.startByte);
		//LOGGER.info("position: {}",stream.getRandomAccessFile().getFilePointer());

		//Struct<?> s = Struct.create(header.properties.integerStructFormat+header.properties.integerStructFormat+"2scs");

		for (int i = 0; i < count; i++) {
			ColumnAttributes ca = ColumnAttributes.STRUCT.unpackEntity(stream);
			LOGGER.info("ColumnAttributes: {}", ca);
			//LOGGER.info("ColumnAttributes skip: {}", Hex.encodeHexString(ca.unknown));
			//			LOGGER.info("ColumnAttributes skip: {} skip2: {}",Hex.encodeHex(ca.skip),ca.skip2);
			subHeader.getColumnAttributes().add(ca);
			/*List<Object> objects = s.unpack(stream);
			LOGGER.info("column {} objects: {}",i,objects);
			header.columnDataOffset.add((int)objects.get(0));
			header.columnDataLength.add((int)objects.get(1));
			header.columnDataType.add(VariableType.values()[(byte) objects.get(3)-1]);*/
		}

		//stream.getRandomAccessFile().seek(mark);
	}

	protected static void processFormatAndLabelSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)
			throws IOException {

		LOGGER.info("processFormatAndLabelSubHeader");

		Struct<FormatAndLabelSubHeader> s = Struct.create(FormatAndLabelSubHeader.class);
		FormatAndLabelSubHeader fal = s.unpackEntity(stream);
		fal.dataset = dataset;
		pointer.subHeader = fal;
		
		dataset.formatAndLabels.add(fal);
		
		LOGGER.info("FormatAndLabelSubHeader: {}", fal);
		LOGGER.info("format: {}", fal.getFormat());
		LOGGER.info("label: {}", fal.getLabel());
		
	}

	protected static void processDataSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)
			throws IOException {

		LOGGER.info("processDataSubHeader");

		if (dataset.compression) {
			//TODO
			throw new NotImplementedException();
		}

		//skip 24 bytes
		IOUtils.skipFully(stream, 24);

		//long mark = stream.getRandomAccessFile().getFilePointer();

		//long baseOffset = 24;
		/*if(pointer != null) {
			if(pointer != null) baseOffset += pointer.getOffset().longValue();
			//stream.getRandomAccessFile().seek(offset);
		}*/

		//int rowCount = page.getBlockCount();
		//long colCount = dataset.getVariableCount();
		//LOGGER.info("rowCount: {}", rowCount);

		/*for(int r=0;r<rowCount;r++) {
			
			for(VariableBdat var : dataset.getVariables()) {
				
			}
			for(int i=0;i<colCount;i++) {
				
				
			}
			
		}*/

		/*	if(page.getPageType() == PageType.Mixed1 || page.getPageType() == PageType.Mixed2) {
				//baseOffset += page.subHeaderCount * header.properties.
				
				baseOffset += dataset.getSubHeaderPointerLength() * page.getSubHeaderCount();
				rowCount = dataset.rowSizeSubHeader.mixedPageRowCount.intValue();
				
				if(dataset.properties.alignCorrect) {
					//TODO
				}
			}
			*/
		/*
		
		LOGGER.info("page block count: {}",page.getBlockCount());
		LOGGER.info("baseOffset: {}",baseOffset);
		
		for(int r=0;r<rowCount;r++) {
		
			
			long rowBaseOffset = baseOffset + r* dataset.rowSizeSubHeader.rowLength.longValue();
			LOGGER.info("row: {} offset: {}",r,rowBaseOffset);
			
		for(int i=0;i<dataset.columnCount;i++) {
			Integer length = dataset.columnDataLength.get(i);
			FormatType formatType = dataset.columnFormats.get(i);
			if(length == 0) break;
			
			long offset = dataset.columnDataOffset.get(i) + rowBaseOffset + page.startByte;
		
			stream.getRandomAccessFile().seek(offset);
		
			VariableType type = dataset.columnDataType.get(i);
			Object value = null;
			if(type == VariableType.Numeric) {
				if(length == 1) {
					value = Struct.unpack(TokenType.Boolean, dataset.getByteOrder(),stream);
				}
				else if(length == 2) {
					value = Struct.unpack(TokenType.Short,dataset.getByteOrder(), stream);
				}
				else if(length == 8) {
					value = Struct.unpack(TokenType.Double,dataset.getByteOrder(), stream);
				}
				else if(length < 8) {
					byte[] src = new byte[length];
					IOUtils.readFully(stream, src);
					byte[] full = new byte[] {0,0,0,0,0,0,0,0};
					System.arraycopy(src, 0, full, dataset.getByteOrder()==ByteOrder.Big?0:8-length, length);
					value = Struct.unpack(TokenType.Double,dataset.getByteOrder(), full);
				}
				else throw new IllegalArgumentException("Numeric length is > 8");
				
				LOGGER.info("Numeric Value: {}",value);
				
				
				if(formatType != null) {
					
					LOGGER.info("format was: {}",formatType);
					
		
					switch(formatType) {
						case YYMMDD:
						case DDMMYY:
						case JULIAN:
						case MMDDYY:
						case MONYY:
						case DATE:
							value = EPOCH.toLocalDate().plusDays(((Double)value).longValue());
							break;
						case DATETIME:
							value = EPOCH.plusSeconds(((Double)value).longValue());
							break;
						case TIME:
							value = LocalTime.MIDNIGHT.plusSeconds(((Double)value).longValue());
							break;
						default:
							break;
					
					}
					LOGGER.info("datetime value: {}",value);
				}
		
			}
			else if(type == VariableType.Character) {
				Struct<?> s = Struct.create(length+"S");
				List<Object> ob = s.unpack(stream);
				value = ob.get(0);
				//LOGGER.info("column: {}",ob);
			}
			
			LOGGER.info("final value: {}",value);
		}
		
		}
		stream.getRandomAccessFile().seek(mark);
		*/

	}

}
