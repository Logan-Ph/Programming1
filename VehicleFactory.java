public class VehicleFactory {
    public static Vehicle createVehicle(String vehicleType, String vehicleName, double fuelCapacity, double storingCapacity){
        return switch (vehicleType) {
            case "basic truck" -> new BasicTruck(vehicleName, fuelCapacity, storingCapacity);
            case "reefer truck" -> new ReeferTruck(vehicleName, fuelCapacity, storingCapacity);
            case "tanker truck" -> new TankerTruck(vehicleName, fuelCapacity, storingCapacity);
            case "ship truck" -> new Ship(vehicleName, fuelCapacity, storingCapacity);
            default -> null;
        };
    }
}
