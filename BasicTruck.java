import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class BasicTruck implements Vehicle, Truck, Serializable {
    private String id;
    private String name;
    private double currentFuel;
    private double fuelCapacity;
    private double storingCapacity;
    private double currentStoringCapacity;

    private Vector<Container> containers;
    private Port port;

    public BasicTruck() {
    }

    // initialize the constructor
    public BasicTruck(String name, double fuelCapacity, double storingCapacity, Port port) {
        this.id = generateID();
        this.name = name;
        this.currentFuel = 0.0;
        this.fuelCapacity = fuelCapacity;
        this.storingCapacity = storingCapacity;
        this.port = port;
    }

    //Load the container to the vehicle
    @Override
    public void load(Container container) {
        if (LoadContainerBehavior.load(container, this) && getCurrentStoringCapacity() + container.getWeight() <= getStoringCapacity()) { // check if that vehicle can load the container or if the container weight exceed the storing capacity
            this.containers.add(container);
            currentStoringCapacity += container.getWeight();
        } else {
            System.out.println("This vehicle cannot carry this container!");
            System.out.println("The current storing capacity of this vehicle is: " + getCurrentStoringCapacity());
            System.out.println("The maximum storing capacity of this vehicle is: " + getStoringCapacity());
        }
    }

    //Get the current storing capacity
    public double getCurrentStoringCapacity() {
        return currentStoringCapacity;
    }

    @Override
    public String generateID() {
        return IDFactory.generateID("truck");
    }

    @Override
    public String getID() {
        return id;
    }

    //Unload the container from the vehicle
    @Override
    public Container unLoad(String id) {
        Container container = findContainerByID(id);
        try {
            this.containers.remove(container); // remove the container from the ArrayList
            return container;
        } catch (Exception e) {
            System.out.println("There is no matching ID container!"); // Throw exception if the container doesn't exist in the ArrayList
            return null;
        }
    }

    //Find the container by using id
    public Container findContainerByID(String id) {
        Container container = null;
        for (Container cont : this.containers) {
            if (cont.getId().equals(id)) {
                container = cont;
            }
        }
        return container;
    }

    //Refueling the vehicle
    @Override
    public void refueling(double fuel) {
        if (getCurrentFuel() + fuel > fuelCapacity) { // check if it does not exceed the fuel capacity
            System.out.println("You can not refuel more than the fuel capacity of this vehicle!");
            System.out.println("The current fuel of the vehicle is: "+getCurrentFuel());
            System.out.println("The maximum fuel capacity of the vehicle is: "+getFuelCapacity());
        } else {
            currentFuel += fuel;
        }
    }

    @Override
    public double calculateFuelConsumption(Port port) {
        double totalFuelConsumption = 0.0;
        for (Container container: containers){
            totalFuelConsumption += container.getWeight()*CalculateFuelBehaviour.calculateFuelConsumption(container,this);
        }
        if (this.port != null){
            return totalFuelConsumption*this.port.getDistance(port);
        }else {
            return totalFuelConsumption*port.getDistance();
        }
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public Port getPort() {
        return port;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    @Override
    public Vector<Container> getContainers() {
        return containers;
    }

    @Override
    public String toString() {
        return "BasicTruck " + " - name: " + getName() + " - id: " + getID() + " - current storing capacity (kg): " + getCurrentStoringCapacity() + " - maximum storing capacity (kg): " + getStoringCapacity() + " - current fuel (L): " + getCurrentFuel() + " - maximum fuel capacity (L): " + getFuelCapacity();
    }
}
