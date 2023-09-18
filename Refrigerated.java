import java.io.Serializable;

public class Refrigerated implements Container, Serializable {
    private String id;
    private double weight;
    private Vehicle vehicle;

    public Refrigerated(double weight) {
        this.id = generateID();
        this.weight = weight;
        this.vehicle = null;
    }

    public Refrigerated() {
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


}
