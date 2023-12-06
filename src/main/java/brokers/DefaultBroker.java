package brokers;

import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import org.json.JSONObject;
import partitions.CityPartition;
import partitions.IdPartition;
import partitions.Partition;
import topics.Topic;

import java.util.ArrayList;
import java.util.List;

public class DefaultBroker implements Broker {
    ArrayList<Partition> partitions;

    public DefaultBroker() {
        this.partitions = new ArrayList<Partition>();
    }

    public ArrayList<Partition> getPartitions() {
        return partitions;
    }
    public void addPartition(Partition partition) {
        partitions.add(partition);
    }

    public List<Message> consume(Topic topic, int id, int offset) throws BadPartitionException, NoPartitionFound {
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            throw new BadPartitionException(String.format("%s partitions by id not city", topic.toString()));
        }
        List<Message> messages = new ArrayList<Message>();

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
        List<Message> messages = new ArrayList<Message>();

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

    public void produce(Message message) throws NoPartitionFound {
        // TODO add message to partition corresponding to topic/id.
        Topic topic = message.topic;
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

    // TODO return type? it should be port of broker
    public int consumeInit(Broker broker, Topic topic, int id) {

        return -1;
    }

    public int produceInit(Broker broker, Topic topic) {
        return -1;
    }

    private Partition findPartition(Topic topic, String city) throws NoPartitionFound {
        for (Partition p : partitions) {
            if (p instanceof CityPartition) {
                CityPartition cp = (CityPartition) p;
                if (p.getTopic() == topic && city.equals(cp.getCity())) {
                    return p;
                }
            }
        }
        throw new NoPartitionFound();
    }

    private Partition findPartition(Topic topic, int id) throws NoPartitionFound {
        for (Partition p : partitions) {
            if (p instanceof IdPartition) {
                IdPartition idp = (IdPartition) p;
                if (p.getTopic() == topic && id == idp.getId()) {
                    return p;
                }
            }
        }
        throw new NoPartitionFound();
    }
}
