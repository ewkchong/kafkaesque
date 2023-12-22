package brokers;

import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import partitions.Partition;
import types.Service;
import types.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Broker {
    public Map<Topic, List<Partition>> getPartitions();

    // REST API methods below

    // used by consumers to 'subscribe' to a topic and receive messages
    // returns list of all messages starting at offset and matching topic and id
    public List<Message> consume(Topic topic, int id, int offset) throws BadPartitionException, NoPartitionFound;
    // for Topic: Rider Requests Ride
    public List<Message> consume(Topic topic, String city, int offset) throws BadPartitionException, NoPartitionFound;
    // used by producers to send a message
    public void produce(Message message) throws NoPartitionFound, BadPartitionException;


    // returns the info of corresponding broker
    public Service findBroker(Topic topic, int id) throws BadPartitionException;
    public Service findBroker(Topic topic, String city) throws BadPartitionException;
}
