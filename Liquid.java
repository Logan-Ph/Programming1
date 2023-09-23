import java.io.Serializable;

public class Liquid extends Container implements Serializable{
    // define constructor
    public Liquid(double weight, Port port) {
        super(weight, port);
    }

    public Liquid(){
        super();
    }

    @Override
    public String toString() {
        return "Liquid - id: " + getID() + " - weight (Kg): " + getWEIGHT() + " - Vehicle: " + getVehicle();
    }

}
