package clusters;

import brokers.Broker;

import java.util.ArrayList;

public interface Cluster {
    public ArrayList<Broker> getBrokers();
}
