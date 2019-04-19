package ShimonHeadController.siggraph;

//import ShimonHeadController.ShimonOSCHeadController;
//import ShimonHeadController.siggraph.playbehaviors.AccompanyPlayBehavior;
//import ShimonHeadController.siggraph.playbehaviors.CopyPlayBehavior;
//import ShimonHeadController.siggraph.playbehaviors.PlayBehavior;
//import ShimonHeadController.innards.Launcher;
//import ShimonHeadController.innards.debug.Debug;
//import ShimonHeadController.innards.iLaunchable;
//import ShimonHeadController.innards.iUpdateable;
////import ui.cocoawidgets.Inflator;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Timer;
//
///**
// * Created by Guy Hoffman
// * Date: Dec 7, 2009
// * Time: 7:03:39 PM
// */
//public class SiggraphMusician implements iLaunchable, iUpdateable, ReceiveOSCFromPython.iNewPhraseListener, PlayBehavior.iPlayEndListener {
//
//
//    private ReceiveOSCFromPython receiver;
//    private OSCPlayer player;
//
//    private Timer timer = new Timer();
//
//    private PlayBehavior playBehavior;
//
//    private ShimonOSCHeadController headController;
//    private String behavior = "NONE";
////    private PhraseVisualizer visualizer;
//
//    public void launch() {
//
//
//        receiver = new ReceiveOSCFromPython(this);
//        player = new OSCPlayer();
//        headController = new ShimonOSCHeadController();
//
////        Inflator.makeTriggerButton("Home", this, "home");
////        Inflator.makeTriggerButton("Start", this, "start");
////        Inflator.makeTriggerButton("Stop", this, "stop");
////        Inflator.makeTriggerButton("Disable", this, "disable");
////        Inflator.displayText("Status", "Startup                                        ");
//
////        visualizer = new PhraseVisualizer();
//
//        Debug.setDebugOn(true);
//
//        // ALWAYS LAST
//        Launcher.getLauncher().registerUpdateable(this);
//
//    }
//
//
//    public void update() {
//
////        visualizer.update();
//
////        Debug.setChannelStatus("Gestures", Inflator.getBoolean("Gestures", false, "Debug"));
////        Debug.setChannelStatus("Slider", Inflator.getBoolean("Slider", false, "Debug"));
////        Debug.setChannelStatus("SliderManager", Inflator.getBoolean("SliderManager", false, "Debug"));
////        Debug.setChannelStatus("SCONController", Inflator.getBoolean("SCONController", false, "Debug"));
////        Debug.setChannelStatus("ThreadedMotorController", Inflator.getBoolean("ThreadedMotorController", false, "Debug"));
////        Debug.setChannelStatus("BeatKeeper", Inflator.getBoolean("BeatKeeper", false, "Debug"));
//
//    }
//
//
//    public void newPhrase() {
////        visualizer.setPhrase(receiver.getLastPhrase());
//
//        headController.play();
////        playBehavior = new CopyPlayBehavior(this, receiver, player);
//        playBehavior = pickRandomBehavior();
//        delay(200);
//        playBehavior.play();
////        Inflator.displayText("Status", "Playing " + behavior);
//
//    }
//
//    public void playTogether() {
//
////        playBehavior = new AccompanyPlayBehavior(this, visualizer, receiver, player);
//        delay(200);
////        playBehavior.play();
//        headController.listen();
//
//    }
//
//
////    public void endPlayTogether() {
////        playBehavior.end();
////    }
//
//    public void playDone() {
////        playBehavior = null;
//        delay(700);
//        headController.listen();
////        Inflator.displayText("Status", "Listening");
//
//    }
//
//    public void home()
//    {
//        headController.home();
//    }
//
//    public void start()
//    {
//        headController.launch();
////        Inflator.displayText("Status", "Listening");
//
//    }
//
//    public void stop()
//    {
//        headController.stop();
//    }
//
//    public void disable()
//    {
//        headController.disable();
//    }
//
//    private void delay(long ms)
//    {
//        try {
//            Thread.sleep(ms);
//        } catch (InterruptedException ignored) {
//
//        }
//    }
//
//    private PlayBehavior pickRandomBehavior ()  {
//        String [] classes = new String[] {
////                "ShimonHeadController.siggraph.playbehaviors.CopyPlayBehavior",
//                "ShimonHeadController.siggraph.playbehaviors.ClimbCopyPlayBehavior",
////                "ShimonHeadController.siggraph.playbehaviors.FasterCopyPlayBehavior",
//                "ShimonHeadController.siggraph.playbehaviors.ArpCopyPlayBehavior",
//                "ShimonHeadController.siggraph.playbehaviors.FasterSegmentPlayBehavior",
//                "ShimonHeadController.siggraph.playbehaviors.ChordCopyPlayBehavior"
//        };
//
//        int idx = (int) (Math.random() * classes.length);
//
////        PlayBehavior pb = new CopyPlayBehavior(this, visualizer, receiver, player);
//        try {
//            Class c = Class.forName(classes[idx]);
//            Constructor cs = c.getConstructor(PlayBehavior.iPlayEndListener.class, PlayBehavior.iPlayBeatListener.class, ReceiveOSCFromPython.class, OSCPlayer.class);
////            pb =  (PlayBehavior) cs.newInstance(this, visualizer, receiver, player);
//            behavior = classes[idx].substring(classes[idx].lastIndexOf('.'));
//        }
//        catch (ClassNotFoundException e) {
//            System.out.println("Class Not Found " + e);
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            System.out.println("Illegal Access " + e);
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            System.out.println("No Such Constructor " + e);
//            e.printStackTrace();
//        }
//        catch (InstantiationException e) {
//            System.out.println("Instantiation Error " + e);
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            System.out.println("Invocation Target Error " + e);
//            e.printStackTrace();
//        }
//
//        return pb;
//
//    }
//}
