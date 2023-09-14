import java.time.LocalDate;

public class Trip {
    private String id;
    private Vehicle vehicle;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private boolean status;

    public Trip(Port departurePort, Port arrivalPort, boolean status) {
        id = id++;
        this.departureDate = LocalDate.now();
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
    }

    public Trip() {
    }

    public int getId() {
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

    public void sendTrip(Port port){
        port.addTrip(this);
        // get Port
        // invoke method add Trip of class port
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

}
