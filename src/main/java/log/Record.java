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
}
