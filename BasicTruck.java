import java.util.ArrayList;

public class BasicTruck implements Vehicle, Truck{
    private String id;
    private String name;
    private double currentFuel;
    private double fuelCapacity;
    private double storingCapacity;
    private ArrayList<Container> containers;
    private Port port;

    public BasicTruck() {
    }

    // initialize the constructor
    public BasicTruck(String id, String name, double fuelCapacity, double storingCapacity) {
        this.id = id;
        this.name = name;
        this.currentFuel = 0.0;
        this.fuelCapacity = fuelCapacity;
        this.storingCapacity = storingCapacity;
        this.port = null;
    }

    //Load the container to the vehicle
    @Override
    public void load(Container container) {
        if(LoadContainerBehavior.load(container,this) && getCurrentStoringCapacity() + container.getWeight()<getStoringCapacity()){ // check if that vehicle can load the container or if the container weight exceed the storing capacity
            this.containers.add(container);
        }else {
            throw new RuntimeException("This vehicle cannot carry this container!"); // throw exception if the container does not match the criteria
        }
    }

    //Get the current storing capacity
    public double getCurrentStoringCapacity(){
        double totalWeight = 0.0;
        for (Container container: this.containers){
            totalWeight+= container.getWeight();
        }
        return totalWeight;
    }

    //Unload the container from the vehicle
    @Override
    public Container unLoad(String id) {
        Container container = findContainerByID(id);
        try {
            this.containers.remove(container); // remove the container from the ArrayList
            return container;
        }catch (Exception e){
            throw new RuntimeException("There is no matching ID container!"); // Throw exception if the container doesn't exist in the ArrayList
        }
    }

    //Find the container by using id
    public Container findContainerByID(String id){
        Container container = null;

        for (Container cont: this.containers){
            if (cont.getId().equals(id)){
                container = cont;
            }
        }
        return container;
    }

    //Refueling the vehicle
    @Override
    public void refueling(double fuel) {
        if (getCurrentFuel() + fuel > fuelCapacity){ // check if it does not exceed the fuel capacity
            throw new RuntimeException("You can not refuel more than the fuel capacity of this vehicle!");
        }else {
            currentFuel += fuel;
        }
    }

    @Override
    public double calculateFuelConsumption(Port port) {
        // calculate distance of the port
        return 0.0;
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
}
