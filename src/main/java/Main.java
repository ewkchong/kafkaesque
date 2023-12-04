import brokers.Broker;
import brokers.DefaultBroker;
import clusters.Cluster;
import clusters.DefaultCluster;
import consumers.DefaultConsumer;
import consumers.Consumer;
import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import partitions.DefaultPartition;
import partitions.IdPartition;
import partitions.Partition;
import producers.DefaultProducer;
import producers.Producer;
import topics.Topic;

class Main {
	public static void main(String[] args) {
		try {
			example1();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void example1() throws BadPartitionException, NoPartitionFound {
		Cluster cluster = new DefaultCluster();
		Broker broker = new DefaultBroker();
		Partition partition = new IdPartition(Topic.DRIVER_DATA, 123);
		Producer producer = new DefaultProducer();
		Consumer consumer = new DefaultConsumer();
		String content = "{\"driver_id\": 123, \"location\": {\"lat\": 49.2606, \"lng\": 123.2460}}";
		Message message = new Message(0, Topic.DRIVER_DATA, content);

		cluster.addBroker(broker);
		broker.addPartition(partition);

		producer.initialize(broker, Topic.DRIVER_DATA);
		producer.produceMessage(message);
		producer.close();

		consumer.initialize(broker, Topic.DRIVER_DATA, 123);
		consumer.consumeMessage();
		System.out.println(consumer.getMessageBuffer());
		consumer.close();
	}
}
