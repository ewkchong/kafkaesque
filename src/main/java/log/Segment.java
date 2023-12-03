package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import log.Record;
import log.exceptions.SegmentFullException;


public class Segment {
	private File file;
	private long baseOffset;
	private long nextOffset;
	private RandomAccessFile raf;

	public Segment(String baseDirectory, long baseOffset) {

		// create a new file in the baseDirectory
		this.file = new File(baseDirectory, String.valueOf(baseOffset));

		// allow access to file as if it were a byte array
		try {
			this.raf = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.baseOffset = baseOffset;
		this.nextOffset = baseOffset;
	}


	/**
	 * Appends record to segment and returns offset of Record
	 *
	 * @param record
	 * @return offset of record
	 */
	public long append(Record record) throws SegmentFullException {
		if (!canFit(record.length())) {
			throw new SegmentFullException();
		}

		// append serialized version of record to end of file
		byte[] data = record.serialize();

		try {
			raf.write(data);
			nextOffset += data.length;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Record read(long offset) {
		return null;
	}
	
	/**
	 * Closes the segment, releasing any resources associated with the file
	 *
	 * @return void
	 */
	public void close() {
		try {
			raf.close();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getNextOffset() {
		return nextOffset;
	}

	/**
	 * Returns true if record can fit in segment, false otherwise
	 * @param len
	 * @return true if record can fit in segment, false otherwise
	 */
	private boolean canFit(int len) {
		//  TODO: implement canFit
		return false;
	}
}
