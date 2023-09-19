public class AdminGUI{

    public static void display() {
        System.out.println("Select the operation");
        System.out.println("1: Add container");
        System.out.println("2: Add port manager and port");
        System.out.println("3: Add Vehicle");
        System.out.println("4: Remove container");
        System.out.println("5: Remove port manager");
        System.out.println("6: Remove Vehicle");
        System.out.println("7: Operation on Port");
        System.out.println(Separator.sep());
    }

    public static void displayPort(){
        int i =1;
        for (Port port: ContainerPortManagementSystem.getPorts()){
            System.out.println(i++ + ": " + port.getId());
        }
    }

    public static void displayVehicleType(){
        System.out.println("1: Basic Truck");
        System.out.println("2: Reefer Truck");
        System.out.println("3: Tanker Truck");
        System.out.println("4: Ship");
        System.out.println(Separator.sep());

    }

    public static void displayContainerType(){
        System.out.println("1: Dry Storage");
        System.out.println("2: liquid");
        System.out.println("3: Open Top");
        System.out.println("4: Open Side");
        System.out.println("5: Refrigerated");
    }
}
