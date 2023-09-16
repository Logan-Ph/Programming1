public class LandingBehaviour {
    // if the vehicle is truck and the landing ability is 'true' (1) return 'true'
    // else if the vehicle is ship and the landing ability is 'false' (0) return 'true'
    // the remains cases return 'false'
    public static boolean landing (Port port, Vehicle vehicle){
        return !(vehicle instanceof Truck) || port.isLandingAbility();
    }
}
