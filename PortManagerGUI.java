public class PortManagerGUI{

    public static void display() {
    }


    public static void displayOperationForPortManager() {
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
        System.out.println(Separator.sep());
    }

    public static void displayContainerInPort(Port port) {
        for (Container container : port.getContainers()) {
            System.out.println(container);
        }
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
