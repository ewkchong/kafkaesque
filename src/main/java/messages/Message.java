package messages;

import topics.Topic;

public class Message {
    public int identifier;
    public Topic topic;
    // content is json format
    // Depending on topic, attributes of content will differ
    // might need serializer/deserializer to get driver/rider id
    public String content;

    public Message(int identifier, Topic topic, String content) {
        this.identifier = identifier;
        this.topic = topic;
        this.content = content;
    }
}
