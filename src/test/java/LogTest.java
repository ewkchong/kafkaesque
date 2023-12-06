import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import log.InternalLog;
import log.Log;
import log.LogConfig;
import log.Record;
import types.*;
import messages.Message;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogTest {
	Log log;

	@BeforeEach
	public void setUp() {
		try {
			LogConfig config = new LogConfig(100, "logTest");
			log = new InternalLog(config);
		} catch (Exception e) {
			System.err.println("Could not create log in testing");
		}
	}

	@AfterEach
	public void tearDown() {
		log.close();
	}

	@Test
	public void testAppendAndRead() {
		log.append(new Message(1, Topic.DRIVER_DATA, "blah"));
		Record record = log.read(0);
		assertArrayEquals(new Message(1, Topic.DRIVER_DATA, "blah").serialize(), record.serialize());
	}

	@Test
	public void testAppendAndReadMultiple() {
		Message msg1 = new Message(1, Topic.DRIVER_DATA, "blah");
		Message msg2 = new Message(2, Topic.DRIVER_DATA, "blah");

		log.append(msg1);
		log.append(msg2);
		
		Record m1 = log.read(0);
		Record m2 = log.read(1);

		assertArrayEquals(msg1.serialize(), m1.serialize());
		assertArrayEquals(msg2.serialize(), m2.serialize());
	}

	@Test
	public void testAppendFullActiveSegment() {
		//  TODO: test appending to full segment
		try {
			LogConfig config = new LogConfig(30, "fullActiveSegment");
			log = new InternalLog(config);
		} catch (Exception e) {
			if (log != null) {
				log.close();
			}
			fail();
			System.err.println("Could not create log in testing");
		}
		Message msg1 = new Message(1, Topic.DRIVER_DATA, "blah");
		Message msg2 = new Message(2, Topic.DRIVER_DATA, "blah");

		log.append(msg1);
		log.append(msg2);
		
		Record m1 = log.read(0);
		Record m2 = log.read(1);

		assertArrayEquals(msg1.serialize(), m1.serialize());
		assertArrayEquals(msg2.serialize(), m2.serialize());
	}
}
