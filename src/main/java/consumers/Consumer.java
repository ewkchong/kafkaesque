package consumers;

import brokers.Broker;
import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import topics.Topic;

import java.util.List;

public interface Consumer {
    /**
     * Initializes the consumer by communicating with a broker in the cluster.
     * The broker assignment for the specified topic and id is obtained during this process.
     *
     * @param broker The initial broker to communicate with.
     * @param topic The topic from which messages will be consumed.
     * @param id The driver/rider id we are interested in OR -1 if not needed
     */
    void initialize(Broker broker, Topic topic, int id);

    /**
     * Same as above but when we don't want to specify an id
     *
     * @param broker The initial broker to communicate with.
     * @param topic The topic from which messages will be consumed.
     */
    void initialize(Broker broker, Topic topic);

    /**
     * Consume from the assigned broker.
     */
    void consumeMessage() throws BadPartitionException, NoPartitionFound;

    /*
     * return messages
     */
    public List<Message> getMessageBuffer();

    /**
     * Closes the consumer, releasing any resources.
     */
    void close();
}
