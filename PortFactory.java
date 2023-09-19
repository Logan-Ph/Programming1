import com.sun.security.jgss.GSSUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;
import java.util.Vector;

public class PortFactory {
    public static Port createPort(){
        Scanner input = new Scanner(System.in);
        boolean existed = false;
        boolean landingAbility;
        double longitude,latitude, storingCapacity;
        String portName;

        while (true){
            if (existed){
                System.out.println("The port information existed. Please enter again!");
            }
            System.out.println("Current port(s) in the system: ");
            AdminGUI.displayPort();
            System.out.print("Enter the new port name: ");
            portName = input.nextLine();
            System.out.print("Enter the new port longitude: ");
            longitude = Double.parseDouble(input.nextLine());
            System.out.print("Enter the new port latitude: ");
            latitude =  Double.parseDouble(input.nextLine());
            System.out.print("Enter the new port storing capacity (Kg): ");
            storingCapacity =  Double.parseDouble(input.nextLine());
            System.out.println("Enter the port landing ability ('true' or 'false')");
            System.out.println("If 'true' the truck can land at this port");
            landingAbility =  Boolean.parseBoolean(input.nextLine());
            input.reset();
            if (ContainerPortManagementSystem.checkPortInfo(latitude,longitude,portName)){
                existed = true;
            }else {
                break;
            }
        }
        return new Port(portName,latitude,longitude,storingCapacity,landingAbility);
    }

    public static void createPortHistoryFile(Port port) throws IOException {
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
}
