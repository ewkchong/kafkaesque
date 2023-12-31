package partitions;

import messages.Message;
import types.Topic;

public interface Partition {
    public Topic getTopic();
    public void appendMessage(Message message);
    public Message readMessage(int offset);

}
