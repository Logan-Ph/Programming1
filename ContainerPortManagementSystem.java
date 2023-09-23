import java.io.*;
import java.nio.file.FileSystems;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.Vector;

public class ContainerPortManagementSystem {

    private static Vector<Vehicle> vehicles = new Vector<>();
    private static Vector<Port> ports = new Vector<>();
    private static Vector<User> users = new Vector<>();
    private static Vector<Container> containers = new Vector<>();

    public static void run() throws IOException {
        Scanner input = new Scanner(System.in);
        String exit= "";

        GUI.display();
        System.out.println(Separator.sep());
        systemInitialization();
        System.out.println(Separator.sep());

        if (users.isEmpty()) {
            System.out.println("Because user admin does not exist in the system, you have to create new admin account.");
            users.add(Admin.create());
            System.out.println("Create admin successfully.");
            System.out.println(Separator.sep());
        }

        while (!exit.equals("q")){
            User user = login();
            System.out.println(Separator.sep());

            if (user == null){
                System.out.println("Wrong username!");
            }else {
                while (true){
                    if (user instanceof Admin) {
                        GUI.displayOperation();
                        System.out.print("Enter the number associated with the operation or 'x' to exit: ");
                        String operation = input.nextLine();
                        if (operation.equals("x")){break;}
                        user.operationCase(operation);
                    }else if (user instanceof PortManager){
                        GUI.displayOperationForPortManager();
                        System.out.print("Enter the number associated with the operation or 'x' to exit: ");
                        String operation = input.nextLine();
                        if (operation.equals("x")){break;}
                        user.operationCase(operation);
                    }
                }
            }
            System.out.println("Enter 'q' to end system or else to continue login");
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
        clearPortHistory();
    }

    private static void endSystem() {
        writePort();
        writeUser();
        writeVehicle();
        writeContainer();
        clearPortHistory();
    }

    public static boolean checkUsername(String username){
        for (User user: getUsers()){
            if (user.username().equals(username)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkPortInfo(double latitude, double longitude, String portName){
        for (Port port: getPorts()){
            if ((port.getLatitude() == latitude && port.getLongitude() == longitude)|| port.getName().equals(portName)){
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

    public static Port findPortById(String id){
        for(Port port: getPorts()){
            if (port.getId().equals(id)){
                return port;
            }
        }
        return null;
    }

    public static void clearPortHistory(){
        for (Port port:getPorts()){
            for (int i = port.getTrips().size()-1; i>=0; i--){
                if (port.getTrips().get(i).getStatus() && validateTime(port.getTrips().get(i).getArrivalDate())){
                    port.getTrips().subList(0,i+1).clear();
                    break;
                }else if (!port.getTrips().get(i).getStatus() && validateTime(port.getTrips().get(i).getDepartureDate())){
                    port.getTrips().subList(0,i+1).clear();
                    break;
                }
            }
        }
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
                .toString().concat("/Programming1/PortManagementSystem/Ports.txt")).getCanonicalPath();
    }

    public static String getUserFilePath() throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("/Programming1/PortManagementSystem/Users.txt")).getCanonicalPath();
    }

    public static String getVehicleFilePath() throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("/Programming1/PortManagementSystem/Vehicles.txt")).getCanonicalPath();
    }

    public static String getContainerFilePath() throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString().concat("/Programming1/PortManagementSystem/Containers.txt")).getCanonicalPath();
    }

    //check if the time exceeds 7 days
    public static boolean validateTime(LocalDate priorDate){
        return (ChronoUnit.DAYS.between(priorDate, LocalDate.now())) >= 7;
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
            e.printStackTrace();
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
