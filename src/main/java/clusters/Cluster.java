package clusters;

import brokers.Broker;
import partitions.Partition;

import java.util.ArrayList;

public interface Cluster {
    public ArrayList<Broker> getBrokers();
    public void addBroker(Broker broker);

}
