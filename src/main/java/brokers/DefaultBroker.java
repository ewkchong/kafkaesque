package brokers;

import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
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
            messages.add(m);
            offset += 1;
            p.readMessage(offset);
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
            messages.add(m);
            offset += 1;
            p.readMessage(offset);
        }

        return messages;
    }

    public void produce(Message message) {
        // TODO add message to partition corresponding to topic/id.
        Topic topic = message.topic;
        if (topic == Topic.RIDER_REQUESTS_RIDE) {
            // get city

        } else {
            // get id
        }
    }

    private Partition findPartition(Topic topic, String city) throws NoPartitionFound {
        for (Partition p : partitions) {
            if (p instanceof CityPartition) {
                CityPartition cp = (CityPartition) p;
                if (p.getTopic() == topic && city == cp.getCity()) {
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
