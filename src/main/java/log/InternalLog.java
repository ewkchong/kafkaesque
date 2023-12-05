package log;

import java.io.File;
import java.util.ArrayList;

import log.exceptions.SegmentFullException;


public class InternalLog implements Log {
	private Segment activeSegment;
	private ArrayList<Segment> segments;
	private LogConfig config;
	private int nextIndex;
	private File dataDir;

	public InternalLog(LogConfig config) {
		this.segments = new ArrayList<>();
		this.config = config;
		this.nextIndex = 0;
		setup();
	}

	private void setup() {
		// Get system path separator
		String separator = System.getProperty("file.separator");

		// ensure data/ directory exists, if not, create it
		File dataDir = new File("data");
		if (!dataDir.exists()) {
			dataDir.mkdir();
		}

		// initialize a folder within the data/ directory
		String dataDirPath = "data" + separator + config.getStoreDir();
		File dataFolder = new File(dataDirPath);
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		this.dataDir = dataFolder;

		// initialize one segment with base offset of 0 (beginning)
		Segment initialSegment = new Segment(dataDirPath, 0, 0, this.config);
		activeSegment = initialSegment;
		segments.add(initialSegment);
	}

	public long append(Record record) {
		// try to append record to segment, if full, create new one and append to that
		// segment
		try {
			long result = activeSegment.append(record);
			nextIndex++;
			return result;
		} catch (SegmentFullException e) {
			String dataDirPath = dataDir.getPath();
			Segment newSegment = new Segment(dataDirPath, activeSegment.getNextOffset(), nextIndex, this.config);
			segments.add(newSegment);
			activeSegment = newSegment;
			try {
				long result = activeSegment.append(record);
				nextIndex++;
				return result;
			} catch (SegmentFullException e1) {
				// situation should be impossible, since we just created a new segment
				System.err.println("Segment should not be full");
				e1.printStackTrace();
			}
		}

		// unreachable
		return -1;
	}

	/**
	 * Reads record at given index, returns null if no record exists at that index
	 *
	 * @param  index  logical index in log to read
	 * @return        record at given index
	 */
	public Record read(long index) {
		// find segment that contains offset
		for (Segment s: segments) {
			int baseIndex = s.getBaseIndex();
			if (index >= baseIndex && index < baseIndex + s.getSize()) {
				return s.read(index - baseIndex);
			}
		}
		return null;
	}

	public void close() {
		for (Segment segment : segments) {
			segment.close();
		}
		dataDir.delete();
	}
}
