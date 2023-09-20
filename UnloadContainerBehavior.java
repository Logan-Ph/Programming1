public class UnloadContainerBehavior {
    public static boolean unload(Port port, Container container) {
        return !(port.getCurrentStoringCapacity() + container.getWeight() > port.getStoringCapacity());
    }
}
