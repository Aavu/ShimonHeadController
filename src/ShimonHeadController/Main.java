package ShimonHeadController;

public class Main {
    public static void main(String[] args) {
        ShimonOSCHeadController headController = new ShimonOSCHeadController();
        System.out.println("\nHoming the head. Please wait...");
        headController.home();
        System.out.println("\n Homing done...");
        headController.launch();


//        HeadMotorController head = new HeadMotorController();
//        head.init();
//        head.home();
//        head.goTo("basePan", 1, 3.0f, 0.2f);

//        try {
//            OSCPortIn receiver = new OSCPortIn(30310);
//            OSCListener listener = (time, message) -> {
//                for (Object m: message.getArguments()) {
//                    System.out.println(m);
//                }
//            };
//            receiver.addListener("/head-commands", listener);
//            receiver.startListening();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
