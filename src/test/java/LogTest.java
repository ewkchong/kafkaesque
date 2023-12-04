import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import log.InternalLog;
import log.Log;
import log.LogConfig;
import log.Record;
import topics.*;
import messages.Message;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogTest {
	Log log;

	@BeforeAll
	public void setUp() {
		try {
			LogConfig config = new LogConfig(100, "target/storage");
			log = new InternalLog(config);
		} catch (Exception e) {
			System.err.println("Could not create log in testing");
		}
	}

	@AfterAll
	public void tearDown() {
		log.close();
	}

	@Test
	public void testAppendAndRead() {
		log.append(new Message(1, Topic.DRIVER_DATA, "blah"));
		Record record = log.read(0);
		assertArrayEquals(new Message(1, Topic.DRIVER_DATA, "blah").serialize(), record.serialize());
	}

	// @Test
	// public void basicRead() {
	// }
}
