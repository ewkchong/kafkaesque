import brokers.Broker;
import brokers.DefaultBroker;
import exceptions.BadPartitionException;
import org.junit.Before;
import org.junit.Test;
import partitions.CityPartition;
import partitions.IdPartition;
import partitions.Partition;
import topics.Topic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PartitionTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testCityPartition() {
        assertThrows(BadPartitionException.class, () -> {new CityPartition(Topic.DRIVER_DATA, "vancouver");});
        assertDoesNotThrow(() -> {new CityPartition(Topic.RIDER_REQUESTS_RIDE, "vancouver");});
    }
    @Test
    public void testIdPartition() {
        assertThrows(BadPartitionException.class, () -> {new IdPartition(Topic.RIDER_REQUESTS_RIDE, 1);});
        assertDoesNotThrow(() -> {new IdPartition(Topic.DRIVER_DATA, 1);});
    }
}
