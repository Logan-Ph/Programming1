public class Refrigerated implements Container{
    private String id;
    private double weight;
    private Vehicle vehicle;

    public Refrigerated(String id, double weight) {
        this.id = id;
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
}
