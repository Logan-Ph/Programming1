import java.io.*;
import java.nio.file.FileSystems;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.Vector;

public class ContainerPortManagementSystem {
    private static Vector<Vehicle> vehicles = new Vector<>();
    private static Vector<Port> ports = new Vector<>();
    private static Vector<User> users = new Vector<>();
    private static Vector<Container> containers = new Vector<>();

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String exit= "";

        SystemGUI.display();
        System.out.println(Separator.sep());
        systemInitialization();
        System.out.println(Separator.sep());

        while (!exit.equals("q")){
            User user = login();
            System.out.println(Separator.sep());
            while (user!= null){
                if (user instanceof Admin) {
                    AdminGUI.display();
                    System.out.println("Enter the number associated with the operation");
                    String operation = input.nextLine();
                    user.operationCase(operation);
                    System.out.println("Enter 'x' to exit to login page or else to continue");
                    if (input.nextLine().equals("x")){break;}
                }else if (user instanceof PortManager){
                    System.out.println("asdsdafasd");
                    System.out.println("Enter 'x' to exit to login page or else to continue");
                    if (input.nextLine().equals("x")){break;}
                }
            }
            System.out.println("Enter 'q' to end system or else to continue");
            exit = input.nextLine();
            System.out.println(Separator.sep());
        }
        endSystem();
        System.out.println(Separator.sep());
    }

    private static void systemInitialization() {
        readUser();
        readPort();
        readVehicle();
        readContainer();
    }

    private static void endSystem() {
        writePort();
        writeUser();
        writeVehicle();
        writeContainer();
    }

    public static boolean checkUsername(String username){
        for (User user: getUsers()){
            if (user.username().equals(username)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkCoordinatePort(double latitude, double longitude){
        for (Port port: getPorts()){
            if (port.getLatitude() == latitude && port.getLongitude() == longitude){
                return true;
            }
        }
        return false;
    }

    private static User login() {
        Scanner input = new Scanner(System.in);
        Vector<User> users = getUsers();

        System.out.println("Please enter the username");
        String username = input.nextLine().strip();
        System.out.println("Please enter the password");
        String password = input.nextLine().strip();
        for (User user : users) {
            if (user.username().equals(username) && user.password().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static Container findContainerById(String id){
        for (Container container: getContainers()){
            if (container.getId().equals(id)){
                return container;
            }
        }
        return null;
    }

    public static Vehicle findVehicleById(String id){
        for(Vehicle vehicle: getVehicles()){
            if (vehicle.getID().equals(id)){
                return vehicle;
            }
        }
        return null;
    }

    public static Port findPortById(String id){
        for(Port port: getPorts()){
            if (port.getId().equals(id)){
                return port;
            }
        }
        return null;
    }

    public static Vector<Container> getContainers() {
        return containers;
    }

    public static Vector<Port> getPorts() {
        return ports;
    }

    public static Vector<User> getUsers() {
        return users;
    }

    public static Vector<Vehicle> getVehicles() {
        return vehicles;
    }

    public static String getPortFilePath() throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("\\Programming1\\PortManagementSystem\\Ports.txt")).getCanonicalPath();
    }

    public static String getUserFilePath() throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("\\Programming1\\PortManagementSystem\\Users.txt")).getCanonicalPath();
    }

    public static String getVehicleFilePath() throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("\\Programming1\\PortManagementSystem\\Vehicles.txt")).getCanonicalPath();
    }

    public static String getContainerFilePath() throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("\\Programming1\\PortManagementSystem\\Containers.txt")).getCanonicalPath();
    }

    //check if the time exceeds 7 days
    public boolean validateTime(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate priorDate = LocalDate.parse(date, dtf);
        LocalDate thisDate = LocalDate.now();
        return (ChronoUnit.DAYS.between(priorDate, thisDate)) >= 7;
    }

    private static void writeContainer() {
        try {
            ObjectOutputStream containerOut = new ObjectOutputStream(new FileOutputStream(getContainerFilePath()));
            containerOut.writeObject(getContainers());
            containerOut.close();
            System.out.println("Writing Container successfully");
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private static void readContainer() {
        try {
            ObjectInputStream containerIn = new ObjectInputStream(new FileInputStream(getContainerFilePath()));
            containers = (Vector<Container>) containerIn.readObject();
            containerIn.close();
            System.out.println("Reading Container successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Containers.txt' file does not exist or the file is empty");
        }
    }

    private static void writeVehicle() {
        try {
            ObjectOutputStream vehicleOut = new ObjectOutputStream(new FileOutputStream(getVehicleFilePath()));
            vehicleOut.writeObject(getVehicles());
            vehicleOut.close();
            System.out.println("Writing Vehicle successfully");
        } catch (IOException e) {
            System.out.println("The 'Vehicles.txt' file does not exist");
        }
    }

    private static void readVehicle() {
        try {
            ObjectInputStream vehicleIn = new ObjectInputStream(new FileInputStream(getVehicleFilePath()));
            vehicles = (Vector<Vehicle>) vehicleIn.readObject();
            vehicleIn.close();
            System.out.println("Reading Vehicle successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Vehicles.txt' file does not exist or the file is empty");
        }
    }

    private static void writePort() {
        try {
            ObjectOutputStream portOut = new ObjectOutputStream(new FileOutputStream(getPortFilePath()));
            portOut.writeObject(getPorts());
            portOut.close();
            System.out.println("Writing Port successfully");
        } catch (IOException e) {
            System.out.println("The 'Ports.txt' file does not exist!");
        }
    }

    private static void readPort() {
        try {
            ObjectInputStream portIn = new ObjectInputStream(new FileInputStream(getPortFilePath()));
            ports = (Vector<Port>) portIn.readObject();
            portIn.close();
            System.out.println("Reading Port successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Ports.txt' file does not exist or the file is empty");
        }
    }

    private static void writeUser() {
        try {
            ObjectOutputStream userOut = new ObjectOutputStream(new FileOutputStream(getUserFilePath()));
            userOut.writeObject(getUsers());
            userOut.close();
            System.out.println("Writing User successfully");
        } catch (IOException e) {
            System.out.println("The 'Users.txt' file does not exist!");
        }
    }

    private static void readUser() {
        try {
            ObjectInputStream UserIn = new ObjectInputStream(new FileInputStream(getUserFilePath()));
            users = (Vector<User>) UserIn.readObject();
            UserIn.close();
            System.out.println("Reading User successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Users.txt' file does not exist or the file is empty");
        }
    }
}
