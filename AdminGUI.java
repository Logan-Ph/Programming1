public class AdminGUI {

    public static void display() {
        System.out.println("------------------------------------------------");
        System.out.println("| COSC2081 GROUP ASSIGNMENT                    |");
        System.out.println("| CONTAINER PORT MANAGEMENT SYSTEM             |");
        System.out.println("| Instructor: Mr. Minh Vu & Dr. Phong Ngo      |");
        System.out.println("| Group: Group Name                            |");
        System.out.println("| s3975979 Pham Phuoc Sang                     |");
        System.out.println("| s3978387 Cao Nguyen Hai Linh                 |");
        System.out.println("| s3978486 Nguyen Ngoc Thanh Mai               |");
        System.out.println("| s3978798 Tran Pham Khanh Doan                |");
        System.out.println("------------------------------------------------");

    }

    public static void displayOperation() {
        System.out.println("Select the operation");
        System.out.println("1: Operation on Port");
        System.out.println("2: Add new port manager and port");
        System.out.println(Separator.sep());
    }

    public static void displayPort() {
        int i = 1;
        for (Port port : ContainerPortManagementSystem.getPorts()) {
            System.out.println("Port " + i++ + ": " + port);
        }
    }

    public static void displayPortWithDistance(Port port) {
        for (Port p : ContainerPortManagementSystem.getPorts()) {
            if (p != port){
                System.out.println("    " + p.getName() + " - "+ p.getId() +": " + port.getDistance(p) + "km");
            }
        }
    }

    public static void displayContainerInVehicle(Vehicle vehicle){
        vehicle.getContainers().forEach(System.out::println);
    }

    public static void displayContainerInPort(Port port) {
        for (Container container : port.getContainers()) {
            System.out.println(container);
        }
        System.out.println(Separator.sep());

    }

    public static void displayVehicleInPort(Port port) {
        for (Vehicle vehicle : port.getVehicles()) {
            System.out.println(vehicle);
        }
        System.out.println(Separator.sep());
    }

    public static void displayContainerAndVehicleInPort(Port port){
        System.out.println("Unloaded container:");
        displayContainerInPort(port);

        System.out.println("Loaded container:");
        for (Vehicle vehicle : port.getVehicles()) {
            if (vehicle == null){
                continue;
            }
            System.out.println(vehicle);
            if (vehicle.getContainers() == null){
                continue;
            }
            for (Container container: vehicle.getContainers()){
                System.out.println("    "+container);
            }
        }
    }

    public static void displayTripInPort(Port port) {
        for(Trip trip: port.getTrips()) {
            if (trip.getArrivalPort() == port && !trip.getStatus()) {
                System.out.println(trip);
            }
        }
    }

    public static void displayVehicleType() {
        System.out.println(Separator.sep());
        System.out.println("1: Basic Truck");
        System.out.println("2: Reefer Truck");
        System.out.println("3: Tanker Truck");
        System.out.println("4: Ship");
        System.out.println(Separator.sep());
    }

    public static void displayPortOperation() {
        System.out.println(Separator.sep());
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
        System.out.println("15: Remove port");
        System.out.println(Separator.sep());

    }

    public static void displayContainerType() {
        System.out.println("1: Dry Storage");
        System.out.println("2: liquid");
        System.out.println("3: Open Top");
        System.out.println("4: Open Side");
        System.out.println("5: Refrigerated");
        System.out.println(Separator.sep());
    }

}
