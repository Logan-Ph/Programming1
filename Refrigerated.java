import java.io.Serializable;

public class Refrigerated extends Container implements Serializable {
    public Refrigerated(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Refrigerated - id: " + getId() + " - weight (Kg): " + getWeight() + " - Vehicle: " + getVehicle();
    }
}
