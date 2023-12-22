import brokers.Broker;
import brokers.DefaultBroker;
import exceptions.BadPartitionException;
import types.Service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import partitions.CityPartition;
import partitions.IdPartition;
import partitions.Partition;
import types.Topic;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BrokerTest {

    @BeforeAll
    public void setUp() {
    }

	@Test
    public void testConsume() throws BadPartitionException {
        Broker b = new DefaultBroker("node-1", "127.0.0.1", 8080);
        assertDoesNotThrow(() -> {
            b.consume(Topic.RIDER_REQUESTS_RIDE, "Toronto", 0);
        });
        assertDoesNotThrow(() -> {
            b.consume(Topic.DRIVER_DATA, 0, 0);
        });

        assertThrows(BadPartitionException.class, () -> {
            b.consume(Topic.RIDER_REQUESTS_RIDE, 1, 1);
        });
        assertThrows(BadPartitionException.class, () -> {
            b.consume(Topic.DRIVER_DATA, "vancouver", 1);
        });

    }
}
