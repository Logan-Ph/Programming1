public class PortManagerGUI{

    public static void display() {
    }


    public static void displayPortOperation() {
        System.out.println("1: Add new container");
        System.out.println("2: Remove container");
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
