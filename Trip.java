import java.io.Serializable;
import java.time.LocalDate;

public class Trip implements Serializable {
    private final String ID;
    private final Vehicle VEHICLE;
    private final LocalDate DEPARTURE_DATE;
    private LocalDate arrivalDate;
    private final Port DEPARTURE_PORT;
    private final Port ARRIVAL_PORT;
    private boolean status;

    private final double amountFuel;

    public Trip(Vehicle vehicle,Port departurePort, Port arrivalPort, boolean status, double amountFuel) {
        this.ID = generateID();
        this.DEPARTURE_DATE = LocalDate.now();
        this.DEPARTURE_PORT = departurePort;
        this.ARRIVAL_PORT = arrivalPort;
        this.status = status;
        this.VEHICLE = vehicle;
        this.amountFuel = amountFuel;
    }

    public String getId() {
        return ID;
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
        return VEHICLE;
    }

    public LocalDate getDepartureDate() {
        return DEPARTURE_DATE;
    }

    public Port getArrivalPort() {
        return ARRIVAL_PORT;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return DEPARTURE_PORT;
    }

    public String generateID(){
        return IDFactory.generateID("trip");
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + ID + '\'' +
                ", vehicle=" + VEHICLE +
                ", departureDate=" + DEPARTURE_DATE +
                ", arrivalDate=" + arrivalDate +
                ", departurePort=" + DEPARTURE_PORT +
                ", arrivalPort=" + ARRIVAL_PORT +
                ", status=" + status +
                ", amountFuel=" + amountFuel +
                '}';
    }
}
