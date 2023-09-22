import java.util.Scanner;
import java.util.Vector;

public abstract class Truck implements Vehicle {
    private final String ID;
    private final String NAME;
    private double currentFuel;
    private final double FUEL_CAPACITY;
    private final double STORING_CAPACITY;
    private double currentStoringCapacity;

    private Vector<Container> containers;
    private Port port;

    // initialize the constructor
    public Truck(String name, double fuelCapacity, double storingCapacity, Port port) {
        this.ID = generateID();
        this.NAME = name;
        this.currentFuel = 0.0;
        this.FUEL_CAPACITY = fuelCapacity;
        this.STORING_CAPACITY = storingCapacity;
        this.port = port;
        this.containers = new Vector<>();
    }

    //Load the container to the vehicle
    @Override
    public boolean load(Container container) {
        if (LoadContainerBehavior.load(container, this) && getCurrentStoringCapacity() + container.getWEIGHT() <= getStoringCapacity()) { // check if that vehicle can load the container or if the container weight exceed the storing capacity
            this.containers.add(container);
            currentStoringCapacity += container.getWEIGHT();
            return true;
        } else {
            System.out.println("This vehicle cannot carry this container!");
            System.out.println("The current storing capacity of this vehicle is: " + getCurrentStoringCapacity());
            System.out.println("The maximum storing capacity of this vehicle is: " + getStoringCapacity());
            return false;
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
        return ID;
    }

    //Unload the container from the vehicle
    @Override
    public Container unLoad(String id) {
        Container container = findContainerByID(id);
        try {
            this.containers.remove(container); // remove the container from the ArrayList
            currentStoringCapacity -= container.getWEIGHT();
            return container;
        } catch (Exception e) {
            System.out.println("There is no matching ID container!"); // Throw exception if the container doesn't exist in the ArrayList
            return null;
        }
    }

    //Find the container by using ID
    public Container findContainerByID(String id) {
        Container container = null;
        for (Container cont : this.containers) {
            if (cont.getID().equals(id)) {
                container = cont;
            }
        }
        return container;
    }

    //Refueling the vehicle
    @Override
    public boolean refueling() {
        Scanner input = new Scanner(System.in);
        double fuel;
        System.out.print("Enter the amount you want to refuel: ");
        try {
            fuel = Double.parseDouble(input.nextLine());
        } catch (RuntimeException e) {
            System.out.println("The amount must be a number");
            return false;
        }
        if (fuel < 0) {
            System.out.println("The amount can not be negative");
        }

        if (getCurrentFuel() + fuel > FUEL_CAPACITY) { // check if it does not exceed the fuel capacity
            System.out.println("You can not refuel more than the fuel capacity of this vehicle!");
            System.out.println("The current fuel of the vehicle is: " + getCurrentFuel());
            System.out.println("The maximum fuel capacity of the vehicle is: " + getFuelCapacity());
            return false;
        } else {
            currentFuel += fuel;
            return true;
        }
    }

    @Override
    public double calculateFuelConsumption(Port port) {
        double totalFuelConsumption = 0.0;
        try {
            for (Container container : containers) {
                totalFuelConsumption += container.getWEIGHT() * CalculateFuelBehaviour.calculateFuelConsumption(container, this);
            }
            return (double) Math.round(totalFuelConsumption * this.port.getDistance(port)*100)/100.0 ;
        } catch (NullPointerException e) {
            return 1.0;
        }
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public String getNAME() {
        return NAME;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public Port getPort() {
        return port;
    }

    public double getStoringCapacity() {
        return STORING_CAPACITY;
    }

    public double getFuelCapacity() {
        return FUEL_CAPACITY;
    }

    @Override
    public Vector<Container> getContainers() {
        return containers;
    }

    public abstract String toString();
}
