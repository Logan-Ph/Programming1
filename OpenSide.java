import java.io.Serializable;

public class OpenSide extends Container implements Serializable {
    // define constructor
    public OpenSide(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Open Side - id: " + getId() + " - weight (Kg): " + getWeight() + " - Vehicle: " + getVehicle();
    }
}
