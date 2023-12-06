package producers;

import brokers.Broker;
import exceptions.NoPartitionFound;
import types.Service;
import types.Topic;
import messages.Message;

public class DefaultProducer implements Producer {
    Broker broker;
    Service service;
    Topic topic;
    public void initialize(Broker broker, Topic topic, int id) throws NoPartitionFound {
        this.broker = broker;
        this.topic = topic;
        this.service = broker.clientInit(topic, id);
    }

    public void initialize(Broker broker, Topic topic, String city) throws NoPartitionFound {
        this.broker = broker;
        this.topic = topic;
        this.service = broker.clientInit(topic, city);
    }

    public void produceMessage(Message message) throws NoPartitionFound {
        broker.produce(message); // topic is in the message
    }

    public void close() {

    }
}


