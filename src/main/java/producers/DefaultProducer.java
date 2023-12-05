package producers;

import brokers.Broker;
import exceptions.NoPartitionFound;
import topics.Topic;
import messages.Message;

public class DefaultProducer implements Producer {
    Broker broker;
    Topic topic;
    public void initialize(Broker broker, Topic topic) {
        this.broker = broker;
        this.topic = topic;
    }

    public void produceMessage(Message message) throws NoPartitionFound {
        broker.produce(message); // topic is in the message
    }

    public void close() {

    }
}


