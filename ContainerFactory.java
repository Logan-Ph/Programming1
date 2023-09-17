public class ContainerFactory {
    public static Container createContainer(String containerType, double weight){
        return switch (containerType) {
            case "dry storage" -> new DryStorage(weight);
            case "liquid" -> new Liquid(weight);
            case "open top" -> new OpenTop(weight);
            case "open side" -> new OpenSide(weight);
            case "refrigerated" -> new Refrigerated(weight);
            default -> null;
        };
    }
}
