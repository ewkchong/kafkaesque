package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Segment {
	private File file;
	private long baseOffset;
	private long nextOffset;
	private RandomAccessFile raf;

	public Segment(String baseDirectory, long baseOffset) throws FileNotFoundException {

		// create a new file in the baseDirectory
		this.file = new File(baseDirectory, String.valueOf(baseOffset));

		// allow access to file as if it were a byte array
		this.raf = new RandomAccessFile(file, "rw");

		this.baseOffset = baseOffset;
	}

	public long append(Record record) {
		return 0;
	}

	public Record read(long offset) {
		return null;
	}
	
	public void close() {
		try {
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
