import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import log.LogConfig;
import log.Segment;
import messages.Message;
import topics.Topic;

//  TODO: add more tests
@Disabled
public class SegmentTest {
	Segment segment;
	LogConfig config = new LogConfig(100, "target/storage");

	@BeforeEach
	public void setUp() {
		// init test segment with max 100 byte file size
		try {
			segment = new Segment("data", 0, config);
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
			assertEquals(msg.serialize(), readRecord.serialize());
		} catch (Exception e) {
			fail();
		}
	}
}
