import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ContainerPortManagementSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(java.lang.System.in);
        User port1 = new PortManager("Port", "Port123");
        User admin = new PortManager("Admin", "admin123");
        ArrayList<User> users = new ArrayList<>();
        users.add(port1);
        users.add(admin);

        while (true){
            try{
                System.out.println("Please enter the username");
                String username = input.nextLine();
                System.out.println("Please enter the password");
                String pass = input.nextLine();
                Boolean succesfull = false;

                for (User user: users){
                    if(user.getUsername().equals(username) && user.getPassword().equals(pass)&& user instanceof PortManager){
                        System.out.println(port());
                        succesfull = true;
                    } else if (user.getUsername().equals(username) && user.getPassword().equals(pass)&& user instanceof Admin) {
                        System.out.println(admin());
                    }
                    else {
                        if (succesfull){
                            break;
                        }
                    }
                }
                if (!succesfull){
                    System.out.println("The username/password is wrong");
                }
            }catch (Exception e){
                System.out.println("The username/password is wrong");
            }
        }

    }

    static String admin(){
        return "I'm admin";
    }
    static String port(){
        return "I'm admin";
    }
}
