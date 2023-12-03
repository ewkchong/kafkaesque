package brokers;

import partitions.Partition;
import topics.Topic;

import java.util.ArrayList;

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
}
