package clusters;

import brokers.Broker;

import java.util.ArrayList;

public class DefaultCluster implements Cluster {
    ArrayList<Broker> brokers;

    public DefaultCluster() {
        this.brokers = new ArrayList<Broker>();
    }

    public ArrayList<Broker> getBrokers() {
        return brokers;
    }
}
