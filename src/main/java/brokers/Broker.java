package brokers;

import partitions.Partition;
import topics.Topic;

import java.util.ArrayList;

public interface Broker {
    public ArrayList<Partition> getPartitions();
    public ArrayList<Topic> getTopics();
}
