import java.io.Serializable;

public class DryStorage extends Container implements Serializable {
    // define constructor
    public DryStorage(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Dry Storage - id: " + getId() + " - weight (Kg): " + getWeight() + " - Vehicle: " + getVehicle();
    }
}
