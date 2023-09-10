public class LoadContainerBehavior {
    public static boolean load(Container container, Vehicle vehicle){
        if (vehicle instanceof BasicTruck && container instanceof Refrigerated){
            return false;
        } else if (vehicle instanceof BasicTruck && container instanceof Liquid) {
            return false;
        } else return !(vehicle instanceof ReeferTruck) || !(container instanceof Liquid);
    }
}
