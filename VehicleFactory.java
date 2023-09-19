public class VehicleFactory {
    public static Vehicle createVehicle(String vehicleType, String vehicleName, double fuelCapacity, double storingCapacity, Port port){
        return switch (vehicleType) {
            case "1" -> new BasicTruck(vehicleName, fuelCapacity, storingCapacity, port);
            case "2" -> new ReeferTruck(vehicleName, fuelCapacity, storingCapacity, port);
            case "3" -> new TankerTruck(vehicleName, fuelCapacity, storingCapacity, port);
            case "4" -> new Ship(vehicleName, fuelCapacity, storingCapacity, port);
            default -> null;
        };
    }
}
