import java.io.Serializable;

public class OpenTop implements Container, Serializable {
    private String id;
    private double weight;
    private Vehicle vehicle;
    private Port port;

    public OpenTop( double weight, Port port) {
        this.id = generateID();
        this.weight = weight;
        this.vehicle = null;
    }

    public OpenTop() {
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

    @Override
    public String generateID() {
        return IDFactory.generateID("container");
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "OpenTop - id: " + getId() + " - weight (Kg): " + getWeight() + " - Vehicle: " + getVehicle();
    }
}
