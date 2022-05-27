package org.thshsh.sas.bdat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.input.RandomAccessFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thshsh.sas.Dataset;
import org.thshsh.sas.Library;
import org.thshsh.struct.Struct;

/**
 * A Bdat "library" is actually just a folder containing sas7bdat files
 * 
 * @author daniel.watson
 *
 */
public class LibraryBdat extends Library {

	private static final Logger LOGGER = LoggerFactory.getLogger(LibraryBdat.class);

	/*
	 SUBHEADER_SIGNATURE_TO_INDEX = {
	 			
		        b'\xF7\xF7\xF7\xF7': ROW_SIZE_SUBHEADER_INDEX,  
		        b'\x00\x00\x00\x00\xF7\xF7\xF7\xF7': ROW_SIZE_SUBHEADER_INDEX,
		        b'\xF7\xF7\xF7\xF7\x00\x00\x00\x00': ROW_SIZE_SUBHEADER_INDEX,
		        
		        b'\xF6\xF6\xF6\xF6': COLUMN_SIZE_SUBHEADER_INDEX, 16185078
		        b'\x00\x00\x00\x00\xF6\xF6\xF6\xF6': COLUMN_SIZE_SUBHEADER_INDEX,
		        b'\xF6\xF6\xF6\xF6\x00\x00\x00\x00': COLUMN_SIZE_SUBHEADER_INDEX,
		        
		        b'\x00\xFC\xFF\xFF': SUBHEADER_COUNTS_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFC\x00': SUBHEADER_COUNTS_SUBHEADER_INDEX,
		        b'\x00\xFC\xFF\xFF\xFF\xFF\xFF\xFF': SUBHEADER_COUNTS_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFF\xFF\xFF\xFC\x00': SUBHEADER_COUNTS_SUBHEADER_INDEX,
		        
		        b'\xFD\xFF\xFF\xFF': COLUMN_TEXT_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFD': COLUMN_TEXT_SUBHEADER_INDEX,
		        b'\xFD\xFF\xFF\xFF\xFF\xFF\xFF\xFF': COLUMN_TEXT_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFD': COLUMN_TEXT_SUBHEADER_INDEX,
		        
		        b'\xFF\xFF\xFF\xFF': COLUMN_NAME_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF': COLUMN_NAME_SUBHEADER_INDEX,
		        
		        b'\xFC\xFF\xFF\xFF': COLUMN_ATTRIBUTES_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFC': COLUMN_ATTRIBUTES_SUBHEADER_INDEX,
		        b'\xFC\xFF\xFF\xFF\xFF\xFF\xFF\xFF': COLUMN_ATTRIBUTES_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFC': COLUMN_ATTRIBUTES_SUBHEADER_INDEX,
		        
		        b'\xFE\xFB\xFF\xFF': FORMAT_AND_LABEL_SUBHEADER_INDEX, <little endian
		        b'\xFF\xFF\xFB\xFE': FORMAT_AND_LABEL_SUBHEADER_INDEX,
		        b'\xFE\xFB\xFF\xFF\xFF\xFF\xFF\xFF': FORMAT_AND_LABEL_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFF\xFF\xFF\xFB\xFE': FORMAT_AND_LABEL_SUBHEADER_INDEX,
		        
		        b'\xFE\xFF\xFF\xFF': COLUMN_LIST_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFE': COLUMN_LIST_SUBHEADER_INDEX,
		        b'\xFE\xFF\xFF\xFF\xFF\xFF\xFF\xFF': COLUMN_LIST_SUBHEADER_INDEX,
		        b'\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFE': COLUMN_LIST_SUBHEADER_INDEX,
		    }
	*/

	
	public static ZonedDateTime EPOCH = ZonedDateTime.of(1960, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

	//	public static Charset METADATA_CHARSET = Charset.forName("US-ASCII");
	public static Charset METADATA_CHARSET = Charset.forName("UTF-8");

	/*static Pattern HEADER_PATTERN = Pattern.compile(
			"(HEADER RECORD\\*{7}LIBRARY HEADER RECORD\\!{7}0{30} {2}SAS {5}SAS {5}SASLIB {2}(?<version>.{8})(?<os>.{8}) {24}(?<created>.{16})(?<modified>.{16}) {64})"
			,Pattern.DOTALL);*/

	static byte U64_BYTE_CHECKER_VALUE = 51;
	static byte ALIGN_1_CHECKER_VALUE = 51;

	static int ALIGN_1_DEFAULT = 4;
	static int ALIGN_2_DEFAULT = 4;

	static int PAGE_BIT_OFFSET_X86 = 16;
	static int PAGE_BIT_OFFSET_X64 = 32;

	static int SUBHEADER_POINTER_LENGTH_X86 = 12;
	static int SUBHEADER_POINTER_LENGTH_X64 = 24;

	static int PAGE_META_TYPE = 0;
	static int PAGE_DATA_TYPE = 256;
	static int PAGE_AMD_TYPE = 1024;
	static int PAGE_METC_TYPE = 16384;
	static int PAGE_COMP_TYPE = -28672;

	static int COMPRESSION_TRUNCATED = 1;

	/*static int[] PAGE_MIX_TYPE = new int[] { 512, 640 };
	static int[] PAGE_MIX_DATA_TYPE = new int[] { 512, 640, PAGE_DATA_TYPE };
	static int[] PAGE_META_MIX_AMD = new int[] { 512, 640, PAGE_META_TYPE, PAGE_DATA_TYPE, PAGE_AMD_TYPE };
	static int[] PAGE_ANY = new int[] { 512, 640, PAGE_META_TYPE, PAGE_DATA_TYPE, PAGE_AMD_TYPE, PAGE_COMP_TYPE, PAGE_METC_TYPE };
	*/
	/*	static Struct HEADER_1_STRUCT = Struct.create("32sc2sc1s1b1s1S52s64S8S");
	
		static Struct HEADER_2_STRUCT = Struct.create("dd16siii");
	
		static Struct HEADER_3_STRUCT = Struct.create("8s8S16S16S16S16S");
	*/
	
	List<String> columnNames = new ArrayList<String>();
	
	public static LibraryBdat from_file(File f) throws IOException, InstantiationException, IllegalAccessException {

		RandomAccessFile raf = new RandomAccessFile(f, "r");
		RandomAccessFileInputStream stream = new RandomAccessFileInputStream(raf);

		return from_file(stream);
	}

	public static LibraryBdat from_file(RandomAccessFileInputStream stream) throws IOException, InstantiationException, IllegalAccessException {

		RandomAccessFile raf = stream.getRandomAccessFile();
		
		DatasetBdat header = DatasetBdat.from_file(stream);

		return null;

	
	}

	public static List<Object> seekAndRead(RandomAccessFileInputStream raf, long pos, Struct s) throws IOException {
		long mark = raf.getRandomAccessFile().getFilePointer();
		raf.getRandomAccessFile().seek(pos);
		List<Object> objects = s.unpack(raf);
		raf.getRandomAccessFile().seek(mark);
		return objects;
	}

	public static void debugBuffer(InputStream is) throws IOException {
		byte[] print = new byte[64];
		is.read(print);
		LOGGER.debug("BUFFER: {}", Hex.encodeHexString(print));
	}

	@Override
	public LocalDateTime getModified() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getCreated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Dataset> getDatasets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDatasets(List<? extends Dataset> ds) {
		// TODO Auto-generated method stub
		
	}

}
