import java.io.Serializable;

public class Refrigerated extends Container implements Serializable {
    // define constructor
    public Refrigerated(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Refrigerated - id: " + getID() + " - weight (Kg): " + getWEIGHT() + " - Vehicle: " + getVehicle();
    }
}
