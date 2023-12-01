package logs;

public interface Log {
    /**
     * Appends an event to the log.
     *
     * @param content The content of the event.
     * @return The offset assigned to the appended event.
     */
    long appendEvent(String content);

    /**
     * Reads an event from the log based on the given offset.
     *
     * @param offset The offset of the event to be read.
     * @return The content of the event.
     */
    String readEvent(long offset);
}
