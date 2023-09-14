import java.io.Serializable;

public class OpenTop implements Container, Serializable {
    private String id;
    private double weight;
    private Vehicle vehicle;

    public OpenTop(String id, double weight) {
        this.id = id;
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
}
