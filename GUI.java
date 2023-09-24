import java.util.Optional;
import java.util.stream.IntStream;

public class GUI {
    public static void display() {
        System.out.println("------------------------------------------------");
        System.out.println("| COSC2081 GROUP ASSIGNMENT                    |");
        System.out.println("| CONTAINER PORT MANAGEMENT SYSTEM             |");
        System.out.println("| Instructor: Mr. Minh Vu & Dr. Phong Ngo      |");
        System.out.println("| Group: 12                                    |");
        System.out.println("| s3975979 Pham Phuoc Sang                     |");
        System.out.println("| s3978387 Cao Nguyen Hai Linh                 |");
        System.out.println("| s3978486 Nguyen Ngoc Thanh Mai               |");
        System.out.println("| s3978798 Tran Pham Khanh Doan                |");
        System.out.println("------------------------------------------------");
    }

    public static void displayOperationForPortManager() {
        System.out.println(Separator.sep());
        System.out.println("1: Add new container");
        System.out.println("2: Remove container");
        System.out.println("3: Display all vehicle and container");
        System.out.println("4: Send vehicle");
        System.out.println("5: Refuel vehicle");
        System.out.println("6: Load container");
        System.out.println("7: Unload container");
        System.out.println("8: Display weight of each type of container");
        System.out.println("9: Display how much fuel used in a day");
        System.out.println("10: Display all trip in a day");
        System.out.println("11: Display all trip between 2 days");
        System.out.println("12: Confirm trip");
        System.out.println("13: Update container weight");
        System.out.println(Separator.sep());
    }

    // display the operation
    public static void displayOperation() {
        System.out.println(Separator.sep());
        System.out.println("Select the operation");
        System.out.println("1: Operation on Port");
        System.out.println("2: Add new port manager and port");
        System.out.println("3: Display total weight of container types");
        System.out.println("4: Update account");
        System.out.println(Separator.sep());
    }

    // display all the port in the system
    public static void displayPort() {
        IntStream.range(0, ContainerPortManagementSystem.getPorts().size())
                .forEach(i -> System.out.println("Port " + i + ": " + ContainerPortManagementSystem.getPorts().get(i)));
    }

    // display all the port with distance
    public static void displayPortWithDistance(Port port) {
        ContainerPortManagementSystem.getPorts().stream().filter(p -> p != port).forEach(p -> System.out.println("    " + p.getName() + " - " + p.getId() + ": " + port.getDistance(p) + "km"));
    }

    // display container in the vehicle
    public static void displayContainerInVehicle(Vehicle vehicle) {
        vehicle.getContainers().forEach(System.out::println);
    }

    // display container in port
    public static void displayContainerInPort(Port port) {
        System.out.println(Separator.sep());
        port.getContainers().forEach(System.out::println);
        System.out.println(Separator.sep());
    }

    // display the vehicle in the port
    public static void displayVehicleInPort(Port port) {
        System.out.println(Separator.sep());
        port.getVehicles().forEach(System.out::println);
        System.out.println(Separator.sep());
    }

    // display all the container and vehicle in the port
    public static void displayContainerAndVehicleInPort(Port port) {
        System.out.println(Separator.sep());
        System.out.println("Unloaded container:");
        displayContainerInPort(port);

        System.out.println("Loaded container:");
        port.getVehicles()
        .forEach(vehicle -> {
        System.out.println("Vehicle: ");
        System.out.println(vehicle);
        Optional.ofNullable(
                vehicle.getContainers()).ifPresent(
                        containers -> containers.forEach(
                                container -> System.out.println(" " + container)));
        });
    }

    // display all the trip in the port
    public static void displayTripInPort(Port port) {
        port.getTrips().stream().filter(trip -> trip.getArrivalPort() == port && !trip.getStatus()).forEach(System.out::println);
    }

    // display all the vehicle type
    public static void displayVehicleType() {
        System.out.println(Separator.sep());
        System.out.println("1: Basic Truck");
        System.out.println("2: Reefer Truck");
        System.out.println("3: Tanker Truck");
        System.out.println("4: Ship");
        System.out.println(Separator.sep());
    }

    // display all the port operation
    public static void displayPortOperation(Port port) {
        System.out.println(Separator.sep());
        System.out.println("You are at the port: " + port.getName());
        System.out.println("1: Add new container");
        System.out.println("2: Add new vehicle");
        System.out.println("3: Remove vehicle ");
        System.out.println("4: Remove container");
        System.out.println("5: Display all vehicle and container");
        System.out.println("6: Send vehicle");
        System.out.println("7: Refuel vehicle");
        System.out.println("8: Load container");
        System.out.println("9: Unload container");
        System.out.println("10: Display weight of each type of container");
        System.out.println("11: Display how much fuel used in a day");
        System.out.println("12: Display all trip in a day");
        System.out.println("13: Display all trip between 2 days");
        System.out.println("14: Confirm trip");
        System.out.println("15: Remove port manager and port");
        System.out.println("16: Update port and port manager");
        System.out.println("17: Update container weight");
        System.out.println("18: Update vehicle");
        System.out.println(Separator.sep());
    }

    // display all the container type
    public static void displayContainerType() {
        System.out.println(Separator.sep());
        System.out.println("1: Dry Storage");
        System.out.println("2: Liquid");
        System.out.println("3: Open Top");
        System.out.println("4: Open Side");
        System.out.println("5: Refrigerated");
        System.out.println(Separator.sep());
    }

}
