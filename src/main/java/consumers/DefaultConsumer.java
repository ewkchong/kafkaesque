package consumers;

import brokers.Broker;
import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import types.Service;
import types.Topic;

import java.util.ArrayList;
import java.util.List;

public class DefaultConsumer implements Consumer {
    Broker broker;
    Service service;
    Topic topic;
    int id;
    String city;
    int offset;

    List<Message> messageBuffer;

    public DefaultConsumer() {
        this.messageBuffer = new ArrayList<Message>();
    }

    public void initialize(Broker broker, Topic topic, int id) throws NoPartitionFound {
        initialize(broker, topic);
        this.id = id;
        this.offset = 0;
    }

    public void initialize(Broker broker, Topic topic, String city) throws NoPartitionFound {
        initialize(broker, topic);
        this.city = city;
        this.offset = 0;
    }

    public void initialize(Broker broker, Topic topic) throws NoPartitionFound {
        this.broker = broker;
        this.topic = topic;
        this.service = broker.clientInit(topic, id);
    }

    public void consumeMessage() throws BadPartitionException, NoPartitionFound {
        // id is 0 if not initialized
        if (id != 0) {
            messageBuffer.addAll(broker.consume(topic, id, 0));
        } else {
            messageBuffer.addAll(broker.consume(topic, city, 0));
        }
    }

    public List<Message> getMessageBuffer() {
        return messageBuffer;
    }

    public void close() {

    }
}
