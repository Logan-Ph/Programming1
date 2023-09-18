import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
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
            case "1" -> createContainer();
            case "2" -> createPort();
            case "3" -> createVehicle();
            default -> System.out.println("You have to choose the number in the operation");
        }
    }

    // phải try catch ở từng input cho vehicle
    public void createVehicle() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select what vehicle you want to create");
        AdminGUI.displayVehicleType();
        System.out.println(Separator.sep());
        System.out.println("Enter the number associated with vehicle type");
        String vehicleType = input.nextLine().trim();
        System.out.println("Enter the vehicle name");
        String vehicleName = input.nextLine();
        System.out.println("Enter the fuel capacity of the vehicle");
        double fuelCapacity = input.nextDouble();
        System.out.println("Enter the storing capacity of the vehicle");
        double storingCapacity = input.nextDouble();

        System.out.println(Separator.sep());
        Vehicle vehicle = VehicleFactory.createVehicle(vehicleType, vehicleName, fuelCapacity, storingCapacity); // chưa check điều kiện nếu vehicle null!!!

        Vector<Vehicle> vehicles = ContainerPortManagementSystem.getVehicles();
        vehicles.add(vehicle);
        System.out.println("Adding vehicle successfully");
    }

    public void createContainer() {
        Scanner input = new Scanner(System.in);

        System.out.println(Separator.sep());
        System.out.println("Select what Container you want to create");
        AdminGUI.displayContainerType();
        System.out.println(Separator.sep());

        System.out.println("Enter the number associated with container type");
        String containerType = input.nextLine().trim();
        System.out.println("Enter the container weight");
        double containerWeight = input.nextDouble();

        Container container = ContainerFactory.createContainer(containerType, containerWeight); // chưa check điều kiện nếu container null!!!

        Vector<Container> containers = ContainerPortManagementSystem.getContainers();
        containers.add(container);
        System.out.println(Separator.sep());
        System.out.println("Adding container successfully");
        System.out.println(Separator.sep());

    }

    public void createPort() throws IOException {
        Scanner input = new Scanner(System.in);
        Vector<User> users = ContainerPortManagementSystem.getUsers();
        Vector<Port> ports = ContainerPortManagementSystem.getPorts();
        System.out.println("When creating port, the port manager has to go along");
        System.out.println("Enter the username of port manager");
        String username = input.nextLine();
        while (ContainerPortManagementSystem.checkUsername(username)) {
            System.out.println("The username exist. Please enter a new username");
            username = input.nextLine();
        }
        System.out.println("Enter the password");
        String password = input.nextLine();

        System.out.println();
        System.out.println("Enter the port name");
        String portName = input.nextLine();

        System.out.println();
        getAllPortCoordinate();

        System.out.println();
        System.out.println("Enter the port longitude");
        double longitude = input.nextDouble();
        System.out.println("Enter the port latitude");
        double latitude = input.nextDouble();

        while (ContainerPortManagementSystem.checkCoordinatePort(latitude, longitude)) {
            System.out.println("the coordinate of port exist. Please enter again!");
            getAllPortCoordinate();

            System.out.println("Enter the port longitude");
            longitude = input.nextDouble();
            System.out.println("Enter the port latitude");
            latitude = input.nextDouble();
        }

        // try catch at this param
        System.out.println("Enter the port storing capacity");
        double storingCapacity = input.nextDouble();

        System.out.println("Enter landing ability");
        System.out.println("Choose 'true' or 'false'");
        System.out.println("if the landing ability is 'true' the truck can land at this port");
        boolean landingAbility = input.nextBoolean();

        Port port = new Port(portName, latitude, longitude, storingCapacity, landingAbility);
        User portManager = new PortManager(username, password, port);
        port.setPortManager(portManager);

        users.add(portManager);
        ports.add(port);
        createPortHistoryFile(port); // create the history file of port
    }

    private void createPortHistoryFile(Port port) throws IOException {
        File file = new File(new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("/Programming1/PortManagementSystem/PortHistory/" + port.getId() + ".txt")).getCanonicalPath());
        boolean result = file.createNewFile();
        if (result) {
            System.out.println("File create successfully " + file.getCanonicalPath());
        } else {
            System.out.println("File already exist at location: " + file.getCanonicalPath());
        }
    }

    public void getAllPortCoordinate() {
        for (Port port : ContainerPortManagementSystem.getPorts()) {
            System.out.println(port.getId() + "- " + "Latitude: " + port.getLatitude() + "- " + "Longitude: " + port.getLongitude());
        }
    }


}
