import java.io.*;
import java.nio.file.FileSystems;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class ContainerPortManagementSystem {
    private static Vector<Vehicle> vehicles = new Vector<>();
    private static Vector<Port> ports = new Vector<>();
    private static Vector<User> users = new Vector<>();
    private static Vector<Container> containers = new Vector<>();

    public static void main(String[] args) {
        GUI systemGUI = new SystemGUI();
        systemGUI.display();
        systemInitialization();
        users.forEach(System.out::println);
    }

    private static void loadUsers() {
        users = readUser();
    }

    private static void loadPorts() {
        ports = readPort();
    }

    private static void loadVehicles() {
        vehicles = readVehicle();
    }

    private static void loadContainers() {
        containers = readContainer();
    }

    private static void systemInitialization() {
        loadUsers();
        loadPorts();
        loadVehicles();
        loadContainers();
    }

    private static boolean login() {
        Scanner input = new Scanner(System.in);
        Vector<User> users = getUsers();

        System.out.println("Please enter the username");
        String username = input.nextLine();
        System.out.println("Please enter the password");
        String password = input.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
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

    //check if the time exceeds 7 days
    public boolean validateTime(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate priorDate = LocalDate.parse(date, dtf);
        LocalDate thisDate = LocalDate.now();
        return (ChronoUnit.DAYS.between(priorDate, thisDate)) >= 7;
    }

    public static String getContainerFilePath() {
        return FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("\\Programming1\\PortManagementSystem\\Containers.txt");
    }

    private static void writeContainer() {
        try {
            ObjectOutputStream containerOut = new ObjectOutputStream(new FileOutputStream(getContainerFilePath()));
            containerOut.writeObject(getContainers());
            containerOut.close();
            System.out.println("Writing Container successfully");
        } catch (IOException e) {
            System.out.println("The 'Containers.txt' file does not exist");
        }
    }

    private static Vector<Container> readContainer() {
        Vector<Container> containers = null;
        try {
            ObjectInputStream containerIn = new ObjectInputStream(new FileInputStream(getContainerFilePath()));
            containers = (Vector<Container>) containerIn.readObject();
            containerIn.close();
            System.out.println("Reading Vehicle successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Containers.txt' file does not exist or the file is empty");
        }
        return containers;
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

    private static Vector<Vehicle> readVehicle() {
        Vector<Vehicle> vehicles = null;
        try {
            ObjectInputStream vehicleIn = new ObjectInputStream(new FileInputStream(getVehicleFilePath()));
            vehicles = (Vector<Vehicle>) vehicleIn.readObject();
            vehicleIn.close();
            System.out.println("Reading Vehicle successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Vehicles.txt' file does not exist or the file is empty");
        }
        return vehicles;
    }

    private static void writePort() {
        try {
            ObjectOutputStream portOut = new ObjectOutputStream(new FileOutputStream(getPortFilePath()));
            portOut.writeObject(getPorts());
            portOut.close();
            System.out.println("Writing 'Ports.txt' successfully");
        } catch (IOException e) {
            System.out.println("The 'Ports.txt' file does not exist!");
        }
    }

    private static Vector<Port> readPort() {
        Vector<Port> ports = null;
        try {
            ObjectInputStream portIn = new ObjectInputStream(new FileInputStream(getPortFilePath()));
            ports = (Vector<Port>) portIn.readObject();
            portIn.close();
            System.out.println("Reading Port successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Ports.txt' file does not exist or the file is empty");
        }
        return ports;
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

    private static Vector<User> readUser() {
        Vector<User> users = null;
        try {
            ObjectInputStream UserIn = new ObjectInputStream(new FileInputStream(getUserFilePath()));
            users = (Vector<User>) UserIn.readObject();
            UserIn.close();
            System.out.println("Reading User successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The 'Users.txt' file does not exist or the file is empty");
        }
        return users;
    }
}
