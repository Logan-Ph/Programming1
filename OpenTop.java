import java.io.Serializable;

public class OpenTop extends Container implements Serializable {
    // define constructor
    public OpenTop(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Open Top - id: " + getId() + " - weight (Kg): " + getWeight() + " - Vehicle: " + getVehicle();
    }
}
