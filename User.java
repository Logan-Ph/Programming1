import java.io.IOException;
import java.io.Serializable;

public interface User {
    public String password();
    public String username();
    public String toString();
    public void operationCase(String opCase) throws IOException;

}
