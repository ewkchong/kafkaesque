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
    List<Partition> partitions;

    public static final Map<Service, List<Partition>> brokersToPartitions = new HashMap<>();


    public DefaultBroker(String name, int port) {
        // setup static brokers
        List<Partition> partitions_1 = new ArrayList<>();
        List<Partition> partitions_2 = new ArrayList<>();
        List<Partition> partitions_3 = new ArrayList<>();
        List<Partition> partitions_4 = new ArrayList<>();

        try {
            // TODO set these to other values. right now only broker "node-1"
            partitions_1.add(new IdPartition(Topic.DRIVER_DATA,1));
            partitions_1.add(new IdPartition(Topic.RIDER_DATA,1));
            partitions_1.add(new CityPartition(Topic.RIDER_REQUESTS_RIDE,"Vancouver"));
            partitions_1.add(new IdPartition(Topic.DRIVER_REQUESTS_RIDE,1));
            partitions_1.add(new IdPartition(Topic.RIDER_ACCEPTS_RIDE,1));
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }
        brokersToPartitions.put(new Service("node-1", 8080), partitions_1);
        brokersToPartitions.put(new Service("node-2", 8080), partitions_2);
        brokersToPartitions.put(new Service("node-3", 8080), partitions_3);
        brokersToPartitions.put(new Service("node-4", 8080), partitions_4);


        // init member variables
        this.partitions = brokersToPartitions.get(new Service(name, port));
        assert(this.partitions != null);
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public List<Message> consume(Topic topic, int id, int offset) throws BadPartitionException, NoPartitionFound {
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException(String.format("%s partitions by id not city", topic));
        }
        List<Message> messages = new ArrayList<>();

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
            throw new BadPartitionException(String.format("%s partitions by id not city", topic.toString()));
        }
        List<Message> messages = new ArrayList<>();

        Partition p = findPartition(topic, city);
        System.out.println(p);
        Message m = p.readMessage(offset);
        System.out.println(m);
        while (m != null) {
            System.out.printf("consuming: " + m.content);
            messages.add(m);
            offset += 1;
            m = p.readMessage(offset);
        }

        return messages;
    }

    public void produce(Message message) throws NoPartitionFound {
        Topic topic = message.topic;
        System.out.printf(message.content);
        JSONObject obj = new JSONObject(message.content);
        Partition p;
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            // get city
            String city = obj.getString("city");
            p = findPartition(topic, city);

        } else {
            // get id
            int id = obj.getInt("id");
            p = findPartition(topic, id);
        }
        p.appendMessage(message);
    }

    public Service clientInit(Topic topic, int id) throws NoPartitionFound {
        for (var entry : brokersToPartitions.entrySet()) {
            try {
                findPartition(entry.getValue(), topic, id);
                return entry.getKey();
            }
            catch(NoPartitionFound e) {
                // continue
            }
        }
        throw new NoPartitionFound();
    }

    public Service clientInit(Topic topic, String city) throws NoPartitionFound {
        for (var entry : brokersToPartitions.entrySet()) {
            try {
                findPartition(entry.getValue(), topic, city);
                return entry.getKey();
            }
            catch(NoPartitionFound e) {
                // continue
            }
        }
        throw new NoPartitionFound();
    }

    private Partition findPartition(Topic topic, String city) throws NoPartitionFound {
        return findPartition(this.partitions, topic, city);
    }

    private Partition findPartition(Topic topic, int id) throws NoPartitionFound {
        return findPartition(this.partitions, topic, id);
    }

    private static Partition findPartition(List<Partition> partitions, Topic topic, String city) throws NoPartitionFound {
        for (Partition p : partitions) {
            if (p instanceof CityPartition cp) {
                if (p.getTopic() == topic && city.equals(cp.getCity())) {
                    return p;
                }
            }
        }
        throw new NoPartitionFound();
    }

    private static Partition findPartition(List<Partition> partitions, Topic topic, int id) throws NoPartitionFound {
        for (Partition p : partitions) {
            if (p instanceof IdPartition idp) {
                if (p.getTopic() == topic && id == idp.getId()) {
                    return p;
                }
            }
        }
        throw new NoPartitionFound();
    }
}
