
import java.io.Serializable;

public class TankerTruck extends Truck implements Vehicle, Serializable {

    public TankerTruck(String name, double fuelCapacity, double storingCapacity, Port port) {
        super(name, fuelCapacity, storingCapacity, port);
    }

    @Override
    public String toString() {
        return "Tanker Truck" + " - name: " + getName() + " - id: " + getID() + " - current storing capacity (kg): " + getCurrentStoringCapacity() + " - maximum storing capacity (kg): " + getStoringCapacity() + " - current fuel (Gallon): " + getCurrentFuel() + " - maximum fuel capacity (Gallon): " + getFuelCapacity() + " - current port: " + getPort();
    }
}
