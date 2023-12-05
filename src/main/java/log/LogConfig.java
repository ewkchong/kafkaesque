package log;

public class LogConfig {
	private long segmentSizeBytes;
	private String storeDir;

	public LogConfig(long segmentSizeBytes, String storeDir) {
		this.segmentSizeBytes = segmentSizeBytes;
		this.storeDir = storeDir;
	}

	public long getSegmentSizeBytes() {
		return segmentSizeBytes;
	}
	
	public String getStoreDir() {
		return storeDir;
	}
}
