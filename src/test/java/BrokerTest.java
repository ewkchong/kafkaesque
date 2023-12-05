import brokers.Broker;
import brokers.DefaultBroker;
import exceptions.BadPartitionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import partitions.CityPartition;
import partitions.IdPartition;
import partitions.Partition;
import topics.Topic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BrokerTest {

    @BeforeAll
    public void setUp() {
    }

	@Test
    public void testConsume() throws BadPartitionException {
        Broker b = new DefaultBroker();
        Partition p1 = new IdPartition(Topic.DRIVER_DATA, 1);
        Partition p2 = new CityPartition(Topic.RIDER_REQUESTS_RIDE, "vancouver");
        assertDoesNotThrow(() -> {
            b.consume(Topic.RIDER_REQUESTS_RIDE, "vancouver", 1);
        });
        assertDoesNotThrow(() -> {
            b.consume(Topic.DRIVER_DATA, 1, 1);
        });

        assertThrows(BadPartitionException.class, () -> {
            b.consume(Topic.RIDER_REQUESTS_RIDE, 1, 1);
        });
        assertThrows(BadPartitionException.class, () -> {
            b.consume(Topic.DRIVER_DATA, "vancouver", 1);
        });

    }
}
