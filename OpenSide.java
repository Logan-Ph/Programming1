import java.io.Serializable;

public class OpenSide extends Container implements Serializable {
    public OpenSide(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Open Side - id: " + getID() + " - weight (Kg): " + getWEIGHT() + " - Vehicle: " + getVehicle();
    }
}
