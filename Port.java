import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;

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
            this.containers.add(container); // add container to vector
            currentStoringCapacity += container.getWeight(); // update current storing of the port
            return true;
        } else {
            // Print error message if the container cannot be added to the port
            System.out.println("Can not add this container to the port!");
            System.out.println("The current storing capacity of this port is: " + getCurrentStoringCapacity());
            System.out.println("The maximum storing capacity of this port is: " + getStoringCapacity());
            return false;
        }
    }

    //Remove the container from the port using ID parameter
    public Container removeContainer(String id) {
        Container container = findContainerByID(id); // find the container in the port
        try {
            this.containers.remove(container); // remove the container in the port
            currentStoringCapacity -= container.getWeight();
            container.setPort(null);
            return container;
        } catch (NullPointerException e) { // catch exception when the id does not match any container in the port
            System.out.println("The container does not exist in this Port!");
        }
        return null;
    }

    // Remove the container from the port using object parameter
    public void removeContainer(Container container) {
        try {
            this.containers.remove(container); // remove the container in the port
            currentStoringCapacity -= container.getWeight();
            container.setPort(null);
        } catch (NullPointerException e) { // catch exception when the container does not exist in the port
            System.out.println("The container does not exist in this Port!");
        }
    }

    // Add vehicle to the port and validate whether the vehicle can land at that port
    public boolean addVehicle(Vehicle vehicle) {
        if (LandingBehaviour.landing(this, vehicle)) {
            vehicles.add(vehicle);
            return true;
        } else {
            System.out.println("This vehicle can not land at this port");
            return false;
        }
    }

    // Remove the container from the port
    public Vehicle removeVehicle(String id) {
        Vehicle vehicle = findVehicleByID(id); // find the container in the port
        try {
            this.vehicles.remove(vehicle); // remove the container in the port
            return vehicle;
        } catch (NullPointerException e) {
            System.out.println("The vehicle does not exist in this Port!");
            return null;
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        try {
            this.vehicles.remove(vehicle); // remove the vehicle in the port
        } catch (NullPointerException e) { // catch exception if the vehicle cannot be found at the port
            System.out.println("The vehicle does not exist in this Port!");
        }
    }

    public Vector<Trip> listAllTripFromDayAToB(LocalDate startTime, LocalDate endTime) {
        Vector<Trip> listTripOut = new Vector<>(); // create new vector to return
        for (Trip currentTrip : this.getTrips()) {
            // Trips is validated when either the departure days or arrival day is in the given day range.
            if ((!currentTrip.getStatus() && (currentTrip.getDepartureDate().isAfter(startTime)||currentTrip.getDepartureDate().isEqual(startTime)) && (currentTrip.getDepartureDate().isBefore(endTime))||currentTrip.getDepartureDate().isEqual(endTime)) || (currentTrip.getStatus() && (((currentTrip.getArrivalDate().isAfter(startTime)||currentTrip.getArrivalDate().isEqual(startTime)) && (currentTrip.getArrivalDate().isBefore(endTime)||currentTrip.getArrivalDate().isEqual(endTime))) || ((currentTrip.getDepartureDate().isBefore(endTime)||currentTrip.getDepartureDate().isEqual(endTime)) && (currentTrip.getDepartureDate().isAfter(startTime)||currentTrip.getDepartureDate().isEqual(startTime)))))) {
                listTripOut.add(currentTrip); // add validated trip to the vector
            }
        }
        return listTripOut;
    }

    public Vector<Trip> listAllTripInDay(LocalDate date) {
        Vector<Trip> listTripOut = new Vector<>(); // create new vector to return
        for (Trip currentTrip : this.getTrips()) {
            // Trips is validated when either the departure day or arrival day is the given day
            if ((currentTrip.getStatus() && (currentTrip.getArrivalDate().isEqual(date) || currentTrip.getDepartureDate().isEqual(date))) || (!currentTrip.getStatus() && currentTrip.getDepartureDate().isEqual(date))) {
                listTripOut.add(currentTrip); // add validated trip to the vector
            }
        }
        return listTripOut;
    }

    public double amountFuelUsedInDay(LocalDate date) {
        Vector<Trip> trips;
        trips = listAllTripInDay(date); // call method to get all the trips in the given day
        double amountFuel = 0; // initialize result variable
        if (trips.isEmpty()) {
            return 0; // return 0 if no trip is found
        } else {
            for (Trip trip : trips) {
                if (trip.getDeparturePort() == this) {
                    amountFuel += trip.getAmountFuel(); // add the trip's used fuel to result variable
                }
            }
            return amountFuel;
        }
    }

    // Get distance to other port
    public double getDistance(Port port) { // Return distance from this port to other port
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
        for (Trip trip : this.trips) { // loop through the list of trip in the port
            if (trip.getId().equals(id)) {
                return trip; // return the trip match with the given id
            }
        }
        return null;
    }

    public Vector<Trip> getTrips() {
        return trips;
    }

    public void confirmTrip(String id) {
        Trip trip = findTripById(id); // return the trip matched with the given id
        try {
            if (trip.getArrivalPort() == this && !trip.getStatus()) { // check whether the destination port is this port and the trip is incomplete
                trip.setStatus(true); // set the trip status complete
                trip.setArrivalDate(); // set the arrival date to the trip
                System.out.println("Confirm the trip successfully");
                trip.getVehicle().setPort(this); // update the current port of the vehicle
                this.getVehicles().add(trip.getVehicle()); // update the list of vehicle of the port
            } else {
                System.out.println("There are no trip to confirm"); // display this message when there are no trip with incomplete status
            }
        } catch (NullPointerException e) {
            System.out.println("The trip does not exist"); // catch exception when the system cannot found trip with matching id
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
        for (Container container : this.containers) { // loop through the list of containers in this port
            if (container.getId().equals(id)) {
                return container; // return the container with matching id
            }
        }
        return null;
    }

    public Vehicle findVehicleByID(String id) {
        for (Vehicle vehicle : this.vehicles) { // loop through the list of vehicles in this port
            if (vehicle.getID().equals(id)) {
                return vehicle; // return the vehicle with matching id
            }
        }
        return null;
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    } // add trip to this port's list of trip

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public String generateID() {
        return IDFactory.generateID("port");
    }

    // Receive and update new port's name
    public void updateName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter new port name: ");
        this.name = input.next();
        System.out.println("Update port name successfully.");
    }

    // Receive, validate, and update the storing capacity
    public void updateStoringCapacity() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter the new port storing capacity (Kg): ");
            storingCapacity = Double.parseDouble(input.nextLine());
        } catch (RuntimeException e) { // catch exception if the input is not a number
            System.out.println("You have to enter a number");
        }
    }

    // Receive, validate, and update the landing ability of the port
    public void updateLandingAbility() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the port landing ability ('true' or 'false')");
        System.out.println("If 'true' the truck can land at this port");
        String landing = input.nextLine();
        if (landing.equals("true") || landing.equals("false")) {
            landingAbility = Boolean.parseBoolean(landing);
        } else {
            System.out.println("You have to enter 'true' or 'false'"); // print error message if the input is invalid
        }
    }

    // Receive, validate, and update port manager
    public void updatePortManager() {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose attributes to update: 1. Name | 2. Password: "); // prompt the corresponding number with the action
        String chosenAttribute = input.nextLine();
        switch (chosenAttribute) { // invoke method based on the number input
            case "1":
                System.out.print("Enter the new port manager username: ");
                String username = input.nextLine();
                if (ContainerPortManagementSystem.checkUsername(username)) { // check whether the username has existed in the system
                    System.out.println("The user name has already exist.");
                } else {
                    portManager.setUsername(username);
                }
            case "2":
                System.out.print("Enter the new port manager password: ");
                portManager.setPassword(input.next());
            default:
                System.out.println("You have to choose the number associated with the attribute.");
        }

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
