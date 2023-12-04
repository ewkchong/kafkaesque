package messages;

import java.io.UnsupportedEncodingException;

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
		int topicLength = topic.toString().getBytes().length;
		return 4 + 4 + 4 + topicLength + content.getBytes().length;
	}
	
	/**
	 * serializes the message into a byte array
	 * @return the serialized message as a byte array
	 */
	public byte[] serialize() {
		try {
			byte[] topicBytes = topic.toString().getBytes();
			int topicLength = topicBytes.length;

			byte[] data = new byte[4 + 4 + 4 + topicLength + content.getBytes().length];
			
			// write length to byte array
			data[0] = (byte) (length() >> 24);
			data[1] = (byte) (length() >> 16);
			data[2] = (byte) (length() >> 8);
			data[3] = (byte) (length());

			// write int indentifier to byte array
			data[4] = (byte) (identifier >> 24);
			data[5] = (byte) (identifier >> 16);
			data[6] = (byte) (identifier >> 8);
			data[7] = (byte) (identifier);


			// write length of topic string to byte array
			data[8] = (byte) (topicBytes.length >> 24);
			data[9] = (byte) (topicBytes.length >> 16);
			data[10] = (byte) (topicBytes.length >> 8);
			data[11] = (byte) (topicBytes.length);

			// write topic string to byte array
			for (int i = 0; i < topicBytes.length; i++) {
				data[i + 12] = topicBytes[i];
			}

			// write the content to byte array
			byte[] contentBytes = content.getBytes("UTF-8");
			for (int i = 0; i < contentBytes.length; i++) {
				data[i + 4 + 4 + 4 + topicBytes.length] = contentBytes[i];
			}

			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}


}
