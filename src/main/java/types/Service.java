package types;

import partitions.IdPartition;

import java.util.Objects;

public class Service {
    public String name;
    public String ip;
    public int port;
    private int hashCode;

    public Service(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.hashCode = Objects.hash(name, ip, port);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Service)) {
            return false;
        }
        Service otherService = (Service) obj;

        return otherService.name.equals(this.name) && otherService.ip.equals(this.ip) && otherService.port == this.port;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
