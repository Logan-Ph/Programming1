public interface Vehicle {
    public void load(Container container);

    public Container unLoad(String id);
    public void refueling(double fuel);
    public double calculateFuelConsumption(Port port);
    public double getCurrentStoringCapacity();

    public String generateID();

    public String getID();
}
