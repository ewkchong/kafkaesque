import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import log.LogConfig;
import log.Segment;
import messages.Message;
import topics.Topic;

public class SegmentTest {
	Segment segment;
	LogConfig config = new LogConfig(100, "target/storage");

	@BeforeEach
	public void setUp() {
		// init test segment with max 100 byte file size
		if (segment != null) {
			segment.close();
		}
		try {
			segment = new Segment("data", 0, 0, config);
		} catch (Exception e) {
			fail();
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
		segment.close();
	}

	@Test
	public void testAppend() {
		Message msg = new Message(1, Topic.DRIVER_DATA, "blah");
		try {
			segment.append(msg);
		} catch (Exception e) {
			fail();
		}
		assertEquals(msg.length(), segment.getNextOffset());
	}

	@Test
	public void testBasicRead() {
		Message msg = new Message(1, Topic.DRIVER_DATA, "blah");
		try {
			segment.append(msg);
		} catch (Exception e) {
			fail();
		}
		try {
			log.Record readRecord = segment.read(0);
			assertArrayEquals(msg.serialize(), readRecord.serialize());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAppendReadMultiple() {
		Message msg1 = new Message(1, Topic.DRIVER_DATA, "blah");
		Message msg2 = new Message(2, Topic.DRIVER_DATA, "blah2");
		Message msg3 = new Message(3, Topic.DRIVER_DATA, "blah33");
		try {
			segment.append(msg1);
			segment.append(msg2);
			segment.append(msg3);
		} catch (Exception e) {
			fail();
		}
		try {
			log.Record readRecord = segment.read(0);
			assertArrayEquals(msg1.serialize(), readRecord.serialize());
			readRecord = segment.read(1);
			assertArrayEquals(msg2.serialize(), readRecord.serialize());
			readRecord = segment.read(2);
			assertArrayEquals(msg3.serialize(), readRecord.serialize());
		} catch (Exception e) {
			fail();
		}
	}
}
