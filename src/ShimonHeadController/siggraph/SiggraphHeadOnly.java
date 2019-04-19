package ShimonHeadController.siggraph;

import ShimonHeadController.ShimonOSCHeadController;
import ShimonHeadController.siggraph.playbehaviors.PlayBehavior;
import ShimonHeadController.innards.Launcher;
import ShimonHeadController.innards.debug.Debug;
import ShimonHeadController.innards.iLaunchable;
import ShimonHeadController.innards.iUpdateable;
//import ui.cocoawidgets.Inflator;

/**
 * Created by Guy Hoffman
 * Date: Dec 7, 2009
 * Time: 7:03:39 PM
 */
public class SiggraphHeadOnly implements iLaunchable, iUpdateable, PlayBehavior.iPlayEndListener {



    private ShimonOSCHeadController headController;
    private String behavior = "NONE";
    private boolean isPlaying = false;

    public void launch() {


        headController = new ShimonOSCHeadController();

//        Inflator.makeTriggerButton("Home", this, "home");
//        Inflator.makeTriggerButton("Start", this, "start");
//        Inflator.makeTriggerButton("Stop", this, "stop");
//        Inflator.makeTriggerButton("Disable", this, "disable");
//        Inflator.displayText("Status", "Startup                                        ");
//
//        Inflator.makeTriggerButton("New Phrase", this, "newPhrase");
//        Inflator.makeTriggerButton("Play Done", this, "playDone");


        Debug.setDebugOn(true);

        // ALWAYS LAST
        Launcher.getLauncher().registerUpdateable(this);

    }


    public void update() {

        if (!headController.isPlaying() && isPlaying) {
            playDone();
        } if (headController.isPlaying() && !isPlaying) {
            newPhrase();
        }

//        Debug.setChannelStatus("Gestures", Inflator.getBoolean("Gestures", false, "Debug"));
//        Debug.setChannelStatus("Slider", Inflator.getBoolean("Slider", false, "Debug"));
//        Debug.setChannelStatus("SliderManager", Inflator.getBoolean("SliderManager", false, "Debug"));
//        Debug.setChannelStatus("SCONController", Inflator.getBoolean("SCONController", false, "Debug"));
//        Debug.setChannelStatus("ThreadedMotorController", Inflator.getBoolean("ThreadedMotorController", false, "Debug"));
//        Debug.setChannelStatus("BeatKeeper", Inflator.getBoolean("BeatKeeper", false, "Debug"));

    }


    public void newPhrase() {

        System.out.println("*** ********************* PLAY");
        isPlaying = true;
        headController.play();
//        Inflator.displayText("Status", "Playing " + behavior);

    }


    public void playDone() {
        System.out.println("*************************** STOP");
        isPlaying = false;
        headController.listen();
//        Inflator.displayText("Status", "Listening");

    }

    public void home()
    {
        headController.home();
    }

    public void start()
    {
        headController.launch();
//        Inflator.displayText("Status", "Listening");

    }

    public void stop()
    {
        headController.stop();
    }

    public void disable()
    {
        headController.disable();
    }

}