import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import log.InternalLog;
import log.Log;
import log.LogConfig;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
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
	public void testAppend() {
		// TODO:
	}

	@Test
	public void basicRead() {
		// TODO:
	}
}
