package log;

import messages.Message;
import topics.Topic;

public interface Record {
	/**
	 * @return the length of the record in bytes
	 */
	public int length();

	/**
	 * @return the serialized record as a byte array
	 */
	public byte[] serialize();

	/**
	 * deserializes a byte array into a message
	 * @param  data  the byte array to deserialize
	 * @return		 the message deserialized from the byte array
	 */
	public static Record deserialize(byte[] data) {
		// get bytes 4-7 and convert to int
		int identifier = (data[4] << 24) | (data[5] << 16) | (data[6] << 8) | (data[7]);

		// get bytes 8-11 and convert to int
		int topicLength = (data[8] << 24) | (data[9] << 16) | (data[10] << 8) | (data[11]);

		// get bytes 12-12+topicLength and convert to string
		String topic = new String(data, 12, topicLength);
		
		// convert topic to Topic enum
		Topic topicEnum = Topic.valueOf(topic);

		// get bytes 12+topicLength-end and convert to String
		String content = new String(data, 12 + topicLength, data.length - (12 + topicLength));

		return new Message(identifier, topicEnum, content);
	}
}

