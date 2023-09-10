import java.util.ArrayList;
public class Port {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private double currentCapacity;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Container> containers;
    private PortManager user;
    private ArrayList<Trip> trips;

    public Port() {
    }

    public Port(String id, String name, double latitude, double longitude, double storingCapacity, boolean landingAbility, double currentCapacity, PortManager user) {
        this.id = id;
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
            throw new RuntimeException("Can not add this container!");
        }
    }

    //Remove the container from the port
    public Container removeContainer(String id){
        Container container = findContainerByID(id); // find the container in the port
        try{
            this.containers.remove(container); // remove the container in the port
            return container;
        }catch (Exception e){
            throw new RuntimeException("The container does not exist in this Port!");
        }
    }

    // Get distance to other port
    public double getDistance(Port port){
        return Math.round(Math.sqrt(Math.pow(this.latitude - port.latitude,2) + Math.pow(this.longitude - port.longitude,2))*100)/100.0;
    }

    // Get all distance to all the ports
    public void getAllDistance(){

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

    public void confirmTrip(String id){}

    public Trip findTripById(String id){
    return null;
    }

    public void removeTrip(){}

    public void displayTrip(){}

    public void addTrip(Trip trip){
        this.trips.add(trip);
    }
}
