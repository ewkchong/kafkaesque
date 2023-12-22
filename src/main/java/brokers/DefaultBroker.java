package brokers;

import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import org.json.JSONObject;
import partitions.CityPartition;
import partitions.IdPartition;
import partitions.Partition;
import types.Service;
import types.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBroker implements Broker {
    private Service service;
    private Map<Topic, List<Partition>> partitions = new HashMap<>();
    private static final Map<Topic, Map<Integer, Service>> partitionsToBrokers = new HashMap<>();

    public DefaultBroker(String name, String ip, int port) {
        // setup static brokers to partitions mapping configuration
        Map<Integer, Service> brokerMappings = new HashMap<>();
        brokerMappings.put(0, new Service("node-1", "127.0.0.1", 8080));
        brokerMappings.put(1, new Service("node-2", "127.0.0.1", 8081));
        brokerMappings.put(2, new Service("node-3", "127.0.0.1", 8082));
        brokerMappings.put(3, new Service("node-4", "127.0.0.1", 8083));

        // init partition mappings and list for each topic
        for (Topic t : Topic.values()) {
            this.partitionsToBrokers.put(t, brokerMappings);
            this.partitions.put(t, new ArrayList<Partition>());
        }

        this.service = new Service(name, ip, port);

        // Check that the current broker in valid
        for (Map<Integer, Service> m : partitionsToBrokers.values()) {
            for (Service s : m.values()) {
                if (s.equals(this.service)) {
                    return;
                }
            }
        }
        assert false : "Broker is not in the list of valid brokers";
    }

    public Map<Topic, List<Partition>> getPartitions() {
        return partitions;
    }

    public List<Message> consume(Topic topic, int id, int offset) throws BadPartitionException, NoPartitionFound {
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException(String.format("%s partitions by city not id", topic));
        }
        List<Message> messages = new ArrayList<>();

        if (!this.service.equals(findBroker(topic, id))) {
            throw new NoPartitionFound(String.format("Requested id %d not on this broker", id));
        }
        Partition p = findPartition(topic, id);
        Message m = p.readMessage(offset);
        while (m != null) {
            System.out.printf("consuming: " + m.content);
            messages.add(m);
            offset += 1;
            m = p.readMessage(offset);
        }

        return messages;
    }

    public List<Message> consume(Topic topic, String city, int offset) throws BadPartitionException, NoPartitionFound {
        if (topic != Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException(String.format("%s partitions by id not city", topic));
        }
        List<Message> messages = new ArrayList<>();

        if (!this.service.equals(findBroker(topic, city))) {
            throw new NoPartitionFound(String.format("Requested city %s not on this broker", city));
        }
        Partition p = findPartition(topic, city);
        Message m = p.readMessage(offset);
        while (m != null) {
            System.out.printf("consuming: " + m.content);
            messages.add(m);
            offset += 1;
            m = p.readMessage(offset);
        }

        return messages;
    }

    public void produce(Message message) throws NoPartitionFound, BadPartitionException {
        Topic topic = message.topic;
        System.out.printf(message.content);
        JSONObject obj = new JSONObject(message.content);
        Partition p;
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            // get city
            String city = obj.getString("city");
            if (!this.service.equals(findBroker(topic, city))) {
                throw new NoPartitionFound(String.format("Requested city %s not on this broker", city));
            }
            p = findPartition(topic, city);

        } else {
            // get id
            int id = obj.getInt("id");
            if (!this.service.equals(findBroker(topic, id))) {
                throw new NoPartitionFound(String.format("Requested id %d not on this broker", id));
            }
            p = findPartition(topic, id);
        }
        p.appendMessage(message);
    }

    public Service findBroker(Topic topic, int id) throws BadPartitionException{
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException(String.format("%s partitions by city not id", topic));
        }
        Service s = partitionsToBrokers.get(topic).get(id % 4);
        if (s == null) {
            throw new BadPartitionException(String.format("Cannot find mapping for partition with id %d", id));
        }
        return s;
    }

    public Service findBroker(Topic topic, String city) throws BadPartitionException{
        if (topic != Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException(String.format("%s partitions by id not city", topic));
        }
        int key = (int)city.charAt(0) % 4;
        Service s = partitionsToBrokers.get(topic).get(key);
        if (s == null) {
            throw new BadPartitionException(String.format("Cannot find mapping for partition with city %s", city));
        }
        return s;
    }

    // find the requested partition. Create one if it does not exist
    private Partition findPartition(Topic topic, String city) throws BadPartitionException {
        for (Partition p : this.partitions.get(topic)) {
            CityPartition cp = (CityPartition)p;
            if (city.equals(cp.getCity())) {
                return cp;
            }
        }
        Partition newPartition = new CityPartition(topic, city);
        this.partitions.get(topic).add(newPartition);
        return newPartition;
    }

    private Partition findPartition(Topic topic, int id) throws BadPartitionException{
        for (Partition p : this.partitions.get(topic)) {
            IdPartition idp = (IdPartition)p;
            if (id == idp.getId()) {
                return idp;
            }
        }
        Partition newPartition = new IdPartition(topic, id);
        this.partitions.get(topic).add(newPartition);
        return newPartition;
    }
}
