package raft;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import io.microraft.RaftEndpoint;
import io.microraft.model.message.RaftMessage;
import io.microraft.transport.Transport;

final class LocalTransport implements Transport {

	private final RaftEndpoint localEndpoint;
	private final Set<RaftEndpoint> endpoints = new HashSet<>();

	LocalTransport(RaftEndpoint localEndpoint) {
		this.localEndpoint = localEndpoint;
	}

	@Override
	public boolean isReachable(RaftEndpoint endpoint) {
		LocalRaftEndpoint localEndpoint = (LocalRaftEndpoint) endpoint;
		try {
			return localEndpoint.getAddress().isReachable(300);
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void send(RaftEndpoint target, RaftMessage message) {
		LocalRaftEndpoint localEndpoint = (LocalRaftEndpoint) target;
		InetAddress targetAddress = localEndpoint.getAddress();
		int port = localEndpoint.getPort();

		if (!endpoints.contains(target)) {
			throw new IllegalStateException(
					"Cannot send message to " + target + " as it is not discovered yet!");
		}

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(message);
			oos.flush();
			byte[] buffer = bos.toByteArray();

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, targetAddress, port);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			System.out.println("Oops! Something went wrong while sending message to " + target);
		}
	}

	public void discoverNode(RaftEndpoint node) {
		if (localEndpoint.equals(node)) {
			throw new IllegalArgumentException(localEndpoint + " cannot discover itself!");
		}

		endpoints.add(node);
	}
}
