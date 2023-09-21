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
    public void operationCase(String opCase) throws IOException {
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


//        getPort();
//        try {
//            Container container = ContainerFactory.createContainer(port);
//            port.addContainer(container);
//            ContainerPortManagementSystem.getContainers().add(container);
//            System.out.println("Adding container into this port successfully");
//        } catch (NullPointerException e) {
//            System.out.println("Adding container into this port unsuccessfully");
//        }
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
                if(input.nextLine() == container.getId()) {
                    ContainerPortManagementSystem.getContainers().remove(container);
                };
                System.out.println("Remove container successfully");
            } catch (NullPointerException e) {
                System.out.println("Remove container unsuccessfully");
            }
        }
    }
}
