import java.util.Date;

public class Trip {
    private String id;
    private Vehicle vehicle;
    private Date departureDate;
    private Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private boolean status;

    public Trip(Date departureDate, Date arrivalDate, Port departurePort, Port arrivalPort, boolean status) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
    }

    public Trip() {
    }

    public String getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
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

    public Date getDepartureDate() {
        return departureDate;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }


}
