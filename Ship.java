import java.util.ArrayList;
import java.util.HashMap;

public class Ship implements Vehicle{
    private String id;
    private String name;
    private double currentFuel;
    private double storingCapacity;
    private ArrayList<Container> containers;
    private HashMap<Container, Integer> contQuantity;


    @Override
    public void load(Container container) {

    }

    @Override
    public Container unLoad() {
        return null;
    }

    @Override
    public void refuelling() {

    }
}
