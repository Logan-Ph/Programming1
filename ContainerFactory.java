import java.util.Scanner;

public class ContainerFactory {
    public static Container createContainer(Port port){
        Scanner input = new Scanner(System.in);
        double weight;
        String containerType;
        try {
            System.out.print("Enter the weight of the container (kg): ");
            weight = Double.parseDouble(input.nextLine()); // get the input from user
            if (weight<=0){
                System.out.println("The weight can not be negative or zero");
                return null;
            }
            System.out.println("Container type:");
            GUI.displayContainerType();
            System.out.print("Enter number associated to the container type: ");
            containerType = input.nextLine(); // get input from user
        }catch (RuntimeException e){
            System.out.println("You have to enter a number");
            return null;
        }

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
