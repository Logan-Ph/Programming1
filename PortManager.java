import java.io.Serializable;

public class PortManager implements User, Serializable {
    private String password;
    private String username;

    private Port port;

    public PortManager(String username, String password, Port port) {
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public PortManager(String username, String password) {
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Port getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "PortManager{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
