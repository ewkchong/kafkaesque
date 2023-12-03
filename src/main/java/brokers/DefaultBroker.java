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
        return new ArrayList<Message>();
    }

    public void produce(Message message) {

    }
}
