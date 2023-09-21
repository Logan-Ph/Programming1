import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class PortManager implements User, Serializable {
    private final String password;
    private final String username;
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

    @Override
    public void operationCase(String opCase){
        switch (opCase) {
            case "1" -> createContainer(port);
            case "2" -> createVehicle();
            case "3" -> removeVehicle();
            case "4" -> removeContainer(port);
            case "5" -> AdminGUI.displayContainerAndVehicleInPort(port);
            case "6" -> sendVehicle();
            case "7" -> refuelVehicle();
            case "8" -> loadContainer();
            case "9" -> unloadContainer();
            case "10" -> displayWeightOfContainerType();
            case "11" -> amountFuelUsedInDay();
            case "12" -> listTripsInDay(port);
            case "13" -> listTripsBetweenDays();
            case "14" -> confirmTrip();
            default -> System.out.println("You have to choose the number associated with the operation");
        }
    }

    public void createVehicle() {
        Vehicle vehicle = VehicleFactory.createVehicle(port);
        if (vehicle != null && port.addVehicle(vehicle)) {
            ContainerPortManagementSystem.getVehicles().add(vehicle);
            System.out.println("Adding vehicle successfully");
        } else {
            System.out.println("Adding vehicle unsuccessfully");
        }
    }

    public void removeVehicle() {
        Scanner input = new Scanner(System.in);
        System.out.println("Current Vehicle(s) in the port: " + port.getName());
        AdminGUI.displayVehicleInPort(port);
        System.out.print("Enter the vehicle id associated to remove: ");
        Vehicle vehicle = port.removeVehicle(input.nextLine());
        try {
            ContainerPortManagementSystem.getVehicles().remove(vehicle);
            if (vehicle.getContainers() != null) {
                vehicle.getContainers().forEach(container -> ContainerPortManagementSystem.getContainers().remove(container));
            }
            System.out.println("Remove vehicle successfully");
        } catch (NullPointerException e) {
            System.out.println("Remove vehicle unsuccessfully");
        }
    }

    public void sendVehicle() {
        Scanner input = new Scanner(System.in);
        System.out.println("Distance to all Port(s) in the system:");
        AdminGUI.displayPortWithDistance(port);
        System.out.print("Enter the destination port id: ");
        Port destinationPort = ContainerPortManagementSystem.findPortById(input.nextLine());

        if (destinationPort == null) {
            System.out.println("The port does not exist in the system");
            System.out.println("Sending vehicle unsuccessfully");
        } else {
            AdminGUI.displayVehicleInPort(port);
            System.out.print("Enter the vehicle id associated for sending: ");
            Vehicle vehicle = port.findVehicleByID(input.nextLine());
            if (vehicle == null) {
                System.out.println("The vehicle does not exist in the port");
                System.out.println("Sending vehicle unsuccessfully");
            } else if (vehicle.calculateFuelConsumption(destinationPort) < vehicle.getCurrentFuel()) {
                System.out.println("The vehicle cannot drive to the port with the current fuel capacity");
                System.out.println("Please refuel the vehicle or change to another vehicle");
            } else if (LandingBehaviour.landing(destinationPort, vehicle)) {
                Trip trip = new Trip(vehicle, port, destinationPort, false, vehicle.calculateFuelConsumption(destinationPort));
                destinationPort.addTrip(trip);
                port.addTrip(trip);
                port.removeVehicle(vehicle);
                System.out.println("Sending vehicle successfully");
            } else if (vehicle.getContainers() == null) {
                System.out.println("The vehicle is empty. You have to load container into vehicle");
                System.out.println("Sending vehicle unsuccessfully");
            } else {
                System.out.println("The vehicle can not land at that port");
                System.out.println("Sending vehicle unsuccessfully");
            }
        }
    }

    public void refuelVehicle(){
        Scanner input = new Scanner(System.in);
        System.out.println("Vehicles in port: ");
        AdminGUI.displayVehicleInPort(port);
        System.out.println("Enter the id for the vehicle to refuel: ");
        Vehicle vehicle = port.findVehicleByID(input.nextLine());
        if (vehicle == null){
            System.out.println("The vehicle does not exist in the port");
        }else {
            vehicle.refueling();
        }
    }

    public static User create(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the new port manager username: ");
        String username = input.nextLine();

        if (ContainerPortManagementSystem.checkUsername(username)){
            System.out.println("The user name has already exist. Please enter the username again");
            username = input.nextLine();
        }

        System.out.print("Enter the new port manager password: ");
        String password = input.nextLine();

        return new PortManager(username,password,null);
    }


    public void createContainer(Port port) {
        Container container = ContainerFactory.createContainer(port);
        port.addContainer(container);
        ContainerPortManagementSystem.getContainers().add(container);
        System.out.println("Adding container into this port successfully");
    }

    public void removeContainer(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("Current Container(s) in the port: " + port.getName());
        AdminGUI.displayContainerInPort(port);
        if (port.getContainers().isEmpty()) {
            System.out.println("There are no container in the port");
        } else {
            System.out.print("Enter the container id associated to remove: ");
            Container container = port.removeContainer(input.nextLine());
            try {
                ContainerPortManagementSystem.getContainers().remove(container);
                System.out.println("Remove container successfully");
            } catch (NullPointerException e) {
                System.out.println("Remove container unsuccessfully");
            }
        }
    }

    public void confirmTrip() {
        Scanner input = new Scanner(System.in);
        System.out.println("List of trips: ");
        AdminGUI.displayTripInPort(port);
        port.confirmTrip(input.nextLine());
    }

    public void displayWeightOfContainerType() {
        HashMap<String, Double> weightOfContainerType = new HashMap<>();

        double openSideTotalWeight = 0.0;
        double liquidTotalWeight = 0.0;
        double dryStorageTotalWeight = 0.0;
        double refrigeratedTotalWeight = 0.0;
        double openTopTotalWeight = 0.0;

        for (Container container : port.getContainers()) {
            if (container instanceof OpenSide) {
                openSideTotalWeight += container.getWeight();
                weightOfContainerType.put("Open Side", openSideTotalWeight);
            } else if (container instanceof Liquid) {
                liquidTotalWeight += container.getWeight();
                weightOfContainerType.put("Liquid", liquidTotalWeight);
            } else if (container instanceof DryStorage) {
                dryStorageTotalWeight += container.getWeight();
                weightOfContainerType.put("DryStorage", dryStorageTotalWeight);
            } else if (container instanceof Refrigerated) {
                refrigeratedTotalWeight += container.getWeight();
                weightOfContainerType.put("Refrigerated", refrigeratedTotalWeight);
            } else if (container instanceof OpenTop) {
                openTopTotalWeight += container.getWeight();
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

    public void loadContainer() {
        // print vehicle and container in the port
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of vehicle in the port:");
        for (Vehicle v : this.port.getVehicles()) {
            System.out.println(v);
        }
        System.out.println("List of container in the port:");
        for (Container c : this.port.getContainers()) {
            System.out.println(c);
        }
        boolean validInput;
        String inputID;
        Vehicle chosenVehicle;
        Container chosenContainer;
        do {
            System.out.println("Enter vehicle ID to load container:");
            inputID = scanner.nextLine();
            chosenVehicle = this.port.findVehicleByID(inputID);
            validInput = (chosenVehicle != null);
            if (!validInput) {
                System.out.println("Vehicle does not exist in this port.");
            }
        } while (!validInput);
        do {
            System.out.println("Enter container ID to load to vehicle:");
            inputID = scanner.nextLine();
            chosenContainer = this.port.findContainerByID(inputID);
            validInput = (chosenContainer != null);
            if (!validInput) {
                System.out.println("Container does not exist in this port.");
            }
        } while (!validInput);
        chosenVehicle.load(chosenContainer);
        System.out.println("Container loaded.");
    }
    public void unloadContainer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of vehicle in the port:");
        for (Vehicle v: this.port.getVehicles()) {
            System.out.println(v);
        }
        boolean validInput;
        String inputID;
        Vehicle chosenVehicle = null;
        Container chosenContainer = null;
        do {
            System.out.println("Enter vehicle ID to unload container:");
            inputID = scanner.nextLine();
            chosenVehicle = this.port.findVehicleByID(inputID);
            validInput = (chosenVehicle != null);
            if (!validInput) {
                System.out.println("Vehicle does not exist in this port.");
            }
        } while (!validInput);
        System.out.println("List of containers in the vehicle:");
        for (Container c: chosenVehicle.getContainers()) {
            System.out.println(c);
        }
        do {
            System.out.println("Enter container ID to unload:");
            inputID = scanner.nextLine();
            validInput = chosenVehicle.getContainers().contains(ContainerPortManagementSystem.findContainerById(inputID));
            if (!validInput) {
                System.out.println("Container does not exist in this vehicle.");
            }
        } while (!validInput);
        chosenVehicle.unLoad(inputID);
        System.out.println("Container unloaded.");
    }

    public void listTripsInDay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate date;
        Vector<Trip> trips;
        try {
            System.out.println("Enter the day (You need to enter only day, NOT MONTH OR YEAR): ");
            date = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getDayOfMonth() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }
        trips = port.listAllTripInDay(date);
        if (trips==null){
            System.out.println("No trips found");
        }
        else {
            trips.forEach(System.out::println);
        }
    }

    public void listTripsBetweenDays() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate startDay,endDay;
        Vector<Trip> trips;
        try {
            System.out.println("Enter the start day (You need to enter only day, NOT MONTH OR YEAR): ");
            startDay = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getDayOfMonth() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }

        try {
            System.out.println("Enter the end day (You need to enter only day, NOT MONTH OR YEAR): ");
            endDay = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getDayOfMonth() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }

        trips = port.listAllTripFromDayAToB(startDay, endDay);
        if (trips==null){
            System.out.println("No trips found");
        }
        else {
            trips.forEach(System.out::println);
        }
    }

    public void listTripsInDay(Port port) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate date;
        Vector<Trip> trips;
        try {
            System.out.println("Enter the day (You need to enter only day, NOT MONTH OR YEAR): ");
            date = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getDayOfMonth() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }
        trips = port.listAllTripInDay(date);
        if (trips==null){
            System.out.println("No trips found");
        }
        else {
            trips.forEach(System.out::println);
        }
    }

    public void amountFuelUsedInDay(){
        Scanner scanner = new Scanner(System.in);
        LocalDate date;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");

        try {
            System.out.println("Enter the day (You need to enter only day, NOT MONTH OR YEAR): ");
            date = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getDayOfMonth() + " " + LocalDate.now().getYear(), dtf);
        }catch (RuntimeException e){
            System.out.println("The day is invalid");
            return;
        }

        System.out.println("The amount of fuel used in this day: " + port.amountFuelUsedInDay(date));
    }
}
