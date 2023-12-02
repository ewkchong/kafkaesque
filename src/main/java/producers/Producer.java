package producers;

import brokers.Broker;
import messages.Message;
import topics.Topic;

public interface Producer {
    /**
     * Initializes the producer by communicating with a broker in the cluster.
     * The broker assignment for the specified topic is obtained during this process.
     *
     * @param broker The initial broker to communicate with.
     * @param topic The topic to which messages will be produced.
     */
    void initialize(Broker broker, Topic topic);

    /**
     * Produces a message to the assigned broker.
     *
     * @param message
     */
    void produceMessage(Message message);

    /**
     * Closes the producer, releasing any resources.
     */
    void close();
}
