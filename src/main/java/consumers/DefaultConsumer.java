package consumers;

import brokers.Broker;
import messages.Message;
import topics.Topic;

public class DefaultConsumer implements Consumer {
    public void initialize(Broker broker, Topic topic, int id) {

    }

    public void initialize(Broker broker, Topic topic) {

    }

    public Message consumeMessage() {
        return null;
    }

    public void close() {

    }
}
