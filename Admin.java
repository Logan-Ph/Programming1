
import java.io.Serializable;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Vector;

public class Admin implements User, Serializable {
    private String password;
    private String username;

    public Admin(String username, String password) {
        this.password = password;
        this.username = username;
    }



    @Override
    public String password() {
        return password;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public void operationCase(String opCase) {
        switch (opCase) {
            case "1" -> portOperation();
            case "2" -> createPortAndPortManager();
            case "3" -> displayWeightOfContainerType(ContainerPortManagementSystem.getContainers());
            case "4" -> updateAdmin();
            default -> System.out.println("You have to choose the number associated with the operation");
        }
    }

    public void portOperationCase(String opCase, Port port) {
        // match the operation case to each of the function
        // the order is the same in the GUI class 'displayPortOperation' method
        switch (opCase) {
            case "1" -> createContainer(port);
            case "2" -> createVehicle(port);
            case "3" -> removeVehicle(port);
            case "4" -> removeContainer(port);
            case "5" -> GUI.displayContainerAndVehicleInPort(port);
            case "6" -> sendVehicle(port);
            case "7" -> refuelVehicle(port);
            case "8" -> loadContainer(port);
            case "9" -> unloadContainer(port);
            case "10" -> displayWeightOfContainerType(port.getContainers());
            case "11" -> amountFuelUsedInDay(port);
            case "12" -> listTripsInDay(port);
            case "13" -> listTripsBetweenDays(port);
            case "14" -> confirmTrip(port);
            case "15" -> removePortAndPortManager(port);
            case "16" -> updatePort(port);
            case "17" -> updateContainerWeight(port);
            case "18" -> updateVehicle(port);
            default -> System.out.println("You have to choose the number associated with the operation");
        }
    }

    public static User create() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the new admin username: ");
        String username = input.nextLine().strip(); // get the username and remove all the space

        if (ContainerPortManagementSystem.checkUsername(username)){ // check if the username existed in the system
            System.out.println("The user name has already exist. Please enter the username again");
            username = input.nextLine().strip(); // get the username again
        }

        System.out.print("Enter the new admin password: ");
        String password = input.nextLine().strip(); // get the password

        return new Admin(username,password);
    }
    @Override
    public void setUsername(String username) {this.username = username;} // set the username

    @Override
    public void setPassword(String password) {this.password = password;} // set the password

    public void updateAdmin() {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose attributes to update: 1. Username | 2. Password: ");
        String chosenAttribute = input.nextLine(); // get the decision from the user
        switch (chosenAttribute) {
            case "1" -> {
                System.out.print("Enter the new admin username: ");
                String username = input.nextLine().strip(); // get the new username
                if (ContainerPortManagementSystem.checkUsername(username)) { // check if the username exist in the system
                    System.out.println("The user name has already exist.");
                    System.out.println("Update username unsuccessfully");
                } else {
                    this.setUsername(username); // set the username
                    System.out.println("Update username successfully");

                }
            }
            case "2" -> {
                System.out.print("Enter the new admin password: ");
                this.setPassword(input.next().strip()); // get the new password
                System.out.println("Update password successfully");
            }
            default -> System.out.println("You have to choose the number associated with the attribute.");
        }

    }

    private void portOperation() {
        Scanner input = new Scanner(System.in);
        if (ContainerPortManagementSystem.getPorts().isEmpty()) {
            System.out.println("There are no Port in the system. You have to create the port first");
        } else {
            System.out.println("Current Port(s) in the system");
            GUI.displayPort(); // display all the port in the system
            System.out.print("Enter the port id associated: ");
            Port port = ContainerPortManagementSystem.findPortById(input.nextLine()); // find the port based on the id in the system
            if (port != null) {
                while (true) {
                    GUI.displayPortOperation(port); // display all the operation on the port
                    System.out.print("Enter the associated number with the operation or 'x' to exit: ");
                    String opCase = input.nextLine();
                    if (opCase.equals("x")) {
                        break;
                    } else {
                        portOperationCase(opCase, port);
                        if (opCase.equals("15")) {
                            return;
                        }
                    }
                }
            } else {
                System.out.println("The port does not exist in the system");
            }
        }
    }

    public void updatePort(Port port) {
        System.out.print("Choose attributes to update: 1. Name | 2. Storing Capacity | 3. Landing Ability | 4. Port Manager.");
        Scanner input = new Scanner(System.in);
        String chosenAttribute = input.nextLine(); // get the decision from user
        switch (chosenAttribute) {
            case "1" -> port.updateName();
            case "2" -> port.updateStoringCapacity();
            case "3" -> port.updateLandingAbility();
            case "4" -> port.updatePortManager();
            default -> System.out.println("You have to choose the number associated with the attribute.");
        }
    }

