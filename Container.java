import java.util.*;
public abstract class Container {
    private String id;
    private double weight;
    private Vehicle vehicle;
    private Port port;

    public Container( double weight, Port port) {
        this.id = generateID(); // generate the ID
        this.weight = weight;
        this.vehicle = null;
        this.port = port;
    }

    public Container(){}

    public String getID() {
        return id;
    }

    public double getWEIGHT() {
        return weight;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String generateID() {
        return IDFactory.generateID("container");
    } // using the IDFactory to generate the ID

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public void updateWeight() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter the weight of the container (kg): ");
            weight = Double.parseDouble(input.nextLine());
            System.out.println("Update container weight successfully.");
        } catch (RuntimeException e) {
            System.out.println("Container weight must be a number.");
        }
    }

    public abstract String toString();
}
