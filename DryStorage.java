import java.io.Serializable;

public class DryStorage implements Container, Serializable {
    private String id;
    private double weight;
    private Vehicle vehicle;

    public void setPort(Port port) {
        this.port = port;
    }

    private Port port;

    public DryStorage( double weight, Port port) {
        this.id = generateID();
        this.weight = weight;
        this.vehicle = null;
        this.port = port;
    }

    public DryStorage() {
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

    @Override
    public String toString() {
        return "DryStorage - id: " + getId() + " - weight (Kg): " + getWeight() + " - Vehicle: " + getVehicle();
    }
}
