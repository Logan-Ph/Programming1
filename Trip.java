import java.io.Serializable;
import java.time.LocalDate;

public class Trip implements Serializable {
    private final String id;
    private final Vehicle vehicle;
    private final LocalDate departureDate;
    private LocalDate arrivalDate;
    private final Port departurePort;
    private final Port arrivalPort;
    private boolean status;

    private final double amountFuel;

    public Trip(Vehicle vehicle,Port departurePort, Port arrivalPort, boolean status, double amountFuel) {
        id = generateID();
        this.departureDate = LocalDate.now();
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
        this.vehicle = vehicle;
        this.amountFuel = amountFuel;
    }

    public String getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setArrivalDate() {
        this.arrivalDate = LocalDate.now();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getAmountFuel() {
        return amountFuel;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public String generateID(){
        return IDFactory.generateID("trip");
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", vehicle=" + vehicle +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", departurePort=" + departurePort +
                ", arrivalPort=" + arrivalPort +
                ", status=" + status +
                ", amountFuel=" + amountFuel +
                '}';
    }
}
