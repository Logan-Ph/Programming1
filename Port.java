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
        this.vehicles = new Vector<>();
        this.containers = new Vector<>();
        this.trips = new Vector<>();
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

    public boolean addVehicle(Vehicle vehicle){
        if (LandingBehaviour.landing(this, vehicle)){
            vehicles.add(vehicle);
            return true;
        }else {
            System.out.println("This vehicle can not land at this port");
            return false;
        }
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

    public Vehicle removeVehicle(Vehicle vehicle){
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

    public void addTrip(Trip trip){
        this.trips.add(trip);

    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public String generateID(){
        return IDFactory.generateID("port");
    }

    @Override
    public String toString() {
        return "Port{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", storingCapacity=" + storingCapacity +
                ", landingAbility=" + landingAbility +
                ", currentStoringCapacity=" + currentStoringCapacity +
                '}';
    }
}
