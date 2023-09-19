public interface Container {
    public String getId();

    public double getWeight();

    public Vehicle getVehicle();

    public void setVehicle(Vehicle vehicle);

    public String generateID();

    public void setPort(Port port);

    public Port getPort();
}
