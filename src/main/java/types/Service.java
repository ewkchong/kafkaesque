package types;

import partitions.IdPartition;

import java.util.Objects;

public class Service {
    public String name;
    public int port;
    private int hashCode;

    public Service(String name, int port) {
        this.name = name;
        this.port = port;
        this.hashCode = Objects.hash(name, port);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Service)) {
            return false;
        }
        Service otherService = (Service) obj;

        return otherService.name.equals(this.name) && otherService.port == this.port;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
