package logs;

public class DefaultLog implements Log {
    public long appendEvent(String content) {
        return 0;
    }

    public String readEvent(long offset) {
        return null;
    }
}
