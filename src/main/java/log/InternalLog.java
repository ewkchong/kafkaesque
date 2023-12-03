package log;

import java.io.FileNotFoundException;
import java.util.ArrayList;

interface Record {}

public class InternalLog implements Log {
	private static final long SEGMENT_SIZE_BYTES = 1024 * 1024;
	private Segment activeSegment;
	private ArrayList<Segment> segments;

	public InternalLog() {
		this.segments = new ArrayList<>();
		try {
			setup();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setup() throws FileNotFoundException {
		// initialize one segment with base offset of 0 (beginning)
		Segment initialSegment = new Segment("data", 0);
		activeSegment = initialSegment;
		segments.add(initialSegment);
	}

	public long append(Record record) {
		//  TODO: append record to Log
		
		// check record length
		
		// check if active segment can fit record

		// if so, append record to active segment
		
		// if not, create a new segment and append, update active segment

		return 0;
	}

	public Record read(long offset) {
		//  TODO: read record at offset
		return null;
	}
}
