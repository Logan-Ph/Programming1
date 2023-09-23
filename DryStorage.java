import java.io.Serializable;

public class DryStorage extends Container implements Serializable {
    // define constructor
    public DryStorage(double weight, Port port) {
        super(weight, port);
    }

    public DryStorage(){
        super();
    }

    @Override
    public String toString() {
        return "Dry Storage - id: " + getID() + " - weight (Kg): " + getWEIGHT() + " - Vehicle: " + getVehicle();
    }
}
