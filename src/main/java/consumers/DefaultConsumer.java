package consumers;

import brokers.Broker;
import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import topics.Topic;

import java.util.ArrayList;
import java.util.List;

public class DefaultConsumer implements Consumer {
    Broker broker;
    Topic topic;
    int id;
    int offset;

    List<Message> messageBuffer;

    public DefaultConsumer() {
        this.messageBuffer = new ArrayList<Message>();
    }

    public void initialize(Broker broker, Topic topic, int id) {
        this.broker = broker;
        this.topic = topic;
        this.id = id;
        this.offset = 0;
    }

    public void initialize(Broker broker, Topic topic) {
        initialize(broker, topic, -1);
    }

    public void consumeMessage() throws BadPartitionException, NoPartitionFound {
        messageBuffer.addAll(broker.consume(topic, id, 0));
    }

    public List<Message> getMessageBuffer() {
        return messageBuffer;
    }

    public void close() {

    }
}
