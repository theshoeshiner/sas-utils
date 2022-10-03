package org.thshsh.sas.bdat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RleCompressor implements Compressor {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(RleCompressor.class);


    //public byte[] decompressRow(int offset, int length, int resultLength, byte[] page) {
	public byte[] decompressRow(int resultLength, byte[] row) {
		int length = row.length;
        byte[] resultByteArray = new byte[resultLength];
        int currentResultArrayIndex = 0;
        int currentByteIndex = 0;
        while (currentByteIndex < length) {
            int controlByte = row[currentByteIndex] & 0xF0;
            int endOfFirstByte = row[currentByteIndex] & 0x0F;
            int countOfBytesToCopy;
            switch (controlByte) {
                case 0x30://intentional fall through
                case 0x20://intentional fall through
                case 0x10://intentional fall through
                case 0x00:
                    if (currentByteIndex != length - 1) {
                        countOfBytesToCopy = (row[currentByteIndex + 1] & 0xFF) + 64
                           + row[currentByteIndex] * 256;
                        System.arraycopy(row, currentByteIndex + 2, resultByteArray,
                                currentResultArrayIndex, countOfBytesToCopy);
                        currentByteIndex += countOfBytesToCopy + 1;
                        currentResultArrayIndex += countOfBytesToCopy;
                    }
                    break;
                case 0x40:
                    int copyCounter = endOfFirstByte * 16 + (row[ currentByteIndex + 1] & 0xFF);
                    for (int i = 0; i < copyCounter + 18; i++) {
                        resultByteArray[currentResultArrayIndex++] = row[ currentByteIndex + 2];
                    }
                    currentByteIndex += 2;
                    break;
                case 0x50:
                    for (int i = 0; i < endOfFirstByte * 256 + (row[ currentByteIndex + 1] & 0xFF) + 17; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x40;
                    }
                    currentByteIndex++;
                    break;
                case 0x60:
                    for (int i = 0; i < endOfFirstByte * 256 + (row[ currentByteIndex + 1] & 0xFF) + 17; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x20;
                    }
                    currentByteIndex++;
                    break;
                case 0x70:
                    for (int i = 0; i < endOfFirstByte * 256 + (row[ currentByteIndex + 1] & 0xFF) + 17; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x00;
                    }
                    currentByteIndex++;
                    break;
                case 0x80:
                case 0x90:
                case 0xA0:
                case 0xB0:
                    countOfBytesToCopy = Math.min(endOfFirstByte + 1 + (controlByte - 0x80),
                            length - (currentByteIndex + 1));
                    System.arraycopy(row,  currentByteIndex + 1, resultByteArray,
                            currentResultArrayIndex, countOfBytesToCopy);
                    currentByteIndex += countOfBytesToCopy;
                    currentResultArrayIndex += countOfBytesToCopy;
                    break;
                case 0xC0:
                    for (int i = 0; i < endOfFirstByte + 3; i++) {
                        resultByteArray[currentResultArrayIndex++] = row[ currentByteIndex + 1];
                    }
                    currentByteIndex++;
                    break;
                case 0xD0:
                    for (int i = 0; i < endOfFirstByte + 2; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x40;
                    }
                    break;
                case 0xE0:
                    for (int i = 0; i < endOfFirstByte + 2; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x20;
                    }
                    break;
                case 0xF0:
                    for (int i = 0; i < endOfFirstByte + 2; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x00;
                    }
                    break;
                default:
                    LOGGER.error("Error control byte: {}", controlByte);
                    break;
            }
            currentByteIndex++;
        }

        return resultByteArray;
    }
    
    public byte[] decompressRow2(int offset, int length, int resultLength, byte[] page) {
        byte[] resultByteArray = new byte[resultLength];
        int currentResultArrayIndex = 0;
        int currentByteIndex = 0;
        while (currentByteIndex < length) {
            int controlByte = page[offset + currentByteIndex] & 0xF0;
            int endOfFirstByte = page[offset + currentByteIndex] & 0x0F;
            int countOfBytesToCopy;
            switch (controlByte) {
                case 0x30://intentional fall through
                case 0x20://intentional fall through
                case 0x10://intentional fall through
                case 0x00:
                    if (currentByteIndex != length - 1) {
                        countOfBytesToCopy = (page[offset + currentByteIndex + 1] & 0xFF) + 64
                           + page[offset + currentByteIndex] * 256;
                        System.arraycopy(page, offset + currentByteIndex + 2, resultByteArray,
                                currentResultArrayIndex, countOfBytesToCopy);
                        currentByteIndex += countOfBytesToCopy + 1;
                        currentResultArrayIndex += countOfBytesToCopy;
                    }
                    break;
                case 0x40:
                    int copyCounter = endOfFirstByte * 16 + (page[offset + currentByteIndex + 1] & 0xFF);
                    for (int i = 0; i < copyCounter + 18; i++) {
                        resultByteArray[currentResultArrayIndex++] = page[offset + currentByteIndex + 2];
                    }
                    currentByteIndex += 2;
                    break;
                case 0x50:
                    for (int i = 0; i < endOfFirstByte * 256 + (page[offset + currentByteIndex + 1] & 0xFF) + 17; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x40;
                    }
                    currentByteIndex++;
                    break;
                case 0x60:
                    for (int i = 0; i < endOfFirstByte * 256 + (page[offset + currentByteIndex + 1] & 0xFF) + 17; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x20;
                    }
                    currentByteIndex++;
                    break;
                case 0x70:
                    for (int i = 0; i < endOfFirstByte * 256 + (page[offset + currentByteIndex + 1] & 0xFF) + 17; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x00;
                    }
                    currentByteIndex++;
                    break;
                case 0x80:
                case 0x90:
                case 0xA0:
                case 0xB0:
                    countOfBytesToCopy = Math.min(endOfFirstByte + 1 + (controlByte - 0x80),
                            length - (currentByteIndex + 1));
                    System.arraycopy(page, offset + currentByteIndex + 1, resultByteArray,
                            currentResultArrayIndex, countOfBytesToCopy);
                    currentByteIndex += countOfBytesToCopy;
                    currentResultArrayIndex += countOfBytesToCopy;
                    break;
                case 0xC0:
                    for (int i = 0; i < endOfFirstByte + 3; i++) {
                        resultByteArray[currentResultArrayIndex++] = page[offset + currentByteIndex + 1];
                    }
                    currentByteIndex++;
                    break;
                case 0xD0:
                    for (int i = 0; i < endOfFirstByte + 2; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x40;
                    }
                    break;
                case 0xE0:
                    for (int i = 0; i < endOfFirstByte + 2; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x20;
                    }
                    break;
                case 0xF0:
                    for (int i = 0; i < endOfFirstByte + 2; i++) {
                        resultByteArray[currentResultArrayIndex++] = 0x00;
                    }
                    break;
                default:
                    LOGGER.error("Error control byte: {}", controlByte);
                    break;
            }
            currentByteIndex++;
        }

        return resultByteArray;
    }
	
	
}
