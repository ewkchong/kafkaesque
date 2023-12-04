package log;

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
		//  TODO: deserialize record
		return null;
	}
}

