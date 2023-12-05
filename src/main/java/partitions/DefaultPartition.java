package partitions;

import log.InternalLog;
import log.Log;
import log.LogConfig;
import messages.Message;
import topics.Topic;

public abstract class DefaultPartition implements Partition {
    private Topic topic;
    private Log log;

    public DefaultPartition(Topic topic) {
        this.topic = topic;
        log = new InternalLog(new LogConfig(999, "not_sure_what_to_put"));
    }

    public Topic getTopic() {
        return topic;
    }

    public void appendMessage(Message message) {
        log.append(message);
    }

    public Message readMessage(int offset) {
        return (Message) log.read(offset);
    }
}