    public void updateContainerWeight(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("Current Container(s) in the port: " + port.getName());
        GUI.displayContainerInPort(port); // display all the container in the port
        if (port.getContainers().isEmpty()) { // check if there is any container in the port
            System.out.println("There are no container in the port");
        } else {
            System.out.print("Enter the container id associated to update: ");
            Container container = port.findContainerByID(input.nextLine()); // find the container by ID in the port
            if (container == null){
                System.out.println("Container does not exist in this port.");
            } else {
                container.updateWeight(); // update the weight in the container
            }
        }
    }

    public void updateVehicle(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("Vehicles in port: ");
        GUI.displayVehicleInPort(port); // display all the vehicle in the port
        if (port.getVehicles().isEmpty()) { // check if there is any vehicles in the port
            System.out.println("There are no vehicle in this port.");
        } else {
            System.out.println("Enter the id for the vehicle to update: ");
            Vehicle vehicle = port.findVehicleByID(input.nextLine()); // find the vehicle by ID in the port
            if (vehicle == null){
                System.out.println("Container does not exist in this port.");
            } else {
                vehicle.setName();
            }
        }
    }

    public void refuelVehicle(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("Vehicles in port: ");
        GUI.displayVehicleInPort(port); // display all the vehicle in the port
        System.out.println("Enter the id for the vehicle to refuel: ");
        Vehicle vehicle = port.findVehicleByID(input.nextLine()); // find the vehicle in the port
        if (vehicle == null) {
            System.out.println("The vehicle does not exist in the port");
        } else {
            if (vehicle.refueling()) { // check if the vehicle can refuel
                System.out.println("Refuelling the vehicle successfully");
            }
        }
    }

    public void confirmTrip(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("List of trips: ");
        GUI.displayTripInPort(port); // display all the trip in the port
        System.out.println("Enter the trip id associated to confirm");
        port.confirmTrip(input.nextLine()); // call the method of the port
    }

    public void createPortAndPortManager() {
        Port port = PortFactory.createPort(); // create Port
        if (port != null) {
            User portManager = PortManager.create(); // create Port manager
            port.setPortManager(portManager);
            ((PortManager) portManager).setPort(port);
            ContainerPortManagementSystem.getPorts().add(port); // add Port to the system
            ContainerPortManagementSystem.getUsers().add(portManager); // add Port manager to the system
            System.out.println("Port and port manager created successfully!");
        } else {
            System.out.println("Port and port manager created unsuccessfully!");
        }
    }

    public void createContainer(Port port) {
        Container container = ContainerFactory.createContainer(port);
        if (container != null && port.addContainer(container)) { // check if the container is not nul and can add the container in the port
            ContainerPortManagementSystem.getContainers().add(container); // add the container in the system
            System.out.println("Adding container into this port successfully");
        } else {
            System.out.println("Adding container unsuccessful");
        }
    }

    public void createVehicle(Port port) {
        Vehicle vehicle = VehicleFactory.createVehicle(port); // create the vehicle
        if (vehicle != null && port.addVehicle(vehicle)) { // check if the vehicle is not null and the port can add the vehicle
            ContainerPortManagementSystem.getVehicles().add(vehicle); // add the vehicle in the system
            System.out.println("Adding vehicle successfully");
        } else {
            System.out.println("Adding vehicle unsuccessfully");
        }
    }

    public void removeContainer(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("Current Container(s) in the port: " + port.getName());
        GUI.displayContainerInPort(port); // display all the container in the port
        if (port.getContainers().isEmpty()) { // check if there is any container in the port
            System.out.println("There are no container in the port");
        } else {
            System.out.print("Enter the container id associated to remove: ");
            Container container = port.removeContainer(input.nextLine()); // remove the container in the port
            try {
                if (container == null){
                    System.out.println("The container does not exist.");
                    return;
                }
                ContainerPortManagementSystem.getContainers().remove(container); // remove the container from the system
                System.out.println("Remove container successfully");
            } catch (NullPointerException | AssertionError e) {
                System.out.println("Remove container unsuccessfully");
            }
        }
    }

