public abstract class Container {

    private final String id;
    private final double weight;
    private Vehicle vehicle;
    private Port port;

    public Container( double weight, Port port) {
        this.id = generateID();
        this.weight = weight;
        this.vehicle = null;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String generateID() {
        return IDFactory.generateID("container");
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public abstract String toString();
}
