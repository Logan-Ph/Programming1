import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;

public class PortManager implements User, Serializable {
    private final String password;
    private final String username;
    private Port port;
    private Vector<Vehicle> listAllVehicleInPort;
    private Vector<Vehicle> listAllTripFromDayAToB;

    public PortManager(String username, String password, Port port) {
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public String password() {
        return password;
    }

    public String username() {
        return username;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "PortManager{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public void operationCase(String opCase) {

    }

    public void portOperation(String opCase, Port port){}
    public static User create(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the new port manager username: ");
        String username = input.nextLine();

        if (ContainerPortManagementSystem.checkUsername(username)){
            System.out.println("The user name has already exist. Please enter the username again");
            username = input.nextLine();
        }

        System.out.print("Enter the new port manager password: ");
        String password = input.nextLine();

        return new PortManager(username,password,null);
    }

    public Vector<Vehicle> getListAllVehicleInPort(){
        Vector<Vehicle> listVehicle = port.getVehicles();
        Vector<Vehicle> listVehicleInPort = new Vector<>();
        for(Vehicle currentVehicle : listVehicle){
            if (currentVehicle.getPort() == port){
                listVehicleInPort.add(currentVehicle);
            }
        }
        return listVehicleInPort;
    }

//    public Vector<Trip> listAllTripFromDayAToB(LocalDate startTime, LocalDate endTime){
//        Vector<Trip> listTrip = port.getTrips();
//        Vector<Trip> listTripOut = new Vector<>();
//        for (Trip currentTrip : listTrip) {
//            if ((startTime.isAfter(currentTrip.getArrivalDate()) && endTime.isBefore(currentTrip.getArrivalDate()) && currentTrip.getArrivalPort() == port)
//                    || (startTime.isAfter(currentTrip.getDepartureDate()) && endTime.isBefore(currentTrip.getDepartureDate()) && currentTrip.getDeparturePort() == port))
//                listTripOut.add(currentTrip);
//        }
//        return listTripOut;
//    }
}
