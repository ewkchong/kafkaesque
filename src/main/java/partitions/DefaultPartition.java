package partitions;

import log.InternalLog;
import log.Log;
import messages.Message;
import topics.Topic;

public abstract class DefaultPartition implements Partition {
    private Topic topic;
    private Log log;

    public DefaultPartition(Topic topic) {
        this.topic = topic;
        log = new InternalLog();
    }

    public Topic getTopic() {
        return topic;
    }

    public void appendMessage(Message message) {
        log.append(message);
    }

    public Message readMessage(int offset) {
        return null;
    }
}
