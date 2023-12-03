import brokers.Broker;
import brokers.DefaultBroker;
import clusters.Cluster;
import clusters.DefaultCluster;
import consumers.DefaultConsumer;
import consumers.Consumer;
import messages.Message;
import partitions.DefaultPartition;
import partitions.Partition;
import producers.DefaultProducer;
import producers.Producer;
import topics.Topic;

class Main {
	public static void main(String[] args) {
		example1();
	}

	public static void example1() {
		Cluster cluster = new DefaultCluster();
		Broker broker = new DefaultBroker();
		Partition partition = new DefaultPartition(Topic.DRIVER_DATA);
		Producer producer = new DefaultProducer();
		Consumer consumer = new DefaultConsumer();
		String content = "{driver_id: 123, location: {lat: 49.2606, lng: 123.2460}}";
		Message message = new Message(0, Topic.DRIVER_DATA, content);

		cluster.addBroker(broker);
		broker.addPartition(partition);

		producer.initialize(broker, Topic.DRIVER_DATA);
		producer.produceMessage(message);
		producer.close();

		consumer.initialize(broker, Topic.DRIVER_DATA);
		Message received = consumer.consumeMessage();
		consumer.close();
	}
}
