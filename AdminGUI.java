public class AdminGUI implements GUI{

    @Override
    public void display() {
        System.out.println("Welcome back");
        System.out.println("Please enter associated number to do operation");
        System.out.println("1: Add container");
        System.out.println("2: Add port manager and port");
        System.out.println("3: Add Vehicle");
        System.out.println("4: Remove container");
        System.out.println("5: Remove port manager");
        System.out.println("6: Remove Vehicle");
    }
}
