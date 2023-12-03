package log;

public interface Record {
	public int length();
	public byte[] serialize();
}
