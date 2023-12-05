package partitions;

import exceptions.BadPartitionException;
import topics.Topic;

public class IdPartition extends DefaultPartition {
    int id;

    public IdPartition(Topic topic, int id) throws BadPartitionException {
        super(topic);
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException("topic does not partition by id");
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof IdPartition)) {
            return false;
        }
        IdPartition OtherPartition = (IdPartition) obj;

        return OtherPartition.id == this.id && OtherPartition.getTopic() == this.getTopic();
    }
}
