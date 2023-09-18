import java.io.Serializable;
import java.util.Vector;

// con method add vehicle, vaf remove vehicle=

public class Port implements Serializable {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility; // '0' (false) for ship and '1' (true) for truck
    private double currentStoringCapacity;
    private Vector<Vehicle> vehicles;
    private Vector<Container> containers;
    private User portManager;
    private Vector<Trip> trips;

    public Port() {
    }

    public Port(String name, double latitude, double longitude, double storingCapacity, boolean landingAbility) {
        this.id = generateID();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
    }

    public void addContainer(Container container){
        if (container.getWeight() + getCurrentStoringCapacity() <= getStoringCapacity()){
            this.containers.add(container);
            currentStoringCapacity += container.getWeight();
        }else {
            System.out.println("Can not add this container to the port!");
            System.out.println("The current storing capacity of this port is: " + getCurrentStoringCapacity());
            System.out.println("The maximum storing capacity of this port is: " + getStoringCapacity());
        }
    }

    //Remove the container from the port
    public Container removeContainer(String id){
        Container container = findContainerByID(id); // find the container in the port
        try{
            this.containers.remove(container); // remove the container in the port
            return container;
        }catch (NullPointerException e){
            System.out.println("The container does not exist in this Port!");
        }
        return null;
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    //Remove the container from the port
    public Vehicle removeVehicle(String id){
        Vehicle vehicle = findVehicleByID(id); // find the container in the port
        try{
            this.vehicles.remove(vehicle); // remove the container in the port
            return vehicle;
        }catch (NullPointerException e){
            System.out.println("The vehicle does not exist in this Port!");
        }
        return null;
    }

    // Get distance to other port
    public double getDistance(Port port){
        return Math.round(Math.sqrt(Math.pow(this.latitude - port.latitude,2) + Math.pow(this.longitude - port.longitude,2))*100)/100.0;
    }

    // Get distance form the start
    // This is used when adding new vehicle and the vehicle coordinate is 0,0; so we need to calculate the distance of the port to the root coordinate
    public double getDistance(){
        return Math.round(Math.sqrt(Math.pow(this.latitude,2) + Math.pow(this.latitude,2))*100)/100.0;
    }

    // Get all distance to all the ports
    public void getAllDistance(){
        // go with the for loop and using the 'getDistance' method at each port to print out
        System.out.println("The distance to all ports");
        for (Port port : ContainerPortManagementSystem.getPorts()){
            if (port != this){
                System.out.println(port.getId() + ": " + port.getDistance(this));
            }
        }
    }

    public double getCurrentStoringCapacity() {
        return currentStoringCapacity;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public Vector<Container> getContainers() {
        return containers;
    }

    public Vector<Vehicle> getVehicles() {
        return vehicles;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public User getUser() {
        return portManager;
    }

    public void setPortManager(User portManager) {
        this.portManager = portManager;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Trip findTripById(String id){
        for(Trip trip: this.trips){
            if (trip.getId().equals(id)){
                return trip;
            }
        }
        return null;
    }

    public void confirmTrip(String id){
        Trip trip = findTripById(id);
        try{
            this.trips.remove(trip);
        }catch (NullPointerException e){
            System.out.println("The trip does not exist");
        }
    }

    public Container findContainerByID(String id){
        for(Container container: this.containers){
            if (container.getId().equals(id)){
                return container;
            }
        }
        return null;
    }

    public Vehicle findVehicleByID(String id){
        for(Vehicle vehicle: this.vehicles){
            if (vehicle.getID().equals(id)){
                return vehicle;
            }
        }
        return null;
    }

    public void displayTrip() {
        trips.forEach(System.out::println);
    }

    public void addTrip(Trip trip){
        this.trips.add(trip);
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public String generateID(){
        return IDFactory.generateID("port");
    }
}
