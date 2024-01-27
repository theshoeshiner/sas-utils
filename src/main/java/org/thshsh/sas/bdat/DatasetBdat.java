package org.thshsh.sas.bdat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Observation;
import org.thshsh.sas.Variable;
import org.thshsh.sas.bdat.x32.PageHeader32;
import org.thshsh.sas.bdat.x32.SubHeaderPointer32;
import org.thshsh.sas.bdat.x64.PageHeader64;
import org.thshsh.sas.bdat.x64.SubHeaderPointer64;
import org.thshsh.struct.ByteOrder;
import org.thshsh.struct.Struct;

public class DatasetBdat extends Dataset {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DatasetBdat.class);


	public static Charset METADATA_CHARSET = Charset.forName("UTF-8");

	/*static byte[] MAGIC;
	static {
		try {
			MAGIC = Hex.decodeHex("000000000000000000000000c2ea8160b31411cfbd92080009c7318c181f1011");
		} catch (DecoderException e) {
		}
	}*/

	
	

	//static int PAGE_OFFSET_X86 = 16;
	//static int PAGE_OFFSET_X64 = 32;

	static int COMPRESSION_TRUNCATED = 1;

	static int TRUNCATED_SUBHEADER_ID = 1;


	List<VariableBdat> variables;
	List<Page> pages = new ArrayList<Page>();

	//Integer rowCount;

	Header1 header1;
	Header2 header2;
	Header3 header3;
	Header4 header4;

	protected RowSizeSubHeader rowSizeSubHeader;
	protected ColumnNamesSubHeader columnNamesSubHeader;
	protected ColumnAttributesSubHeader columnAttributesSubHeader;
	protected ColumnSizeSubHeader columnSizeSubHeader;
	
	List<FormatAndLabelSubHeader> formatAndLabels = new ArrayList<>();

	//Boolean compression = false;

	protected File file;


	public DatasetBdat(LibraryBdat lib) {
		super(lib);
	}
	
	public DatasetBdat(LibraryBdat lib,File file) {
		super(lib);
		this.file = file;
	}

	public RandomAccessFileInputStream getRandomAccessFileInputStream() throws FileNotFoundException {
		if(file == null) return library.getRandomAccessFileInputStream();
		else return new RandomAccessFileInputStream(new RandomAccessFile(file, "r"), true);
	}

	public Long getColumnCount() {
		return rowSizeSubHeader.getColumnCount();
	}



	public void setColumnNamesSubHeader(ColumnNamesSubHeader columnNamesSubHeader) {
		this.columnNamesSubHeader = columnNamesSubHeader;
		columnNamesSubHeader.setDataset(this);
	}

	public void setColumnAttributesSubHeader(ColumnAttributesSubHeader columnAttributesSubHeader) {
		this.columnAttributesSubHeader = columnAttributesSubHeader;
		columnAttributesSubHeader.setDataset(this);
	}

	public void setColumnSizeSubHeader(ColumnSizeSubHeader columnSizeSubHeader) {
		this.columnSizeSubHeader = columnSizeSubHeader;
		columnSizeSubHeader.setDataset(this);
	}

	public Long getRowLength() {
		return rowSizeSubHeader.getRowLength();
	}



	public Long getRowCount() {
		return rowSizeSubHeader.getRowCount();
	}



	public Long getPageCount() {
		return rowSizeSubHeader.getPageCount();
	}



	public Boolean getCompressed() {
		return rowSizeSubHeader.getCompressed();
	}

	public Long getObservationCount() {
		return rowSizeSubHeader.getRowCount().longValue();
	}

	public Long getVariableCount() {
		return rowSizeSubHeader.getColumnCount().longValue();
	}

	public List<VariableBdat> getVariables() {
		if (variables == null) {
			variables = new ArrayList<VariableBdat>();
			Iterator<FormatAndLabelSubHeader> fals = getFormatAndLabelSubHeaders().iterator();
			Iterator<ColumnName> names = columnNamesSubHeader.getColumnNames().iterator();
			Iterator<ColumnAttributes> atts = columnAttributesSubHeader.columnAttributes.iterator();

			long colCount = getVariableCount();
			for (int i = 0; i < colCount; i++) {
				VariableBdat v = new VariableBdat(fals.next(), names.next(), atts.next());
				variables.add(v);
			}
		}
		return variables;
	}

	public Stream<TextSubHeader> getStringSubHeaders() {
		return getPages().stream().map(Page::getSubHeaderPointers).flatMap(l -> l.stream()).filter(p -> p.getSignature() == SubHeaderSignature.String)
				.map(p -> (TextSubHeader) p.getSubHeader());
	}

	public Stream<FormatAndLabelSubHeader> getFormatAndLabelSubHeaders() {
		return getPages().stream().map(Page::getSubHeaderPointers).flatMap(l -> l.stream())
				.filter(p -> p.getSignature() == SubHeaderSignature.FormatAndLabel).map(p -> (FormatAndLabelSubHeader) p.getSubHeader());
	}



	public Optional<String> getColumnName(ColumnName sh) {
		return getSubHeaderString(sh.index, sh.start, sh.length);
	}

	public Optional<String> getFormatName(FormatAndLabelSubHeader sh) {
		if (sh.formatLength == 0)
			return Optional.empty();
		return getSubHeaderString(sh.formatIndex, sh.formatOffset, sh.formatLength);
	}

	public Optional<String> getLabel(FormatAndLabelSubHeader sh) {
		if (sh.labelLength == 0)
			return Optional.empty();
		return getSubHeaderString(sh.labelIndex, sh.labelOffset, sh.labelLength);
	}

	public Optional<String> getSubHeaderString(int index, int start, int length) {
		return getSubHeaderString(index, start, length,true);
	}
	
	public Optional<String> getSubHeaderString(int index, int start, int length,boolean trim) {
		if (length == 0) return Optional.empty();
		LOGGER.trace("getSubHeaderString index: {} start: {} length: {}",index,start,length);
		return getStringSubHeaders().skip(index).map(h -> {
			LOGGER.trace("string: {}",h.string);
			String s = h.getSubString(start, length);
			return trim?s.trim():s;
			}).findFirst();
	}

	public void setHeader1(Header1 h1) {
		header1 = h1;
	}

	/*public Boolean get64Bit() {
		return header1.align1 == U64_BYTE_CHECKER_VALUE;
	}*/

	

	/*public int getPageOffset() {
		return this.get64Bit()?PAGE_OFFSET_X64:PAGE_OFFSET_X86;
	}*/

	

	public ByteOrder getByteOrder() {
		return header1.littleEndian ? ByteOrder.Little : ByteOrder.Big;
	}

	public Platform getPlatform() {
		return Platform.values()[Integer.valueOf(header2.platform)];
	}
	
	public Optional<String> getCompression() {
		return getSubHeaderString(0, rowSizeSubHeader.getCompressionMethodOffset(), rowSizeSubHeader.getCompressionMethodLength());
	}
	
	public CompressionAlgorithm getCompressionAlgorithm() {
		Optional<String> comp = getCompression();
		if(!comp.isPresent()) return null;
		else {
			try {
				return CompressionAlgorithm.valueOf(comp.get());
			} 
			catch (IllegalArgumentException  e) {
				return null;
			}
		}
	}
	
	public Optional<String>  getCreatorProcess() {
		return getSubHeaderString(0, rowSizeSubHeader.getCreatorProcOffset(), rowSizeSubHeader.getCreatorProcLength());
	}
	
	public Optional<String>  getCreatorSoftware() {
		return getSubHeaderString(0, rowSizeSubHeader.getCreatorSoftwareOffset(), rowSizeSubHeader.getCreatorSoftwareLength());
	}

	public List<Page> getPages() {
		if (pages == null)
			pages = new ArrayList<Page>();
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public void setHeader3(Header3 h2) {
		this.header3 = h2;
	}

	public void setHeader4(Header4 h3) {
		this.header4 = h3;
	}

	
	@Override
	public String getName() {
		return header2.getDatasetName();
	}

	@Override
	public void setName(String name) {
		header2.setDatasetName(name);
	}
	
	public Boolean get64Bit() {
		return header1.get64Bit();
	}


	public Header1 getHeader1() {
		return header1;
	}

	public Header2 getHeader2() {
		return header2;
	}

	public Header3 getHeader3() {
		return header3;
	}

	public Header4 getHeader4() {
		return header4;
	}

	
	
	@Override
	public LocalDateTime getCreated() {
		return header3.getCreated();
	}

	@Override
	public LocalDateTime getModified() {
		return header3.getModified();
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public void setType(String type) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setVariables(List<? extends Variable> variables) {
		this.variables = (List<VariableBdat>) variables;
	}


	protected Stream<Observation> createObservationStream(RandomAccessFileInputStream file) {
		Stream<Observation> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ObservationIteratorBdat(this, file), Spliterator.NONNULL), false);
		return stream;
	}



	@Override
	public LibraryBdat getLibrary() {
		return (LibraryBdat) super.getLibrary();
	}

	public Struct<? extends PageHeader> getPageHeaderStruct(){
		return getStruct(PageHeader32.class,PageHeader64.class);
	}
	
	public Struct<? extends SubHeaderPointer> getSubHeaderPointerStruct(){
		return getStruct(SubHeaderPointer32.class,SubHeaderPointer64.class);
	}

	public <T> Struct<T> getStruct(Class<? extends T> classs){
		return getStruct(classs, null);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Struct<T> getStruct(Class<? extends T> classs, Class<? extends T> class64){
		Class<? extends T> theClass = class64 != null && header1.get64Bit()?class64:classs;
		return (Struct<T>) Struct.create(theClass).byteOrder(getByteOrder());
	}

}
