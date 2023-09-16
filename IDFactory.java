import java.text.SimpleDateFormat;
import java.util.Date;

public class IDFactory {
    public static String generateID(String type){
        switch (type) {
            case "trip" -> {
                return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            }
            case "ship" -> {
                return "sh-".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            }
            case "truck" -> {
                return "tr-".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            }
            case "port" -> {
                return "p-".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            }
            case "container" -> {
                return "c-".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            }
        }
        return null;
    }
}
