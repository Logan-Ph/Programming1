import java.io.Serializable;

public class BasicTruck extends Truck implements Vehicle, Serializable {
    public BasicTruck(String name, double fuelCapacity, double storingCapacity, Port port) {
        super(name, fuelCapacity, storingCapacity, port);
    } // define constructor

    public BasicTruck(){
        super();
    }

    @Override
    public String toString() {
        return "Basic Truck" + " - name: " + getName() + " - id: " + getID() + " - current storing capacity (kg): " + getCurrentStoringCapacity() + " - maximum storing capacity (kg): " + getStoringCapacity() + " - current fuel (Gallon): " + getCurrentFuel() + " - maximum fuel capacity (Gallon): " + getFuelCapacity() + " - current port: " + getPort();
    }
}