    public void removeVehicle(Port port) {
        if (port.getVehicles() == null){
            System.out.println("There are no vehicle in the port");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Current Vehicle(s) in the port: " + port.getName());
        GUI.displayVehicleInPort(port); // display all the vehicle in the port
        System.out.print("Enter the vehicle id associated to remove: ");
        try {
            Vehicle vehicle = port.removeVehicle(input.nextLine()); // remove the vehicle from the port
            if (vehicle == null){
                System.out.println("The vehicle does not exist.");
                return;
            }
            ContainerPortManagementSystem.getVehicles().remove(vehicle); // remove the vehicle from the system
            if (vehicle.getContainers() != null) {
                vehicle.getContainers().forEach(container -> ContainerPortManagementSystem.getContainers().remove(container)); // remove all the container in the vehicle from the system
            }
            System.out.println("Remove vehicle successfully");
        } catch (NullPointerException | AssertionError e) {
            System.out.println("Remove vehicle unsuccessfully");
        }
    }

    public void sendVehicle(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("Distance to all Port(s) in the system:");
        GUI.displayPortWithDistance(port); // display all the distance from this port
        System.out.print("Enter the destination port id: ");
        Port destinationPort = ContainerPortManagementSystem.findPortById(input.nextLine());  // find the port by ID
        try {
            GUI.displayVehicleInPort(port);
        } catch (NullPointerException e) {
            System.out.println("The port does not exist");
            System.out.println("Sending vehicle unsuccessfully");
            return;
        }
        System.out.print("Enter the vehicle id associated for sending: ");
        Vehicle vehicle = port.findVehicleByID(input.nextLine()); // find the vehicle in the port by ID
        try {
              if (vehicle.calculateFuelConsumption(destinationPort) > vehicle.getCurrentFuel()){
                  System.out.println("The vehicle cannot drive to the port with the current fuel capacity");
                  System.out.println("Please refuel the vehicle or change to another vehicle");
              } else if (LandingBehaviour.landing(destinationPort, vehicle)) {
                  Trip trip = new Trip(vehicle, port, destinationPort, false, vehicle.calculateFuelConsumption(destinationPort)); // create the trip
                  assert destinationPort != null;
                  destinationPort.addTrip(trip); // add the trip to the destination port
                  port.addTrip(trip); // add the trip to this port
                  port.removeVehicle(vehicle); // remove the vehicle from this port
                  System.out.println("Sending vehicle successfully");
              }
        }catch (NullPointerException e){
            System.out.println("The vehicle does not exist in this port");
            System.out.println("Sending vehicle unsuccessfully");
        }
    }

    public static void displayWeightOfContainerType(Vector<Container> containers) {
        HashMap<String, Double> weightOfContainerType = new HashMap<>();

        for (Container container : containers) {
            if (container instanceof OpenSide) {
                weightOfContainerType.merge("Open Side", container.getWEIGHT(), Double::sum); // if the container does not exist in the hashmap, put new container weight or sum up to the current value
            } else if (container instanceof Liquid) {
                weightOfContainerType.merge("Liquid", container.getWEIGHT(), Double::sum);
            } else if (container instanceof DryStorage) {
                weightOfContainerType.merge("DryStorage", container.getWEIGHT(), Double::sum);
            } else if (container instanceof Refrigerated) {
                weightOfContainerType.merge("Refrigerated", container.getWEIGHT(), Double::sum);
            } else if (container instanceof OpenTop) {
                weightOfContainerType.merge("OpenTop", container.getWEIGHT(), Double::sum);
            }
        }
        if (!weightOfContainerType.isEmpty()) {
            // Print the total weight for each type of container
            weightOfContainerType.forEach((key, value) -> System.out.println(key + " = " + value + " KG"));
        } else {
            System.out.println("There are no Containers to display!");
        }
    }

    public void loadContainer(Port port) {
        // print vehicle and container in the port
        Scanner scanner = new Scanner(System.in);
        if (port.getContainers().isEmpty() || port.getVehicles().isEmpty()) { // check if the port does not have any container or vehicle
            System.out.println("There are no container or vehicle in the port");
        } else {
            try{
                System.out.println("List of vehicle in the port:");
                GUI.displayVehicleInPort(port);
                System.out.println("Enter the id of the vehicle");
                Vehicle vehicle = port.findVehicleByID(scanner.nextLine()); // find the vehicle by ID in the port

                System.out.println("List of container in the port:");
                GUI.displayContainerInPort(port);
                System.out.println("Enter the id of the container");
                Container container = port.findContainerByID(scanner.nextLine()); // find the container by ID in the port

                if (vehicle.load(container)) {
                    container.setVehicle(vehicle); // set the vehicle of the container
                    port.removeContainer(container); // remove the container from the port
                    container.setPort(null); // set the port of the container
                    System.out.println("Loaded successfully");
                }
            }catch (NullPointerException e){
                System.out.println("The vehicle or the container does not exist in the system");
                System.out.println("Loaded unsuccessfully");
            }
        }
    }

    public void unloadContainer(Port port) {
        Scanner scanner = new Scanner(System.in);
        if (!port.getVehicles().isEmpty()){ // check if the port have any vehicles
            try {
                System.out.println("List of vehicle in the port: ");
                GUI.displayVehicleInPort(port); // display the vehicle in the port
                System.out.println("Enter the vehicle ID to unload container: ");
                Vehicle chosenVehicle = port.findVehicleByID(scanner.nextLine()); // find the vehicle in the port
                if (chosenVehicle.getContainers().isEmpty()) { // check if the containers in the vehicle exist
                    System.out.println("The vehicle does not have any container to unload!");
                } else {
                    System.out.println("List of containers in the vehicle:");
                    GUI.displayContainerInVehicle(chosenVehicle); // display the container in the vehicle
                    System.out.println("Enter container ID to unload:");
                    Container container = chosenVehicle.unLoad(scanner.nextLine());
                    if (port.addContainer(container)) { // check if the port can add the container
                        container.setPort(port); // set port for the container
                        container.setVehicle(null);  // set vehicle for the container
                        System.out.println("Unload container successfully");
                    }
                }
            }catch (NullPointerException e){
                System.out.println("The container or the the vehicle does not exist in this port");
                System.out.println("Unload container unsuccessfully");
            }
        } else {
            System.out.println("There are no vehicle in this port.");
        }
    }

    public void listTripsBetweenDays(Port port) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd M yyyy");
        LocalDate startDay, endDay;
        Vector<Trip> trips;
        try {
            System.out.println("Enter the start day (You need to enter only day, NOT MONTH OR YEAR): ");
            startDay = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf); // parse the startDay to the LocalDate data type
        } catch (RuntimeException e) {
            System.out.println("The day is invalid");
            return;
        }

