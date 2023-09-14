public class SystemGUI implements GUI {
    @Override
    public void display() {
        System.out.println("------------------------------------------------");
        System.out.println("| COSC2081 GROUP ASSIGNMENT                    |");
        System.out.println("| CONTAINER PORT MANAGEMENT SYSTEM             |");
        System.out.println("| Instructor: Mr. Minh Vu & Dr. Phong Ngo      |");
        System.out.println("| Group: Group Name                            |");
        System.out.println("| s3975979 Pham Phuoc Sang                     |");
        System.out.println("| s3978387 Cao Nguyen Hai Linh                 |");
        System.out.println("| s3978486 Nguyen Ngoc Thanh Mai               |");
        System.out.println("| s3978798 Tran Pham Khanh Doan                |");
        System.out.println("------------------------------------------------");

    }

    public static void main(String[] args) {
        GUI gui = new SystemGUI();
        gui.display();
    }
}
