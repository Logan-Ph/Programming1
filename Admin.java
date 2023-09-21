import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Vector;

public record Admin(String username, String password) implements User, Serializable {

    @Override
    public String toString() {
        return "Admin{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public void operationCase(String opCase) throws IOException {
        switch (opCase) {
            case "1" -> portOperation();
            case "2" -> createPortManager();
            case "3" -> displayWeightOfContainerType(ContainerPortManagementSystem.getContainers());
            default -> System.out.println("You have to choose the number associated with the operation");
        }
    }

    public void portOperationCase(String opCase, Port port) {
        switch (opCase) {
            case "1" -> createContainer(port);
            case "2" -> createVehicle(port);
            case "3" -> removeVehicle(port);
            case "4" -> removeContainer(port);
            case "5" -> AdminGUI.displayContainerAndVehicleInPort(port);
            case "6" -> sendVehicle(port);
//            case "7" -> ;
            case "8" -> loadContainer(port);
            case "9" -> unloadContainer(port);
            case "10" -> displayWeightOfContainerType(port.getContainers());
//            case "11" -> ;
//            case "12" -> ;
            case "13" -> listTripsBetweenDays(port);
            case "14" -> confirmTrip(port);
            default -> System.out.println("You have to choose the number associated with the operation");
        }
    }

    private void portOperation() {
        Scanner input = new Scanner(System.in);
        if (ContainerPortManagementSystem.getPorts().isEmpty()) {
            System.out.println("There are no Port in the system. You have to create the port first");
        } else {
            System.out.println("Current Port(s) in the system");
            AdminGUI.displayPort();
            System.out.print("Enter the port id associated: ");
            Port port = ContainerPortManagementSystem.findPortById(input.nextLine());
            if (port != null) {
                while (true) {
                    AdminGUI.displayPortOperation();
                    System.out.print("Enter the associated number with the operation or 'x' to exit: ");
                    String opCase = input.nextLine();
                    if (opCase.equals("x")) {
                        break;
                    } else {
                        portOperationCase(opCase, port);
                    }
                }
            } else {
                System.out.println("The port does not exist in the system");
            }
        }
    }

    public void confirmTrip(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("List of trips: ");
        AdminGUI.displayTripInPort(port);
        port.confirmTrip(input.nextLine());
    }

    public void createPortManager() throws IOException {
        Port port = PortFactory.createPort(); // create Port
        if (port != null) {
            User portManager = PortManager.create(); // create Port manager
            port.setPortManager(portManager);
            ((PortManager) portManager).setPort(port);
            ContainerPortManagementSystem.getPorts().add(port); // add Port to the system
            ContainerPortManagementSystem.getUsers().add(portManager); // add Port manager to the system
        } else {
            System.out.println("Port and port manager created unsuccessfully!");
        }
    }

    public void createContainer(Port port) {
        Container container = ContainerFactory.createContainer(port);
        if (container!= null && port.addContainer(container)){
            ContainerPortManagementSystem.getContainers().add(container);
            System.out.println("Adding container into this port successfully");
        }else {
            System.out.println("Adding container unsuccessful");
        }
    }

    public void createVehicle(Port port) {
        Vehicle vehicle = VehicleFactory.createVehicle(port);
        if (vehicle != null && port.addVehicle(vehicle)) {
            ContainerPortManagementSystem.getVehicles().add(vehicle);
            System.out.println("Adding vehicle successfully");
        } else {
            System.out.println("Adding vehicle unsuccessfully");
        }
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

    public void removeVehicle(Port port) {
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

    public void sendVehicle(Port port) {
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
                Trip trip = new Trip(vehicle, port, destinationPort, false);
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

    public static void displayWeightOfContainerType(Vector<Container> containers) {
        HashMap<String, Double> weightOfContainerType = new HashMap<>();

        double openSideTotalWeight = 0.0;
        double liquidTotalWeight = 0.0;
        double dryStorageTotalWeight = 0.0;
        double refrigeratedTotalWeight = 0.0;
        double openTopTotalWeight = 0.0;

        for (Container container : containers) {
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

    public void loadContainer(Port port) {
        // print vehicle and container in the port
        Scanner scanner = new Scanner(System.in);
        if (port.getContainers().isEmpty() || port.getVehicles().isEmpty()) {
            System.out.println("There are no container or vehicle in the port");
        } else {
            System.out.println("List of vehicle in the port:");
            AdminGUI.displayVehicleInPort(port);
            System.out.println("Enter the id of the vehicle");
            Vehicle vehicle = port.findVehicleByID(scanner.nextLine());

            System.out.println("List of container in the port:");
            AdminGUI.displayContainerInPort(port);
            System.out.println("Enter the id of the container");
            Container container = port.findContainerByID(scanner.nextLine());

            if (vehicle == null || container == null) {
                System.out.println("The vehicle or the container does not exist in the system");
            } else if (vehicle.load(container)) {
                container.setVehicle(vehicle);
                port.removeContainer(container);
                container.setPort(null);
                System.out.println("Loaded successfully");
            }
        }
    }

    public void unloadContainer(Port port) {
        Scanner scanner = new Scanner(System.in);
        if (port.getVehicles() != null){
            System.out.println("List of vehicle in the port:");
            AdminGUI.displayVehicleInPort(port);
            System.out.println("Enter vehicle ID to unload container:");
            Vehicle chosenVehicle = port.findVehicleByID(scanner.nextLine());
            if (chosenVehicle == null){
                System.out.println("The vehicle does not exist in this port");
            } else if (chosenVehicle.getContainers() == null){
                System.out.println("The vehicle does not have any container to unload!");
            }else {
                System.out.println("List of containers in the vehicle:");
                AdminGUI.displayContainerInVehicle(chosenVehicle);
                System.out.println("Enter container ID to unload:");
                Container container = chosenVehicle.unLoad(scanner.nextLine());
                if (container == null){
                    System.out.println("The container does not exist ");
                }else {
                    if (port.addContainer(container)){
                        container.setPort(port);
                        container.setVehicle(null);
                        System.out.println("Unload container successfully");
                    }
                }
            }
        }else {
            System.out.println("Unload container unsuccessfully");
        }
    }

    public static void listTripsBetweenDays(Port port) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        System.out.println("Enter the start day: ");
        LocalDate startDay = LocalDate.parse(scanner.nextLine() + " 10 2023", dtf);

        System.out.println("Enter the end day: ");
        LocalDate endDay = LocalDate.parse(scanner.nextLine() + " 10 2023", dtf);

        port.listAllTripFromDayAToB(startDay, endDay).forEach(System.out::println);
    }
}


