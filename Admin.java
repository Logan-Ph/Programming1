import java.io.Serializable;
import java.util.Scanner;
import java.util.Vector;

public class Admin implements User, Serializable {
    private Scanner input = new Scanner(System.in);
    private String password;
    private String username;

    public Admin(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    // phải try catch ở từng input cho vehicle
    public void createVehicle(){
        System.out.println("select what vehicle you want to create");
        AdminGUI.displayVehicleType();
        System.out.println("Enter the vehicle type");
        String vehicleType = input.nextLine().toLowerCase();
        System.out.println("Enter the vehicle name");
        String vehicleName = input.nextLine();
        System.out.println("Enter the fuel capacity of the vehicle");
        double fuelCapacity = input.nextDouble();
        System.out.println("Enter the storing capacity of the vehicle");
        double storingCapacity = input.nextDouble();

        Vehicle vehicle = VehicleFactory.createVehicle(vehicleType,vehicleName,fuelCapacity,storingCapacity); // chưa check điều kiện nếu vehicle null!!!

        Vector<Vehicle> vehicles = ContainerPortManagementSystem.getVehicles();
        vehicles.add(vehicle);
        System.out.println("Adding vehicle successfully");
    }

    public void createContainer(){
        System.out.println("select what Container you want to create");
        AdminGUI.displayContainerType();
        System.out.println("Enter the container type");
        String containerType = input.nextLine().toLowerCase();
        System.out.println("Enter the container weight");
        double containerWeight = input.nextDouble();

        Container container = ContainerFactory.createContainer(containerType,containerWeight); // chưa check điều kiện nếu container null!!!

        Vector<Container> containers = ContainerPortManagementSystem.getContainers();
        containers.add(container);
        System.out.println("Adding vehicle successfully");
    }
}
