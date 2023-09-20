import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
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
//            case "8" -> ;
//            case "9" -> ;
//            case "10" -> ;
//            case "11" -> ;
//            case "12" -> ;
//            case "13" -> ;
//            case "14" -> ;
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

    public void createPortManager() throws IOException {
        Port port = PortFactory.createPort(); // create Port
        User portManager = PortManager.create(); // create Port manager
        PortFactory.createPortHistoryFile(port); // create Port history
        ContainerPortManagementSystem.getPorts().add(port); // add Port to the system
        ContainerPortManagementSystem.getUsers().add(portManager); // add Port manager to the system
    }

    public void createContainer(Port port) {
        Container container = ContainerFactory.createContainer(port);
        port.addContainer(container);
        ContainerPortManagementSystem.getContainers().add(container);
        System.out.println("Adding container into this port successfully");
    }

    public void createVehicle(Port port) {
        Vehicle vehicle = VehicleFactory.createVehicle(port);
        if (port.addVehicle(vehicle)) {
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
            AdminGUI.displayContainerAndVehicleInPort(port);
            System.out.print("Enter the vehicle id associated for sending: ");
            Vehicle vehicle = port.findVehicleByID(input.nextLine());
            if (vehicle == null) {
                System.out.println("The vehicle does not exist in the port");
                System.out.println("Sending vehicle unsuccessfully");
            } else if (vehicle.calculateFuelConsumption(destinationPort) < vehicle.getCurrentFuel()) {
                System.out.println("The vehicle cannot drive to the port with the current fuel capacity");
                System.out.println("Please refuel the vehicle or change to another vehicle");
            } else if (LandingBehaviour.landing(destinationPort, vehicle)) {
                Trip trip = new Trip(port, destinationPort, false);
                destinationPort.addTrip(trip);
                port.addTrip(trip);
                port.removeVehicle(vehicle);
                System.out.println("Sending vehicle successfully");
            } else if (vehicle.getContainers() == null) {
                System.out.println("The vehicle is empty. You have to load container into vehicle");
                System.out.println("Sending vehicle unsuccessfully");
            }
            else {
                System.out.println("The vehicle can not land at that port");
                System.out.println("Sending vehicle unsuccessfully");
            }
        }
    }

    public static void displayWeightOfContainerType() {
        HashMap<String, Double> weightOfContainerType = new HashMap<String, Double>();

        double openSideTotalWeight = 0.0;
        double liquidTotalWeight = 0.0;
        double dryStorageTotalWeight = 0.0;
        double refrigeratedTotalWeight = 0.0;
        double openTopTotalWeight = 0.0;

        for (Container container : ContainerPortManagementSystem.getContainers()) {
            if (container instanceof OpenSide) {
                openSideTotalWeight += container.getWeight();
                weightOfContainerType.put("Open Side",openSideTotalWeight);
            } else if (container instanceof Liquid) {
                liquidTotalWeight += container.getWeight();
                weightOfContainerType.put("Liquid",liquidTotalWeight);
            } else if (container instanceof DryStorage) {
                dryStorageTotalWeight += container.getWeight();
                weightOfContainerType.put("DryStorage",dryStorageTotalWeight);
            } else if (container instanceof Refrigerated) {
                refrigeratedTotalWeight += container.getWeight();
                weightOfContainerType.put("Refrigerated",refrigeratedTotalWeight);
            } else if (container instanceof OpenTop) {
                openTopTotalWeight += container.getWeight();
                weightOfContainerType.put("OpenTop",openTopTotalWeight);
            }
        }

        // Print the total weight for each type of container
        weightOfContainerType.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " = " + entry.getValue());
        });

    }
}
