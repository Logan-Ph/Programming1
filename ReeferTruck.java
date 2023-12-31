import java.io.Serializable;

public class ReeferTruck extends Truck implements Vehicle, Serializable {
    // Reefer truck constructor
    public ReeferTruck(String name, double fuelCapacity, double storingCapacity, Port port) {
        super(name, fuelCapacity, storingCapacity, port);
    }

    public ReeferTruck(){
        super();
    }

    @Override
    public String toString() {
        return "Reefer Truck" + " - name: " + getName() + " - id: " + getID() + " - current storing capacity (kg): " + getCurrentStoringCapacity() + " - maximum storing capacity (kg): " + getStoringCapacity() + " - current fuel (Gallon): " + getCurrentFuel() + " - maximum fuel capacity (Gallon): " + getFuelCapacity() + " - current port: " + getPort();
    }
}
