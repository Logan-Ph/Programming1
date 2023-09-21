import java.util.Vector;

public interface Vehicle {
    public boolean load(Container container);

    public Container unLoad(String id);

    public Container unLoad(Container container);

    public boolean refueling();

    public double calculateFuelConsumption(Port port);

    public double getCurrentStoringCapacity();

    public String generateID();

    public String getID();

    public Port getPort();

    public void setPort(Port port);

    public Vector<Container> getContainers();

    public double getCurrentFuel();
}
