import java.util.Scanner;

public class VehicleFactory {
    public static Vehicle createVehicle(Port port) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the vehicle name: ");
        String vehicleName = input.nextLine(); // get the vehicle name from user
        String vehicleType;
        double fuelCapacity, storingCapacity;
        try {
            System.out.print("Enter the fuel capacity of the vehicle (Gallon): ");
            fuelCapacity = Double.parseDouble(input.nextLine()); // get the fuel capacity from user
            if (fuelCapacity<0){
                System.out.println("The fuel capacity can not be negative");
                return null;
            }
            System.out.print("Enter the storing capacity of the vehicle (Kg): ");
            storingCapacity = Double.parseDouble(input.nextLine()); // get the storing capacity from user
            if (storingCapacity<0){
                System.out.println("The fuel capacity can not be negative");
                return null;
            }
            System.out.println("Vehicle type: ");
            GUI.displayVehicleType();
            System.out.print("Enter the number associated with the vehicle type: ");
            vehicleType = input.nextLine(); // get the vehicle type from the user
        }catch (RuntimeException e){
            System.out.println("You have to enter the number");
            return null;
        }

        return switch (vehicleType) {
            case "1" -> new BasicTruck(vehicleName, fuelCapacity, storingCapacity, port);
            case "2" -> new ReeferTruck(vehicleName, fuelCapacity, storingCapacity, port);
            case "3" -> new TankerTruck(vehicleName, fuelCapacity, storingCapacity, port);
            case "4" -> new Ship(vehicleName, fuelCapacity, storingCapacity, port);
            default -> null;
        };
    }
}
