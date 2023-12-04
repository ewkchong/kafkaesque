package log;

import java.util.ArrayList;

import log.exceptions.SegmentFullException;


public class InternalLog implements Log {
	private Segment activeSegment;
	private ArrayList<Segment> segments;
	private LogConfig config;

	public InternalLog(LogConfig config) {
		this.segments = new ArrayList<>();
		this.config = config;
		setup();
	}

	private void setup() {
		// initialize one segment with base offset of 0 (beginning)
		Segment initialSegment = new Segment("data", 0, this.config);
		activeSegment = initialSegment;
		segments.add(initialSegment);
	}

	public long append(Record record) {
		// try to append record to segment, if full, create new one and append to that
		// segment
		try {
			return activeSegment.append(record);
		} catch (SegmentFullException e) {
			Segment newSegment = new Segment("data", activeSegment.getNextOffset(), this.config);
			segments.add(newSegment);
			activeSegment = newSegment;
			try {
				return activeSegment.append(record);
			} catch (SegmentFullException e1) {
				// situation should be impossible, since we just created a new segment
				System.err.println("Segment should not be full");
				e1.printStackTrace();
			}
		}

		// unreachable
		return -1;
	}

	public Record read(long offset) {
		// TODO: read record at offset
		return null;
	}

	public void close() {
		for (Segment segment : segments) {
			segment.close();
		}
	}
}
