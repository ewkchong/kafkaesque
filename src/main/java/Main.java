///*
//import brokers.Broker;
//import brokers.DefaultBroker;
//import clusters.Cluster;
//import clusters.DefaultCluster;
//import consumers.DefaultConsumer;
//import consumers.Consumer;
//import exceptions.BadPartitionException;
//import exceptions.NoPartitionFound;
//import messages.Message;
//import partitions.CityPartition;
//import partitions.IdPartition;
//import partitions.Partition;
//import producers.DefaultProducer;
//import producers.Producer;
//import types.Topic;
//
//class Main {
//	public static void main(String[] args) {
//		try {
//			example1();
//			example2();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	// DRIVER_DATA example
//	public static void example1() throws BadPartitionException, NoPartitionFound {
//		Cluster cluster = new DefaultCluster();
//		Broker broker = new DefaultBroker();
//		Partition partition = new IdPartition(Topic.DRIVER_DATA, 123);
//		Producer producer = new DefaultProducer();
//		Consumer consumer = new DefaultConsumer();
//		cluster.addBroker(broker);
//		broker.addPartition(partition);
//
//		String content = "{\"id\": 123, \"location\": {\"lat\": 49.2606, \"lng\": 123.2460}}";
//		Message message = new Message(0, Topic.DRIVER_DATA, content);
//
//		producer.initialize(broker, Topic.DRIVER_DATA);
//		producer.produceMessage(message);
//		producer.close();
//
//		consumer.initialize(broker, Topic.DRIVER_DATA, 123);
//		consumer.consumeMessage();
//		System.out.println(consumer.getMessageBuffer());
//		consumer.close();
//	}
//
//	// DRIVER_DATA example
//	public static void example2() throws BadPartitionException, NoPartitionFound {
//		Cluster cluster = new DefaultCluster();
//		Broker broker = new DefaultBroker();
//		Partition partition = new CityPartition(Topic.RIDER_REQUESTS_RIDE, "vancouver");
//		Producer producer = new DefaultProducer();
//		Consumer consumer = new DefaultConsumer();
//		cluster.addBroker(broker);
//		broker.addPartition(partition);
//
//		String content = "{\"id\": 124, \"city\": \"vancouver\"}";
//		Message message = new Message(0, Topic.RIDER_REQUESTS_RIDE, content);
//
//		producer.initialize(broker, Topic.RIDER_REQUESTS_RIDE);
//		producer.produceMessage(message);
//		producer.close();
//
//		consumer.initialize(broker, Topic.RIDER_REQUESTS_RIDE, "vancouver");
//		consumer.consumeMessage();
//		System.out.println(consumer.getMessageBuffer());
//		consumer.close();
//	}
//}
//*/
