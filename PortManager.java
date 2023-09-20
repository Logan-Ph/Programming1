import java.io.Serializable;
import java.util.Scanner;
import java.util.Vector;

public class PortManager implements User, Serializable {
    private final String password;
    private final String username;
    private Port port;

    public PortManager(String username, String password, Port port) {
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public String password() {
        return password;
    }

    public String username() {
        return username;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "PortManager{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public void operationCase(String opCase) {

    }

    public static User create(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the new port manager username: ");
        String username = input.nextLine();

        if (ContainerPortManagementSystem.checkUsername(username)){
            System.out.println("The user name has already exist. Please enter the username again");
            username = input.nextLine();
        }

        System.out.print("Enter the new port manager password: ");
        String password = input.nextLine();

        return new PortManager(username,password,null);
    }
    public void loadContainer() {
        // print vehicle and container in the port
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of vehicle in the port:");
        Vector<Vehicle> vehicles = this.port.getVehicles();
        for (Vehicle v: vehicles) {
            System.out.println(v);
        }
        System.out.println("List of container in the port:");
        Vector<Container> containers = this.port.getContainers();
        for (Container c: containers) {
            System.out.println(c);
        }
        boolean validVehicle;
        boolean validContainer;
        String inputVehicleID;
        String inputContainerID;
        Vehicle chosenVehicle;
        Container chosenContainer;
        do {
            System.out.println("Choose vehicle to load container:");
            inputVehicleID = scanner.next();
            try {
                chosenVehicle = this.port.findVehicleByID(inputVehicleID);
                validVehicle = true;
            } catch (NullPointerException e){
                validVehicle = false;
                System.out.println("Vehicle does not exist in this port.");
            }
        } while(!validVehicle);
        chosenVehicle = this.port.findVehicleByID(inputVehicleID);
        do {
            System.out.println("Choose container ID to load to vehicle:");
            inputContainerID = scanner.nextLine();
            try {
                chosenContainer = this.port.findContainerByID(inputContainerID);
                validContainer = true;
            } catch (NullPointerException e) {
                validContainer = false;
                System.out.println("Container does not exist in this port.");
            }
        } while (!validContainer);
        chosenContainer = this.port.findContainerByID(inputContainerID);
        chosenVehicle.load(chosenContainer);
    }
    public void unloadContainer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of vehicle in the port:");
        Vector<Vehicle> vehicles = this.port.getVehicles();
        for (Vehicle v: vehicles) {
            System.out.println(v);
        }
        boolean validVehicle;
        boolean validContainer;
        String inputVehicleID;
        String inputContainerID;
        Vector<Container> containers;
        Vehicle chosenVehicle;
        Container chosenContainer;
        do {
            System.out.println("Choose vehicle to unload container:");
            inputVehicleID = scanner.next();
            try {
                chosenVehicle = this.port.findVehicleByID(inputVehicleID);
                chosenVehicle.getContainers();
                validVehicle = true;
            } catch (NullPointerException e){
                validVehicle = false;
                System.out.println("Vehicle does not exist in this port or does not have container.");
            }
        } while(!validVehicle);
        chosenVehicle = ContainerPortManagementSystem.findVehicleById(inputVehicleID);
        containers = chosenVehicle.getContainers();
        System.out.println("List of containers in the vehicle:");
        for (Container c:containers) {
            System.out.println(c);
        }
        do {
            System.out.println("Choose container ID to unload:");
            inputContainerID = scanner.nextLine();
            if (chosenVehicle.getContainers().contains(ContainerPortManagementSystem.findContainerById(inputContainerID))) {
                validContainer = true;
            } else {
                validContainer = false;
                System.out.println("Container does not exist in this vehicle.");
            }
        } while (!validContainer);
        chosenVehicle.unLoad(inputContainerID);
    }
}
