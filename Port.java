import java.io.Serializable;
import java.util.ArrayList;
public class Port implements Serializable {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility; // '0' (false) for ship and '1' (true) for truck
    private double currentCapacity;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Container> containers;
    private PortManager user;
    private ArrayList<Trip> trips;

    public Port() {
    }

    public Port(String name, double latitude, double longitude, double storingCapacity, boolean landingAbility, double currentCapacity, PortManager user) {
        this.id = generateID();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.currentCapacity = currentCapacity;
        this.user = user;
    }

    public Container findContainerByID(String id){
        for(Container container: this.containers){
            if (container.getId().equals(id)){
                return container;
            }
        }
        return null;
    }

    public void addContainers(Container container){
        if (container.getWeight() + getCurrentCapacity() <= getStoringCapacity()){
            this.containers.add(container);
        }else {
            System.out.println("Can not add this container!");
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

    public double getCurrentCapacity() {
        return currentCapacity;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public PortManager getUser() {
        return user;
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

    public void displayTrip(String id){
        Trip trip = findTripById(id);
        try{
            System.out.println(trip);
        }catch (NullPointerException e){
            System.out.println("The trip does not exist");
        }
    }

    public void displayTrip(Trip trip){
        if (trips.contains(trip)){
            System.out.println(trip);
        }else {
            System.out.println("The trip does not exist");
        }
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
