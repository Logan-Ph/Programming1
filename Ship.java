import java.io.Serializable;
import java.util.Scanner;
import java.util.Vector;

public class Ship implements Vehicle, Serializable {
    private String id;
    private String name;
    private double currentFuel;
    private double fuelCapacity;
    private double storingCapacity;
    private double currentStoringCapacity;
    private Vector<Container> containers;
    private Port port;

    // initialize the constructor
    public Ship(String name, double fuelCapacity, double storingCapacity, Port port) {
        this.id = generateID();
        this.name = name;
        this.currentFuel = 0.0;
        this.fuelCapacity = fuelCapacity;
        this.storingCapacity = storingCapacity;
        this.port = port;
        this.containers = new Vector<>();
    }

    public Ship() {
    }

    //Load the container to the vehicle
    @Override
    public boolean load(Container container) {
        if (getCurrentStoringCapacity() + container.getWEIGHT() <= getStoringCapacity()) { // check if that vehicle can load the container or if the container weight exceed the storing capacity
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
        return IDFactory.generateID("ship");
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
            currentStoringCapacity -= container.getWEIGHT();
            return container;
        } catch (Exception e) {
            System.out.println("There is no matching ID container!"); // Throw exception if the container doesn't exist in the ArrayList
            return null;
        }
    }

    //Find the container by using id
    public Container findContainerByID(String id) {
        return this.containers.stream().filter(container -> container.getID().equals(id)).findAny().orElse(null);
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
            return false;
        }

        if (getCurrentFuel() + fuel > fuelCapacity) { // check if it does not exceed the fuel capacity
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
        for (Container container : containers) {
            totalFuelConsumption += container.getWEIGHT() * CalculateFuelBehaviour.calculateFuelConsumption(container, this); // calculate fuel consumption
        }
        return totalFuelConsumption * this.port.getDistance(port);
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

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    @Override
    public void setName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter new vehicle name: ");
        this.name = input.next();
        System.out.println("Update vehicle name successfully.");
    }

    @Override
    public String toString() {
        return "Ship" + " - name: " + getName() + " - id: " + getID() + " - current storing capacity (kg): " + getCurrentStoringCapacity() + " - maximum storing capacity (kg): " + getStoringCapacity() + " - current fuel (Gallon): " + getCurrentFuel() + " - maximum fuel capacity (Gallon): " + getFuelCapacity() + " - current port: " + getPort();
    }

}
