package ShimonHeadController;

public class Main {
    public static void main(String[] args) throws InterruptedException {
            ShimonOSCHeadController headController = new ShimonOSCHeadController();
            System.out.println("\nHoming the head. Please wait...");
            headController.home();
            Thread.sleep(5000);
            System.out.println("\n Homing done...");
            headController.launch();
    }
}
