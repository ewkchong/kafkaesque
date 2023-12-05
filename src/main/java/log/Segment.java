package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import log.exceptions.SegmentFullException;

public class Segment {
	private File file;
	private long baseOffset;
	private long nextOffset;
	private RandomAccessFile raf;
	private LogConfig logConfig;
	private ArrayList<Integer> index;
	private int baseIndex;

	public Segment(String baseDirectory, long baseOffset, int baseIndex, LogConfig config) {
		// create a new file in the baseDirectory
		this.file = new File(baseDirectory, String.valueOf(baseOffset) + ".store");
		// allow access to file as if it were a byte array
		try {
			this.file.createNewFile();
			this.raf = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file: " + file.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not create file: " + file.getAbsolutePath());
			e.printStackTrace();
		}

		this.baseOffset = baseOffset;
		this.nextOffset = baseOffset;
		this.baseIndex = baseIndex;
		this.logConfig = config;
		this.index = new ArrayList<Integer>();
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
			// ensure file pointer is at the end
			raf.seek(nextOffset - baseOffset);
			raf.write(data);
			index.add((int) nextOffset);
			nextOffset += data.length;
		} catch (IOException e) {
			System.out.println("Segment Append: Could not write to file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Reads record at logical offset
	 *
	 * @param  index  index of record
	 * @return		  record at offset
	 */
	public Record read(long index) {
		//  TODO: do something if offset is out of bounds
		try {
			raf.seek(this.index.get((int) index) - baseOffset);
			// int recordLength = raf.readInt();
			
			// read the first 4 bytes of the record
			byte[] recordLengthBytes = new byte[4];
			raf.read(recordLengthBytes);
			int recordLength = (recordLengthBytes[0] << 24) | (recordLengthBytes[1] << 16) | (recordLengthBytes[2] << 8) | (recordLengthBytes[3]);

			byte[] data = new byte[recordLength];

			// add the first 4 bytes to the data array
			data[0] = recordLengthBytes[0];
			data[1] = recordLengthBytes[1];
			data[2] = recordLengthBytes[2];
			data[3] = recordLengthBytes[3];

			raf.read(data, 4, recordLength - 4);
			return Record.deserialize(data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// probably an error in deserialization
			e.printStackTrace();
		}
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

	public int getBaseIndex() {
		return baseIndex;
	}

	public int getSize() {
		return index.size();
	}

	/**
	 * Returns true if record can fit in segment, false otherwise
	 * @param len
	 * @return true if record can fit in segment, false otherwise
	 */
	private boolean canFit(int len) {
		return nextOffset - baseOffset + len <= logConfig.getSegmentSizeBytes();
	}

}
