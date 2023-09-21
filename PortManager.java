import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

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
    public void operationCase(String opCase){
        switch (opCase) {
            case "1" -> createContainer(port);
            case "2" -> removeContainer(port);
            default -> System.out.println("You have to choose the number associated with the operation");
        }
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

    public void createContainer(Port port) {
        Container container = ContainerFactory.createContainer(port);
        port.addContainer(container);
        ContainerPortManagementSystem.getContainers().add(container);
        System.out.println("Adding container into this port successfully");
    }

    public void removeContainer(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.println("Current Container(s) in the port: " + port.getName());
        AdminGUI.displayContainerInPort(port);
        if (port.getContainers().isEmpty()) {
            System.out.println("There are no container in the port");
        } else {
            System.out.print("Enter the container id associated to remove: ");
            Container container = port.removeContainer(input.nextLine());
            try {
                ContainerPortManagementSystem.getContainers().remove(container);
                System.out.println("Remove container successfully");
            } catch (NullPointerException e) {
                System.out.println("Remove container unsuccessfully");
            }
        }
    }

    public void loadContainer() {
        // print vehicle and container in the port
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of vehicle in the port:");
        for (Vehicle v : this.port.getVehicles()) {
            System.out.println(v);
        }
        System.out.println("List of container in the port:");
        for (Container c : this.port.getContainers()) {
            System.out.println(c);
        }
        boolean validInput;
        String inputID;
        Vehicle chosenVehicle;
        Container chosenContainer;
        do {
            System.out.println("Enter vehicle ID to load container:");
            inputID = scanner.nextLine();
            chosenVehicle = this.port.findVehicleByID(inputID);
            validInput = (chosenVehicle != null);
            if (!validInput) {
                System.out.println("Vehicle does not exist in this port.");
            }
        } while (!validInput);
        do {
            System.out.println("Enter container ID to load to vehicle:");
            inputID = scanner.nextLine();
            chosenContainer = this.port.findContainerByID(inputID);
            validInput = (chosenContainer != null);
            if (!validInput) {
                System.out.println("Container does not exist in this port.");
            }
        } while (!validInput);
        chosenVehicle.load(chosenContainer);
        System.out.println("Container loaded.");
    }

    public void unloadContainer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of vehicle in the port:");
        for (Vehicle v: this.port.getVehicles()) {
            System.out.println(v);
        }
        boolean validInput;
        String inputID;
        Vehicle chosenVehicle = null;
        Container chosenContainer = null;
        do {
            System.out.println("Enter vehicle ID to unload container:");
            inputID = scanner.nextLine();
            chosenVehicle = this.port.findVehicleByID(inputID);
            validInput = (chosenVehicle != null);
            if (!validInput) {
                System.out.println("Vehicle does not exist in this port.");
            }
        } while (!validInput);
        System.out.println("List of containers in the vehicle:");
        for (Container c: chosenVehicle.getContainers()) {
            System.out.println(c);
        }
        do {
            System.out.println("Enter container ID to unload:");
            inputID = scanner.nextLine();
            validInput = chosenVehicle.getContainers().contains(ContainerPortManagementSystem.findContainerById(inputID));
            if (!validInput) {
                System.out.println("Container does not exist in this vehicle.");
            }
        } while (!validInput);
        chosenVehicle.unLoad(inputID);
        System.out.println("Container unloaded.");
    }
}
