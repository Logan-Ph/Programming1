public class LoadContainerBehavior {
    public static boolean load(Container container, Vehicle vehicle){
        // return the boolean value if the vehicle can load that container
        if (vehicle instanceof BasicTruck && container instanceof Refrigerated){
            return false;
        } else if (vehicle instanceof BasicTruck && container instanceof Liquid) {
            return false;
        } else return !(vehicle instanceof ReeferTruck) || !(container instanceof Liquid);
    }
}
