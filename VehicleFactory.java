public class VehicleFactory {
    public static Vehicle createVehicle(String vehicleType, String vehicleName, double fuelCapacity, double storingCapacity){
        return switch (vehicleType) {
            case "1" -> new BasicTruck(vehicleName, fuelCapacity, storingCapacity);
            case "2" -> new ReeferTruck(vehicleName, fuelCapacity, storingCapacity);
            case "3" -> new TankerTruck(vehicleName, fuelCapacity, storingCapacity);
            case "4" -> new Ship(vehicleName, fuelCapacity, storingCapacity);
            default -> null;
        };
    }
}
