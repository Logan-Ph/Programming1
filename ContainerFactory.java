public class ContainerFactory {
    public static Container createContainer(String containerType, double weight, Port port){
        return switch (containerType) {
            case "1" -> new DryStorage(weight, port);
            case "2" -> new Liquid(weight, port);
            case "3" -> new OpenTop(weight, port);
            case "4" -> new OpenSide(weight, port);
            case "5" -> new Refrigerated(weight, port);
            default -> null;
        };
    }
}
