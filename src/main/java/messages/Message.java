package messages;

import log.Record;
import topics.Topic;

public class Message implements Record {
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
	
	public int length() {
		// TODO: get length of the record
		return 0;
	}
	
	/**
	 * serializes the message into a byte array
	 * @return the serialized message as a byte array
	 */
	public byte[] serialize() {
		// TODO: serialize the record
		return new byte[0];
	}


}
