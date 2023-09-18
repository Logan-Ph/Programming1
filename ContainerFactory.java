public class ContainerFactory {
    public static Container createContainer(String containerType, double weight){
        return switch (containerType) {
            case "1" -> new DryStorage(weight);
            case "2" -> new Liquid(weight);
            case "3" -> new OpenTop(weight);
            case "4" -> new OpenSide(weight);
            case "5" -> new Refrigerated(weight);
            default -> null;
        };
    }
}
