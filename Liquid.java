import java.io.Serializable;

public class Liquid extends Container implements Serializable{
    // define constructor
    public Liquid(double weight, Port port) {
        super(weight, port);
    }

    @Override
    public String toString() {
        return "Liquid - id: " + getId() + " - weight (Kg): " + getWeight() + " - Vehicle: " + getVehicle();
    }

}
