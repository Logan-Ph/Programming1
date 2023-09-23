import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class PortManager implements User, Serializable {
    private String password;
    private String username;
    private Port port;

    public PortManager(String username, String password, Port port) {
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public String password() {
        return password;
    }

    public String username() {
        return username;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }


    @Override
    public String toString() {
        return "PortManager{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    // Method to invoke other methods based on the number input
    @Override
    public void operationCase(String opCase){
        switch (opCase) {
            case "1" -> createContainer();
            case "2" -> removeContainer();
            case "3" -> GUI.displayContainerAndVehicleInPort(port);
            case "4" -> sendVehicle();
            case "5" -> refuelVehicle();
            case "6" -> loadContainer();
            case "7" -> unloadContainer();
            case "8" -> displayWeightOfContainerType();
            case "9" -> amountFuelUsedInDay();
            case "10" -> listTripsInDay();
            case "11" -> listTripsBetweenDays();
            case "12" -> confirmTrip();
            case "13" -> updateContainerWeight();
            default -> System.out.println("You have to choose the number associated with the operation");
        }
    }

    // Create and validate new port manager account
    public static User create(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the new port manager username: ");
        String username = input.nextLine();
        // Call static method to check whether username has existed in the system
        if (ContainerPortManagementSystem.checkUsername(username)){
            System.out.println("The user name has already exist. Please enter the username again");
            username = input.nextLine();
        }

        System.out.print("Enter the new port manager password: ");
        String password = input.nextLine();

        return new PortManager(username,password,null);
    }

    public void setPassword(String password) {this.password = password;}
    public void setUsername(String username) {this.username = username;}

    // Add new container to the port
    public void createContainer() {
        Container container = ContainerFactory.createContainer(port);
        if (container!= null && port.addContainer(container)){
            ContainerPortManagementSystem.getContainers().add(container);
            System.out.println("Adding container into this port successfully");
        }else {
            System.out.println("Adding container unsuccessful");
        }
    }

    // Remove container from the port
    public void removeContainer() {
        Scanner input = new Scanner(System.in);
        System.out.println("Current Container(s) in the port: " + port.getName());
        GUI.displayContainerInPort(port);
        if (port.getContainers().isEmpty()) {
            System.out.println("There are no container in the port");
        } else {
            System.out.print("Enter the container id associated to remove: ");
            Container container = port.removeContainer(input.nextLine());
            try {
                if (container == null){
                    return;
                }
                ContainerPortManagementSystem.getContainers().remove(container);
                System.out.println("Remove container successfully");
            } catch (NullPointerException | AssertionError e) {
                System.out.println("Remove container unsuccessfully");
            }
        }
    }

    // Load container onto the vehicle
    public void loadContainer() {
        // print vehicle and container in the port
        Scanner scanner = new Scanner(System.in);
        // check whether the port has unloaded container
        if (port.getContainers().isEmpty() || port.getVehicles().isEmpty())  {
            System.out.println("There are no container or vehicle in the port");
        } else {
            try{
                System.out.println("List of vehicle in the port:");
                GUI.displayVehicleInPort(port);
                System.out.println("Enter the id of the vehicle");
                Vehicle vehicle = port.findVehicleByID(scanner.nextLine());

                System.out.println("List of container in the port:");
                GUI.displayContainerInPort(port);
                System.out.println("Enter the id of the container");
                Container container = port.findContainerByID(scanner.nextLine());

                if (vehicle.load(container)) {
                    container.setVehicle(vehicle);
                    port.removeContainer(container);
                    container.setPort(null);
                    System.out.println("Loaded successfully");
                }
            }catch (NullPointerException e){
                // catch exception if the user entered the wrong id
                System.out.println("The vehicle or the container does not exist in the system");
                System.out.println("Loaded unsuccessfully");
            }
        }
    }

    // Unload container from the vehicle
    public void unloadContainer() {
        Scanner scanner = new Scanner(System.in);
        if (!port.getVehicles().isEmpty()){
            try {
                System.out.println("List of vehicle in the port: ");
                GUI.displayVehicleInPort(port);
                // Prompt vehicle ID
                System.out.println("Enter the vehicle ID to unload container: ");
                Vehicle chosenVehicle = port.findVehicleByID(scanner.nextLine());
                if (chosenVehicle.getContainers().isEmpty()) {
                    System.out.println("The vehicle does not have any container to unload!");
                } else {
                    System.out.println("List of containers in the vehicle:");
                    GUI.displayContainerInVehicle(chosenVehicle);
                    // Prompt container ID
                    System.out.println("Enter container ID to unload:");
                    Container container = chosenVehicle.unLoad(scanner.nextLine());
                    if (port.addContainer(container)) {
                        container.setPort(port);
                        container.setVehicle(null);
                        System.out.println("Unload container successfully");
                    }
                }
            }catch (NullPointerException e){
                // Catch exception when the user either enter the wrong vehicle ID or container ID
                System.out.println("The container or the the vehicle does not exist in this port");
                System.out.println("Unload container unsuccessfully");
            }
        } else {
            System.out.println("There are no vehicle in this port.");
        }
    }

    // Refuel vehicle at the port
    public void refuelVehicle(){
        Scanner input = new Scanner(System.in);
        System.out.println("Vehicles in port: ");
        GUI.displayVehicleInPort(port);
        System.out.println("Enter the id for the vehicle to refuel: ");
        Vehicle vehicle = port.findVehicleByID(input.nextLine());
        if (vehicle == null){
            // Validate input, variable vehicle is null when system cannot find the vehicle with the input id in the port
            System.out.println("The vehicle does not exist in the port");
        }else {
            if (vehicle.refueling()){
                System.out.println("Refuelling the vehicle successfully");
            }
        }
    }

    // Confirm the arrival of vehicle sent from another port
    public void confirmTrip() {
        Scanner input = new Scanner(System.in);
        // Print lists of unconfirmed trip
        System.out.println("List of trips: ");
        GUI.displayTripInPort(port);
        // Prompt trip id
        System.out.println("Enter the trip id associated to confirm");
        port.confirmTrip(input.nextLine());
    }

    // Send vehicle to another port
    public void sendVehicle() {
        Scanner input = new Scanner(System.in);
        // Display distance to other ports
        System.out.println("Distance to all Port(s) in the system:");
        GUI.displayPortWithDistance(port);
        // Prompt destination port id
        System.out.print("Enter the destination port id: ");
        Port destinationPort = ContainerPortManagementSystem.findPortById(input.nextLine());
        try {
            GUI.displayVehicleInPort(port);
        } catch (NullPointerException e) {
            // Catch exception when the input id is invalid
            System.out.println("The port does not exist");
            System.out.println("Sending vehicle unsuccessfully");
            return;
        }
        // Prompt vehicle id
        System.out.print("Enter the vehicle id associated for sending: ");
        Vehicle vehicle = port.findVehicleByID(input.nextLine());
        try {
            if (vehicle.calculateFuelConsumption(destinationPort) > vehicle.getCurrentFuel()){
                System.out.println("The vehicle cannot drive to the port with the current fuel capacity");
                System.out.println("Please refuel the vehicle or change to another vehicle");
            } else if (LandingBehaviour.landing(destinationPort, vehicle)) {
                Trip trip = new Trip(vehicle, port, destinationPort, false, vehicle.calculateFuelConsumption(destinationPort));
                assert destinationPort != null;
                destinationPort.addTrip(trip);
                port.addTrip(trip);
                port.removeVehicle(vehicle);
                System.out.println("Sending vehicle successfully");
            }
        }catch (NullPointerException e){
            // Catch exception when the input id is invalid
            System.out.println("The vehicle does not exist in this port");
            System.out.println("Sending vehicle unsuccessfully");
        }
    }

    // Display the total weight of each type of container
    public void displayWeightOfContainerType() {
        HashMap<String, Double> weightOfContainerType = new HashMap<>();

        double openSideTotalWeight = 0.0;
        double liquidTotalWeight = 0.0;
        double dryStorageTotalWeight = 0.0;
        double refrigeratedTotalWeight = 0.0;
        double openTopTotalWeight = 0.0;

        // Loop through list of containers and categorize them into types
        for (Container container : port.getContainers()) {
            if (container instanceof OpenSide) {
                openSideTotalWeight += container.getWEIGHT();
                weightOfContainerType.put("Open Side", openSideTotalWeight);
            } else if (container instanceof Liquid) {
                liquidTotalWeight += container.getWEIGHT();
                weightOfContainerType.put("Liquid", liquidTotalWeight);
            } else if (container instanceof DryStorage) {
                dryStorageTotalWeight += container.getWEIGHT();
                weightOfContainerType.put("DryStorage", dryStorageTotalWeight);
            } else if (container instanceof Refrigerated) {
                refrigeratedTotalWeight += container.getWEIGHT();
                weightOfContainerType.put("Refrigerated", refrigeratedTotalWeight);
            } else if (container instanceof OpenTop) {
                openTopTotalWeight += container.getWEIGHT();
                weightOfContainerType.put("OpenTop", openTopTotalWeight);
            }
        }
        if (!weightOfContainerType.isEmpty()) {
            // Print the total weight for each type of container
            weightOfContainerType.forEach((key, value) -> System.out.println(key + " = " + value));
        } else {
            System.out.println("There are no Containers to display!");
        }
    }

    // List trips between two given days
    public void listTripsBetweenDays() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd M yyyy");
        LocalDate startDay,endDay;
        Vector<Trip> trips;
        // Validate day input
        try {
            System.out.println("Enter the start day (You need to enter only day, NOT MONTH OR YEAR): ");
            startDay = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }

        try {
            System.out.println("Enter the end day (You need to enter only day, NOT MONTH OR YEAR): ");
            endDay = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }
        // Display all trips with either departure day and arrival day between 2 input day
        trips = port.listAllTripFromDayAToB(startDay, endDay);
        if (trips==null){
            System.out.println("No trips found");
        }
        else {
            trips.forEach(System.out::println);
        }
    }

    // List all trips in a given day
    public void listTripsInDay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd M yyyy");
        LocalDate date;
        Vector<Trip> trips;
        // Validate input
        try {
            System.out.println("Enter the day (You need to enter only day, NOT MONTH OR YEAR): ");
            date = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }
        // List trips
        trips = port.listAllTripInDay(date);
        if (trips==null){
            // Print message if there are no trip in the given day at this port
            System.out.println("No trips found");
        }
        else {
            trips.forEach(System.out::println);
        }
    }

    // Display the amount of fuel used in a given day
    public void amountFuelUsedInDay(){
        Scanner scanner = new Scanner(System.in);
        LocalDate date;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd M yyyy");
        // Validate day input
        try {
            System.out.println("Enter the day (You need to enter only day, NOT MONTH OR YEAR): ");
            date = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }
        System.out.println("The amount of fuel used in this day: " + port.amountFuelUsedInDay(date));
    }

    // Update a container's weight
    public void updateContainerWeight() {
        Scanner input = new Scanner(System.in);
        // Print existing container in the port
        System.out.println("Current Container(s) in the port: " + port.getName());
        GUI.displayContainerInPort(port);
        if (port.getContainers().isEmpty()) {
            // Print message if no container is found
            System.out.println("There are no container in the port");
        } else {
            // Prompt container ID
            System.out.print("Enter the container id associated to update: ");
            Container container = port.findContainerByID(input.nextLine());
            if (container == null){
                System.out.println("Container does not exist in this port.");
            } else {
                container.updateWeight();
            }
        }
    }
}
