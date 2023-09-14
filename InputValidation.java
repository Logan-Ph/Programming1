import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    public static boolean validateFormatShip(String input){
        Pattern pattern = Pattern.compile("sh-\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean validateFormatTruck(String input){
        Pattern pattern = Pattern.compile("tr-\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean validateFormatContainer(String input){
        Pattern pattern = Pattern.compile("c-\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean validateFormatPort(String input){
        Pattern pattern = Pattern.compile("p-\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
