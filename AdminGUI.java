public class AdminGUI{

    public static void display() {
        System.out.println("Welcome back");
        System.out.println("Please enter associated number to do operation");
        System.out.println("1: Add container");
        System.out.println("2: Add port manager and port");
        System.out.println("3: Add Vehicle");
        System.out.println("4: Remove container");
        System.out.println("5: Remove port manager");
        System.out.println("6: Remove Vehicle");
    }

    public static void displayVehicleType(){
        System.out.println("1: Basic Truck");
        System.out.println("2: Reefer Truck");
        System.out.println("3: Tanker Truck");
        System.out.println("4: Ship");
    }

    public static void displayContainerType(){
        System.out.println("1: dry storage");
        System.out.println("2: liquid");
        System.out.println("3: open top");
        System.out.println("4: open side");
        System.out.println("5: refrigerated");
    }
}
