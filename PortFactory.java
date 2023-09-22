import com.sun.security.jgss.GSSUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;
import java.util.Vector;

public class PortFactory {
    public static Port createPort() {
        Scanner input = new Scanner(System.in);
        boolean existed = false;
        boolean landingAbility;
        double longitude, latitude, storingCapacity;
        String portName;

        System.out.println("Current port(s) in the system: ");
        GUI.displayPort();
        System.out.print("Enter the new port name: ");
        portName = input.nextLine();
        try {
            System.out.print("Enter the new port longitude: ");
            longitude = Double.parseDouble(input.nextLine());
            System.out.print("Enter the new port latitude: ");
            latitude = Double.parseDouble(input.nextLine());
            System.out.print("Enter the new port storing capacity (Kg): ");
            storingCapacity = Double.parseDouble(input.nextLine());
        } catch (RuntimeException e) {
            System.out.println("You have to enter the number");
            return null;
        }

        try {
            System.out.println("Enter the port landing ability ('true' or 'false')");
            System.out.println("If 'true' the truck can land at this port");
            String landing = input.nextLine();
            if (landing.equals("true") || landing.equals("false")) {
                landingAbility = Boolean.parseBoolean(landing);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            System.out.println("You have to enter 'true' or 'false'");
            return null;
        }

        if (ContainerPortManagementSystem.checkPortInfo(latitude, longitude, portName)) {
            return null;
        }
        return new Port(portName, latitude, longitude, storingCapacity, landingAbility);
    }
}
