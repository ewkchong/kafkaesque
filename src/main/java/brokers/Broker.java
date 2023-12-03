package brokers;

import messages.Message;
import partitions.Partition;
import topics.Topic;

import java.util.ArrayList;
import java.util.List;

public interface Broker {
    public ArrayList<Partition> getPartitions();
    public void addPartition(Partition partition);


    // REST API methods below

    // used by consumers to 'subscribe' to a topic and receive messages
    // returns list of all messages starting at offset and matching topic and id
    public List<Message> consume(Topic topic, int id, int offset);
    // used by producers to send a message
    public void produce(Message message);
}
