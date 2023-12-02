package topics;

public abstract class DefaultTopic {
    public Topic topic;
    public int id;

    public DefaultTopic(Topic topic, int id) {
        this.topic = topic;
        this.id = id;
    }

    // specific topics will have more attributes
}
