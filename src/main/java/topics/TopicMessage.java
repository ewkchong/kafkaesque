package topics;

public abstract class TopicMessage {
    public Topic topic;
    public int id;

    public TopicMessage(Topic topic, int id) {
        this.topic = topic;
        this.id = id;
    }

    // specific topics will have more attributes
}