        try {
            System.out.println("Enter the end day (You need to enter only day, NOT MONTH OR YEAR): ");
            endDay = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf); // parse the endDay to the LocalDate data type
        } catch (RuntimeException e) {
            System.out.println("The day is invalid");
            return;
        }
        trips = port.listAllTripFromDayAToB(startDay, endDay); // get all the trips from the port
        if (trips == null) {
            System.out.println("No trips found");
        } else {
            trips.forEach(System.out::println);
        }
    }

    public void listTripsInDay(Port port) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd M yyyy");
        LocalDate date;
        Vector<Trip> trips;
        try {
            System.out.println("Enter the day (You need to enter only day, NOT MONTH OR YEAR): ");
            date = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf);  // parse the Day to the LocalDate data type
        } catch (RuntimeException e) {
            System.out.println("The day is invalid");
            return;
        }
        trips = port.listAllTripInDay(date); // get trips from the port
        if (trips == null) {
            System.out.println("No trips found");
        } else {
            trips.forEach(System.out::println);
        }
    }

    public void amountFuelUsedInDay(Port port) {
        Scanner scanner = new Scanner(System.in);
        LocalDate date;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd M yyyy");
        try {
            System.out.println("Enter the day (You need to enter only day, NOT MONTH OR YEAR): ");
            date = LocalDate.parse(scanner.nextLine() + " " + LocalDate.now().getMonthValue() + " " + LocalDate.now().getYear(), dtf);  // parse the Day to the LocalDate data type
        } catch (RuntimeException e) {
            System.out.println("The day is invalid");
            return;
        }
        System.out.println("The amount of fuel used in this day: " + port.amountFuelUsedInDay(date)); // get the amount fuel used in the defined day
    }

    public void removePortAndPortManager(Port port) {
        Scanner input = new Scanner(System.in);
        if (!port.checkTrip()) {
            System.out.println("Are you sure you want to delete this Port ?");
            System.out.println("Enter 'x' to delete or else to exit");
            String confirm = input.nextLine();
            if (confirm.equals("x")) {
                ContainerPortManagementSystem.getPorts().remove(port);
                Vector<Trip> trips = port.getTrips(); // get trips from the port
                for (Trip trip : trips) {
                    if (trip.getArrivalPort() != port) {
                        trip.getArrivalPort().getTrips().remove(trip); // remove trip from the port
                    } else {
                        trip.getDeparturePort().getTrips().remove(trip); // remove trip form the port
                    }
                }
                port.getContainers().forEach( container -> ContainerPortManagementSystem.getContainers().remove(container));
                port.getVehicles().forEach( vehicle -> ContainerPortManagementSystem.getVehicles().remove(vehicle));
                ContainerPortManagementSystem.getUsers().remove(port.getUser());
                System.out.println("Remove port and port manager successfully");
            }
        } else {
            System.out.println("You cannot delete this port. There are more vehicles are coming to this port, you need to confirm first");
        }
    }
}


