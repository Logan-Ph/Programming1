import java.io.Serializable;

public class DryStorage extends Container implements Serializable {
    public DryStorage(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Dry Storage - id: " + getID() + " - weight (Kg): " + getWEIGHT() + " - Vehicle: " + getVehicle();
    }
}
