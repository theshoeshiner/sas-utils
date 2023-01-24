package org.thshsh.sas.bdat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Parser;
import org.thshsh.sas.bdat.x32.ColumnAttributes32;
import org.thshsh.sas.bdat.x32.ColumnSizeSubHeader32;
import org.thshsh.sas.bdat.x32.FormatAndLabelSubHeader32;
import org.thshsh.sas.bdat.x32.Header332;
import org.thshsh.sas.bdat.x32.PageHeader32;
import org.thshsh.sas.bdat.x32.RowSizeSubHeader32;
import org.thshsh.sas.bdat.x64.ColumnAttributes64;
import org.thshsh.sas.bdat.x64.ColumnSizeSubHeader64;
import org.thshsh.sas.bdat.x64.FormatAndLabelSubHeader64;
import org.thshsh.sas.bdat.x64.Header364;
import org.thshsh.sas.bdat.x64.PageHeader64;
import org.thshsh.sas.bdat.x64.RowSizeSubHeader64;
import org.thshsh.struct.Struct;
import org.thshsh.struct.TokenType;

public class ParserBdat implements Parser {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ParserBdat.class);
	
	public static final String STANDARD_EXTENSION = "sas7bdat";

	public LibraryBdat parseLibrary(File f) throws IOException {

		LOGGER.debug("parseLibrary: {}",f);
		
		LibraryBdat lib = new LibraryBdat(f);
		
		if(f.isDirectory()) {
			for(File child : f.listFiles()) {
				if(!child.isDirectory() && child.getName().endsWith("sas7bdat")) {
					DatasetBdat header = parseDataset(lib,child);
					lib.datasets.add(header);
				}
			}
		}
		else {
			DatasetBdat header = parseDataset(lib,f);
			lib.datasets.add(header);
		}

		return lib;
		
	}

	public DatasetBdat parseDataset(File file) throws IOException {
		LibraryBdat lib = new LibraryBdat(file);
		return parseDataset(lib, file);
	}
	
	public DatasetBdat parseDataset(LibraryBdat lib,File file) throws IOException {

		try {

			DatasetBdat dataset = new DatasetBdat(lib,file);
			
			//if(file == null) file = lib.getFile();

			RandomAccessFileInputStream stream = dataset.getRandomAccessFileInputStream();
			RandomAccessFile raf = stream.getRandomAccessFile();

			//byte[] buffer;

			dataset.setHeader1(Header1.STRUCT.unpackEntity(stream));
			LOGGER.debug("Header1: {}", dataset.header1);
			//if(!Arrays.equals(MAGIC, dataset.header1.magic)) throw new InvalidFormatException("Invalid Header for SAS7BDAT format");

			dataset.header2 = dataset.getStruct(Header2.class).unpackEntity(stream);
			LOGGER.debug("Header2: {}", dataset.header2);
			
			IOUtils.skip(stream, dataset.header1.getHeader1Padding());
			
			//Struct<? extends Header2> header2struct = dataset.header1.get64Bit()?Header264.STRUCT:Header232.STRUCT;
			//header2struct.byteOrder(dataset.header1.getByteOrder());
			
			dataset.setHeader3(dataset.getStruct(Header332.class, Header364.class).unpackEntity(stream));
			LOGGER.debug("Header3: {}", dataset.header3);
			
			

			//Header3 h3 = dataset.getStruct(Header3.class).unpackEntity(stream);
			
			//IOUtils.skip(stream, dataset.header1.getHeader2Padding());

			Header4 h4 = dataset.getStruct(Header4.class).unpackEntity(stream);
			LOGGER.debug("Header4: {}", h4);

			//python parse_metadata

			LOGGER.debug("jumping to end of page header: from {} to {}", raf.getFilePointer(), dataset.header3.headerSize);

			byte[] padding = (byte[]) Struct.unpack(TokenType.Bytes, (int) (dataset.header3.headerSize - raf.getFilePointer()),
					dataset.getByteOrder(), Charset.defaultCharset(), stream);

			LOGGER.debug("Header Padding: {}", padding.length);
			
			//Struct<? extends PageHeader> pageHeaderStruct = dataset.getPageHeaderStruct();

			for (int p = 0; p < dataset.header3.getPageCount(); p++) {

				long mark = raf.getFilePointer();
				LOGGER.debug("page: {}", p);

				Page page = new Page(dataset);
				page.startByte = raf.getFilePointer();
				dataset.getPages().add(page);
				LOGGER.debug("page start: {}", page.startByte);
				LOGGER.debug("page end: {}", page.startByte+dataset.header3.pageSize);
				
				LOGGER.debug("potential sub headers: {}", dataset.header3.pageSize);

				
				
				//python process_page_meta
				{
					//Struct<? extends PageHeader> pageHeaderStruct = dataset.header1.get64Bit()?PageHeader64.STRUCT:PageHeader32.STRUCT;
					//pageHeaderStruct.byteOrder(ByteOrder.valueOf(dataset.header1.getByteOrder()));
					
					PageHeader pageHeader = dataset.getStruct(PageHeader32.class, PageHeader64.class).unpackEntity(stream);
					page.setHeader(pageHeader);

					LOGGER.debug("PageHeader: {}", pageHeader);
					LOGGER.debug("Page: {}", page);

					if (page.getPageType().meta) {
												
						if(page.getSubHeaderCount()>0) {
						
							LOGGER.debug("reading {} page sub header pointers",page.getSubHeaderCount());
	
							for (int i = 0; i < page.getSubHeaderCount(); i++) {
							
								
								page.getSubHeaderPointers().add(processSubHeaderPointer(dataset, stream));
								
							
							}
						
						}
						//else {
							//LOGGER.debug("Processing missing sub header pointers");
							/*
							for (int i = 0; i < 10; i++) {
								
								page.getSubHeaderPointers().add(processSubHeaderPointer(dataset, stream));
							
							}*/
						//}
						

						LOGGER.debug("current position: {}", raf.getFilePointer());
						
						LOGGER.debug("page.startByte: {}", page.startByte);

						

						//process subheaderpointers
					for (SubHeaderPointer pointer : page.getSubHeaderPointers()) {

							long seekTo = page.startByte + pointer.getPageOffset().longValue();
							
							LOGGER.debug("Processing subheader POINTER: {}",pointer);
						
							LOGGER.debug("position: {} seekTo: {} length: {}",raf.getFilePointer(),seekTo,pointer.getLength()+seekTo);
							
							raf.seek(seekTo);
							
							//LOGGER.debug("position: {} length: {} to: {}", raf.getFilePointer(),pointer.getLength(),pointer.getLength()+raf.getFilePointer());

							//unpack signature first so we can select the correct struct to unpack
							Number signature = (Number) Struct.unpack(dataset.header1.getIntegerTokenType(),dataset.getByteOrder(), stream);
							//LOGGER.debug("Signature value: {}",signature);
							SubHeaderSignature type = SubHeaderSignature.fromId(signature.longValue());
							if(
									type == null
									&& dataset.getCompressed()
									&& (pointer.getCompressionType() == CompressionType.Compressed || pointer.getCompressionType() == CompressionType.None)
									&& pointer.getCategory() == SubHeaderCategory.B
							) {
								//There are data sub headers in compressed files
								type = SubHeaderSignature.Data;
							}
							
							pointer.setSignature(type);
							
							

							if(pointer.getCompressionType() == CompressionType.Truncated) {
								LOGGER.debug("Skipping Truncated SubHeader pointer: {}",pointer);
							}
							else {
								
								LOGGER.debug("Signature: {} = {}", signature,type);
	
								if(type == null) throw new NotImplementedException("SubHeader with type "+signature+" is unknown");
								
								
	
								//LOGGER.debug("POINTER: {}", pointer);
	
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
										case SubHeaderCount:
											break;
										case ColumnList:
											break;
										default:
											break;
									}

								} else {
									//LOGGER.debug("Skipping data subheader");
								}
							
							}

							//LOGGER.debug("index: {}",index);

						}
						//));

						LOGGER.debug("done with sub header pointers");
						LOGGER.debug("compression: {}",dataset.getCompression());
						
					}

					//python readlines
					//for(int i=0;i<header.rowCount;i++) {

					//TODO handle mixed type page
					//TODO skip data pages unless we are iterating observations

					//if (page.getPageType() == PageType.Data || page.getPageType() == PageType.Mixed1 || page.getPageType() == PageType.Mixed2) {

						//processDataSubHeader(dataset, page, null, stream);

					//}
					/*if(page.pageType == PageType.Mixed1 || page.pageType == PageType.Mixed2) {
						
					}*/

					long endOfPage = mark + dataset.header3.pageSize;
					LOGGER.debug("moving file pointer from {} to {}", raf.getFilePointer(), endOfPage);
					raf.seek(endOfPage);
					//LOGGER.debug("pointer1: {}",raf.getFilePointer());
					//stream.reset();
					//jump to end of page
					//IOUtils.skip(stream, dataset.header2.pageSize);
					//LOGGER.debug("pointer2: {}",raf.getFilePointer());

				}

			}
			
			LOGGER.debug("dataset strings: comp: {} soft: {} proc: {}",dataset.getCompression(),dataset.getCreatorSoftware(),dataset.getCreatorProcess());

			
			//
			if (dataset.columnSizeSubHeader.getNumColumns().intValue() != dataset.rowSizeSubHeader.getColumnCount()) {
				throw new IllegalArgumentException("Column Count Mismatch");
			}
			
			//LOGGER.debug("columnNames: {}",dataset.columnNames);
			//LOGGER.debug("columnLabels: {}",dataset.columnLabels);
			//LOGGER.debug("columnFormats: {}",dataset.columnFormats);

			return dataset;

		} finally {
			//input.close();
		}

	}

	/*private static void processSubHeaders(RandomAccessFileInputStream stream, DatasetBdat dataset, RandomAccessFile raf, Page page)
			throws IOException {
		//python process_page_metadata
		LOGGER.debug("process metadata");
	
		for(int i=0;i<page.getSubHeaderCount();i++) {
			
			long subHeaderMark = raf.getFilePointer();
			
			LOGGER.debug("sub header: {}",i);
			
			//SubHeaderProperties subHeaderProps = new SubHeaderProperties();
			
			SubHeaderPointer subHeaderProps;
			//python process_subheader_pointers
			{
			
				Struct<? extends SubHeaderPointer> subHeaderStruct = Struct.create((Class<? extends SubHeaderPointer>)(dataset.get64Bit()?SubHeaderPointer64.class:SubHeaderPointer32.class));
				//Struct<? extends SubHeader> subHeaderStruct = Struct.create(SubHeader64.class);
				LOGGER.debug("subHeaderStruct: {}",subHeaderStruct);
				
				subHeaderProps = subHeaderStruct.unpackEntity(stream);
				LOGGER.debug("subHeader: {}",subHeaderProps);
				
				//Struct<?> s = Struct.create("2s"+dataset.properties.integerStructFormat+dataset.properties.integerStructFormat+"cc");
				//List<Object> subHeaderUnpacked = s.unpack(stream);
				//LOGGER.debug("subHeaderUnpacked: {}",subHeaderUnpacked);
				
	
				subHeaderProps.offset = ((Number) subHeaderUnpacked.get(1)).longValue();
				subHeaderProps.length = ((Number) subHeaderUnpacked.get(2)).longValue();
				subHeaderProps.compression = (Byte) subHeaderUnpacked.get(3);
				subHeaderProps.type = (Byte) subHeaderUnpacked.get(4);
				
			}
			
			LOGGER.debug("position: {}",raf.getFilePointer());
			
			if(subHeaderProps.compressionTypeId != TRUNCATED_SUBHEADER_ID) {
				
				//python read_subheader_signature
				List<Object> signature;
				{
					
					long seekTo = subHeaderProps.getOffset().longValue()+page.startByte;
					LOGGER.debug("Seek from: {} to: {}",raf.getFilePointer(),seekTo);
					
					//Struct<?> sigTestStruct =Struct.create("4s");
					
					//signature = LibraryBdat.seekAndRead(stream,seekTo,sigTestStruct);
					//LOGGER.debug("signature bytes: {}",Hex.encodeHexString((byte[]) signature.get(0)));
					
					
					
					Struct<?> sigStruct =Struct.create(dataset.properties.integerStructFormat);
					//Struct<?> sigStruct =Struct.create("i");
					
					signature = LibraryBdat.seekAndRead(stream,subHeaderProps.getOffset().longValue()+page.startByte,sigStruct);
					
					LOGGER.debug("signature: {}",signature);
					
				}
				
				
				
				//python get_subheader_class
				{
					SubHeaderSignature index = SubHeaderSignature.fromId(((Number)signature.get(0)).intValue());
					LOGGER.debug("signature index: {}:",index);
					
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
	
	protected SubHeaderPointer processSubHeaderPointer(DatasetBdat dataset,RandomAccessFileInputStream stream) throws IOException {
		
		SubHeaderPointer subHeaderPointer;
		//python process_subheader_pointers
		{

			subHeaderPointer = dataset.getSubHeaderPointerStruct().unpackEntity(stream);
			LOGGER.debug("subHeaderPointer: {}", subHeaderPointer);

			return subHeaderPointer;

		}
		
	}

	protected static void processRowSizeSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {


		RowSizeSubHeader rowSize = dataset.getStruct(RowSizeSubHeader32.class, RowSizeSubHeader64.class).unpackEntity(stream);
		LOGGER.debug("rowSize: {}", rowSize);
		
		pointer.subHeader = rowSize;
		dataset.rowSizeSubHeader = rowSize;
		
		

	}

	protected static void processColumnCountSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {
		
		//ColumnSizeSubHeader subheader = Struct.create(ColumnSizeSubHeader.class).unpackEntity(stream);
		dataset.columnSizeSubHeader = dataset.getStruct(ColumnSizeSubHeader32.class, ColumnSizeSubHeader64.class).unpackEntity(stream);

		LOGGER.debug("ColumnSizeSubHeader: {}", dataset.columnSizeSubHeader);


	}

	protected static void processTextSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {

		LOGGER.debug("processTextSubHeader offset: {}",stream.getRandomAccessFile().getFilePointer());
		
		
		//SasConstants.debugBytes(stream, 100);
		
		
		TextSubHeader subHeader = dataset.getStruct(TextSubHeader.class).unpackEntity(stream);
		
		
		//subHeader.dataset = dataset;
		LOGGER.debug("TextSubHeader: {}", subHeader);
		pointer.subHeader = subHeader;
		//Compression comp = Compression.STRUCT.unpackEntity(stream);
		//subHeader.compression = comp;
		
		LOGGER.debug("offset: {}",stream.getRandomAccessFile().getFilePointer());
		
		//LOGGER.debug("compression: {}", subHeader.compression);
		
		//LOGGER.debug("Compression: {}", );

		
		//Integer stringLength;
		subHeader.string = (String) Struct.unpack(TokenType.String, Math.toIntExact(subHeader.getLength()), StandardCharsets.US_ASCII, stream);
		LOGGER.debug("string: {}",subHeader.string);
		//we need to
		//Integer stringOffset = TextSubHeader.STRUCT.byteCount();
		
		/*if(dataset.rowSizeSubHeader.creatorProcLength > 0) {
			//subHeader.creatorProcess = subHeader.string.substring(dataset.rowSizeSubHeader.creatorProcOffset, dataset.rowSizeSubHeader.creatorProcLength);
			subHeader.creatorProcess = subHeader.getSubString(dataset.rowSizeSubHeader.creatorProcOffset, dataset.rowSizeSubHeader.creatorProcLength);
			LOGGER.debug("creatorProcess: '{}'",subHeader.creatorProcess);
		}
		
		if(dataset.rowSizeSubHeader.creatorProcLength > 0) {
			//subHeader.creatorProcess = subHeader.string.substring(dataset.rowSizeSubHeader.creatorProcOffset, dataset.rowSizeSubHeader.creatorProcLength);
			subHeader.creatorProcess = subHeader.getSubString(dataset.rowSizeSubHeader.creatorProcOffset, dataset.rowSizeSubHeader.creatorProcLength);
			LOGGER.debug("creatorProcess: '{}'",subHeader.creatorProcess);
		}*/
		
		/*	if(StringUtils.isBlank(subHeader.compression)) {
				//file is not compressed, LCS = 0;
				if(dataset.rowSizeSubHeader.creatorProcLength > 0) {
					subHeader.creatorProcess =  (String) Struct.unpack(TokenType.String, dataset.rowSizeSubHeader.creatorProcLength, StandardCharsets.US_ASCII, stream);
					LOGGER.debug("creatorProcess: {}",subHeader.creatorProcess);
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
					
					String creatorSoftware = subHeader.compression.substring(0, dataset.rowSizeSubHeader.creatorSoftwareLength);
					LOGGER.debug("creatorSoftware: {}",creatorSoftware);
					stringLength = subHeader.length - Compression.STRUCT.byteCount() - dataset.rowSizeSubHeader.creatorSoftwareLength;
					
				}
				else throw new NotImplementedException("Compression was: "+subHeader.compression);
				
			}*/

		//subHeader.offset = 0-stringOffset;
		
		//String strings =  (String) Struct.unpack(TokenType.String, stringLength, StandardCharsets.US_ASCII, stream);
		//LOGGER.debug("text: '{}'",strings);
		//subHeader.string = strings;

		
	}

	//public static int SUB_HEADER_POINTER_LENGTH = 8;

	protected static void processColumnNameSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)throws IOException {

		Struct<ColumnNamesSubHeader> struct = dataset.getStruct(ColumnNamesSubHeader.class);
		Struct<ColumnName> columnNameStruct = dataset.getStruct(ColumnName.class);
		
		ColumnNamesSubHeader subHeader = struct.unpackEntity(stream);
		dataset.setColumnNamesSubHeader(subHeader);
		subHeader.setPointer(pointer);
		
		LOGGER.debug("ColumnNamesSubHeader: {}", subHeader);
		LOGGER.debug("header length: {}", pointer.getLength());
		LOGGER.debug("subHeader.remainingLength: {}", subHeader.remainingLength);

		int count1 = (subHeader.remainingLength-8)/columnNameStruct.byteCount();

		LOGGER.debug("count1: {}", count1);
		
		int count = subHeader.getNumColumnNames();
		LOGGER.debug("column name count: {}", count);

		for (int i = 0; i < count; i++) {
			ColumnName cns = columnNameStruct.unpackEntity(stream);
			cns.dataset = dataset;
			LOGGER.debug("ColumnName: {}", cns);
			subHeader.getColumnNames().add(cns);

		}

	}

	protected static void processColumnAttributesSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer,RandomAccessFileInputStream stream) throws IOException {

		//		long mark = stream.getRandomAccessFile().getFilePointer();

		LOGGER.debug("processColumnAttributesSubHeader");
		
		Struct<ColumnAttributes> columnAttributesStruct = dataset.getStruct(ColumnAttributes32.class, ColumnAttributes64.class);
		ColumnAttributesSubHeader subHeader = dataset.getStruct(ColumnAttributesSubHeader.class).unpackEntity(stream);
		dataset.setColumnAttributesSubHeader(subHeader);
		subHeader.setPointer(pointer);
		
		LOGGER.debug("ColumnAttributesSubHeader {}",subHeader);

		
		//int count = (int) ((pointer.getLength().longValue() - 2 * dataset.getIntegerLength() - 12) / s.byteCount());
		int count = (subHeader.remainingLength-8)/columnAttributesStruct.byteCount();

		LOGGER.debug("count: {}", count);
		
	

		//stream.getRandomAccessFile().seek(pointer.getOffset().longValue() + page.startByte + header.properties.integerBytesLength+8);

		//skip the first 2 integers
		//IOUtils.skipFully(stream, dataset.getIntegerLength() * 2);

		//LOGGER.debug("page.startByte: {}",page.startByte);
		//LOGGER.debug("position: {}",stream.getRandomAccessFile().getFilePointer());

		//Struct<?> s = Struct.create(header.properties.integerStructFormat+header.properties.integerStructFormat+"2scs");

		for (int i = 0; i < count; i++) {
			ColumnAttributes ca = columnAttributesStruct.unpackEntity(stream);
			LOGGER.debug("ColumnAttributes: {}", ca);
			subHeader.getColumnAttributes().add(ca);
		}

		//stream.getRandomAccessFile().seek(mark);
	}

	protected static void processFormatAndLabelSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)
			throws IOException {

		LOGGER.debug("processFormatAndLabelSubHeader");

		Struct<FormatAndLabelSubHeader> s = dataset.getStruct(FormatAndLabelSubHeader32.class, FormatAndLabelSubHeader64.class);
		//Struct<FormatAndLabelSubHeader> s = Struct.create(FormatAndLabelSubHeader.class);
		FormatAndLabelSubHeader fal = s.unpackEntity(stream);
		fal.dataset = dataset;
		pointer.subHeader = fal;
		
		dataset.formatAndLabels.add(fal);
		
		LOGGER.debug("FormatAndLabelSubHeader: {}", fal);
		LOGGER.debug("format: {}", fal.getFormat());
		LOGGER.debug("label: {}", fal.getLabel());
		
	}

	protected static void processDataSubHeader(DatasetBdat dataset, Page page, SubHeaderPointer pointer, RandomAccessFileInputStream stream)
			throws IOException {

		LOGGER.debug("processDataSubHeader");

		if (dataset.getCompressed()) {
			//TODO
			throw new NotImplementedException();
		}

		//skip 24 bytes
		IOUtils.skipFully(stream, 24);

	

	}

}
