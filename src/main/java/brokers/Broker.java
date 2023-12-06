package brokers;

import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
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
    public List<Message> consume(Topic topic, int id, int offset) throws BadPartitionException, NoPartitionFound;
    // for Topic: Rider Requests Ride
    public List<Message> consume(Topic topic, String city, int offset) throws BadPartitionException, NoPartitionFound;
    // used by producers to send a message
    public void produce(Message message) throws NoPartitionFound;


    public int consumeInit(Broker broker, Topic topic, int id);
    public int produceInit(Broker broker, Topic topic);
}
