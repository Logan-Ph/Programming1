public abstract class Container {

    private final String ID;
    private final double WEIGHT;
    private Vehicle vehicle;
    private Port port;

    public Container( double weight, Port port) {
        this.ID = generateID();
        this.WEIGHT = weight;
        this.vehicle = null;
        this.port = port;
    }

    public String getID() {
        return ID;
    }

    public double getWEIGHT() {
        return WEIGHT;
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
