import java.util.Scanner;

public class PortFactory {
    public static Port createPort() {
        Scanner input = new Scanner(System.in);
        boolean landingAbility;
        double longitude, latitude, storingCapacity;
        String portName;

        System.out.println("Current port(s) in the system: ");
        GUI.displayPort();
        System.out.print("Enter the new port name: ");
        portName = input.nextLine();
        try {
            System.out.print("Enter the new port longitude: ");
            longitude = Double.parseDouble(input.nextLine()); // get the longitude from user
            System.out.print("Enter the new port latitude: ");
            latitude = Double.parseDouble(input.nextLine()); // get the latitude from user
            System.out.print("Enter the new port storing capacity (Kg): ");
            storingCapacity = Double.parseDouble(input.nextLine()); // get the storing capacity from user
        } catch (RuntimeException e) {
            System.out.println("You have to enter the number");
            return null;
        }

        try {
            System.out.println("Enter the port landing ability ('true' or 'false')");
            System.out.println("If 'true' the truck can land at this port");
            String landing = input.nextLine(); // get the landing ability from user
            if (landing.equals("true") || landing.equals("false")) { // validate the prompt
                landingAbility = Boolean.parseBoolean(landing);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            System.out.println("You have to enter 'true' or 'false'");
            return null;
        }

        if (ContainerPortManagementSystem.checkPortInfo(latitude, longitude, portName)) { // return null if the port info has exist in the system
            return null;
        }
        return new Port(portName, latitude, longitude, storingCapacity, landingAbility);
    }
}
