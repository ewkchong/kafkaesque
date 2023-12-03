package partitions;

import logs.Log;
import messages.Message;
import topics.Topic;

public class DefaultPartition implements Partition {
    private Topic topic;
    private Log log;

    public DefaultPartition(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void appendMessage(Message message) {

    }

    public Message readMessage(int offset) {
        return null;
    }
}
