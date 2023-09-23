import java.io.Serializable;
import java.sql.Ref;

public class Refrigerated extends Container implements Serializable {
    // define constructor
    public Refrigerated(double weight, Port port) {
        super(weight, port);
    }

    public Refrigerated(){
        super();
    }

    @Override
    public String toString() {
        return "Refrigerated - id: " + getID() + " - weight (Kg): " + getWEIGHT() + " - Vehicle: " + getVehicle();
    }
}
