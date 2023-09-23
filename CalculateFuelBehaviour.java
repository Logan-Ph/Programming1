public class CalculateFuelBehaviour {
    // return the fuel consumption per unit of weight per kilometres based on vehicle type and container type
    public static double calculateFuelConsumption(Container container, Vehicle vehicle){
        if (vehicle instanceof Ship && container instanceof DryStorage){
            return 3.5;
        } else if (vehicle instanceof Ship && container instanceof OpenTop) {
            return 2.8;
        } else if (vehicle instanceof Ship && container instanceof OpenSide) {
            return 2.7;
        } else if (vehicle instanceof Ship && container instanceof Refrigerated){
            return 4.5;
        } else if (vehicle instanceof Ship && container instanceof Liquid) {
            return 4.8;
        } else if (vehicle instanceof Truck && container instanceof DryStorage) {
            return 4.6;
        } else if (vehicle instanceof Truck && container instanceof OpenTop) {
            return 3.2;
        } else if (vehicle instanceof Truck && container instanceof OpenSide) {
            return 3.2;
        } else if (vehicle instanceof Truck && container instanceof Refrigerated) {
            return 5.4;
        } else if (vehicle instanceof Truck && container instanceof Liquid) {
            return 5.3;
        }
        else {
            return 0.0;
        }
    }
}
