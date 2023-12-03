package brokers;

import messages.Message;
import partitions.Partition;
import topics.Topic;

import java.util.ArrayList;
import java.util.List;

public class DefaultBroker implements Broker {
    ArrayList<Partition> partitions;

    public DefaultBroker() {
        this.partitions = new ArrayList<Partition>();
    }

    public ArrayList<Partition> getPartitions() {
        return partitions;
    }
    public void addPartition(Partition partition) {
        partitions.add(partition);
    }

    public List<Message> consume(Topic topic, int id, int offset) {
        // since we assign brokers to consumers this broker should have the partition corresponding to topic/id?
        // ie) we don't have to find the right broker?

        //TODO find partition by topic/id and return messages starting at offset
        return new ArrayList<Message>();
    }

    public void produce(Message message) {
        // since we assign brokers to consumers this broker should have the partition corresponding to topic/id?

        // TODO add message to partition corresponding to topic/id.

    }
}
