import java.io.Serializable;
import java.util.Vector;

public interface Vehicle extends Serializable {
    boolean load(Container container);
    Container unLoad(String id);
    boolean refueling();
    double calculateFuelConsumption(Port port);
    double getCurrentStoringCapacity();
    String generateID();
    String getID();
    Port getPort();
    void setPort(Port port);
    Vector<Container> getContainers();
    double getCurrentFuel();
    void setName();
    void setCurrentFuel(double currentFuel);
}
