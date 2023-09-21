import java.io.Serializable;
import java.time.LocalDate;
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

    public boolean addContainer(Container container) {
        if (container.getWeight() + getCurrentStoringCapacity() <= getStoringCapacity()) {
            this.containers.add(container);
            currentStoringCapacity += container.getWeight();
            return true;
        } else {
            System.out.println("Can not add this container to the port!");
            System.out.println("The current storing capacity of this port is: " + getCurrentStoringCapacity());
            System.out.println("The maximum storing capacity of this port is: " + getStoringCapacity());
            return false;
        }
    }

    //Remove the container from the port
    public Container removeContainer(String id) {
        Container container = findContainerByID(id); // find the container in the port
        try {
            this.containers.remove(container); // remove the container in the port
            currentStoringCapacity -= container.getWeight();
            container.setPort(null);
            return container;
        } catch (NullPointerException e) {
            System.out.println("The container does not exist in this Port!");
        }
        return null;
    }

    public Container removeContainer(Container container) {
        try {
            this.containers.remove(container); // remove the container in the port
            currentStoringCapacity -= container.getWeight();
            container.setPort(null);
            return container;
        } catch (NullPointerException e) {
            System.out.println("The container does not exist in this Port!");
        }
        return null;
    }

    public boolean addVehicle(Vehicle vehicle) {
        if (LandingBehaviour.landing(this, vehicle)) {
            vehicles.add(vehicle);
            return true;
        } else {
            System.out.println("This vehicle can not land at this port");
            return false;
        }
    }

    //Remove the container from the port
    public Vehicle removeVehicle(String id) {
        Vehicle vehicle = findVehicleByID(id); // find the container in the port
        try {
            this.vehicles.remove(vehicle); // remove the container in the port
            return vehicle;
        } catch (NullPointerException e) {
            System.out.println("The vehicle does not exist in this Port!");
        }
        return null;
    }

    public Vehicle removeVehicle(Vehicle vehicle) {
        try {
            this.vehicles.remove(vehicle); // remove the container in the port
            return vehicle;
        } catch (NullPointerException e) {
            System.out.println("The vehicle does not exist in this Port!");
        }
        return null;
    }

    public Vector<Trip> listAllTripFromDayAToB(LocalDate startTime, LocalDate endTime) {
        Vector<Trip> listTripOut = new Vector<>();
        for (Trip currentTrip : this.getTrips()) {
            System.out.println(currentTrip);

            if ((!currentTrip.getStatus() && currentTrip.getDepartureDate().isAfter(startTime) && currentTrip.getDepartureDate().isBefore(endTime)) || (currentTrip.getStatus() && ((currentTrip.getArrivalDate().isAfter(startTime) && currentTrip.getArrivalDate().isBefore(endTime)) || (currentTrip.getDepartureDate().isBefore(endTime) && currentTrip.getDepartureDate().isAfter(startTime))))) {
                listTripOut.add(currentTrip);
            }
        }
        return listTripOut;
    }

    public Vector<Trip> listAllTripInDay(LocalDate date) {
        Vector<Trip> listTripOut = new Vector<>();
        for (Trip currentTrip : this.getTrips()) {
            System.out.println(currentTrip);
            if ((currentTrip.getStatus() && (currentTrip.getArrivalDate().isEqual(date) || currentTrip.getDepartureDate().isEqual(date))) || (!currentTrip.getStatus() && currentTrip.getDepartureDate().isEqual(date))) {
                listTripOut.add(currentTrip);
            }
        }
        return listTripOut;
    }

    public double amountFuelUsedInDay(LocalDate date) {
        Vector<Trip> trips;
        trips = listAllTripInDay(date);
        double amountFuel = 0;
        if (trips.isEmpty()) {
            return 0;
        } else {
            for (Trip trip : trips) {
                if (trip.getDeparturePort() == this) {
                    amountFuel += trip.getAmountFuel();
                }
            }
            return amountFuel;
        }
    }

    // Get distance to other port
    public double getDistance(Port port) {
        return Math.round(Math.sqrt(Math.pow(this.latitude - port.latitude, 2) + Math.pow(this.longitude - port.longitude, 2)) * 100) / 100.0;
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

    public Trip findTripById(String id) {
        for (Trip trip : this.trips) {
            if (trip.getId().equals(id)) {
                return trip;
            }
        }
        return null;
    }

    public Vector<Trip> getTrips() {
        return trips;
    }

    public void confirmTrip(String id) {
        Trip trip = findTripById(id);
        try {
            if (trip.getArrivalPort() == this && !trip.getStatus()) {
                trip.setStatus(true);
                trip.setArrivalDate();
            } else {
                System.out.println("There are no trip to confirm");
            }
        } catch (NullPointerException e) {
            System.out.println("The trip does not exist");
        }
    }

    public boolean checkTrip() {
        for (Trip trip : trips) {
            if (!trip.getStatus() && trip.getArrivalPort() == this) {
                return true; // return true if there is any trip to confirm
            }
        }
        return false;
    }

    public Container findContainerByID(String id) {
        for (Container container : this.containers) {
            if (container.getId().equals(id)) {
                return container;
            }
        }
        return null;
    }

    public Vehicle findVehicleByID(String id) {
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.getID().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public String generateID() {
        return IDFactory.generateID("port");
    }

    public void setCurrentStoringCapacity(double currentStoringCapacity) {
        this.currentStoringCapacity = currentStoringCapacity;
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
