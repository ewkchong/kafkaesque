package raft;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import io.microraft.RaftEndpoint;

public final class LocalRaftEndpoint implements RaftEndpoint {

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

	private final String id;
	private InetAddress address;
	private final int port;

	private LocalRaftEndpoint(String id, String host, int port) {
		this.id = id;
		this.port = port;
		try {
			this.address = InetAddress.getByName(host);
		} catch (Exception e) {
			System.out.println("Oops! Something went wrong while creating LocalRaftEndpoint");
		}
	}

	@Override
	public String getId() {
		return id;
	}

	public InetAddress getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "LocalRaftEndpoint{" + "id=" + id + "}";
	}

	public int getPort() {
		return port;
	}
}
