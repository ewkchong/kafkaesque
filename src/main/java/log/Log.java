package log;

public interface Log {
    /**
     * Appends an event to the log.
     *
     * @param record: the record to append
     * @return The offset assigned to the appended event.
     */
    long append(Record record);

    /**
     * Reads an event from the log based on the given offset.
     *
     * @param offset The offset of the event to be read.
     * @return The record at the given offset
     */
    Record read(long offset);

    /**
     * Closes the log, releasing any resources. Flushes data to file.
     */
    void close();
}
