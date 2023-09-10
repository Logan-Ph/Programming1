public class PortManager implements User{
    private String password;
    private String username;

    public PortManager(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
