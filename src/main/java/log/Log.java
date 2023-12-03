package log;

public interface Log {
	long append(Record record);
	Record read(long offset);
}
