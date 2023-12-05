package partitions;

import exceptions.BadPartitionException;
import topics.Topic;

public class CityPartition extends DefaultPartition {
    String city;
    public CityPartition(Topic topic, String city) throws BadPartitionException {
        super(topic);
        if (topic != Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException("topic does not partition by city");
        }
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
