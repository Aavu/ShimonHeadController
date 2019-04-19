package ShimonHeadController;

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
public class OSCHead implements iLaunchable, iUpdateable {

    private ShimonOSCHeadController headController;

    public void launch() {


        headController = new ShimonOSCHeadController();

//        Inflator.makeTriggerButton("Home", this, "home");
//        Inflator.makeTriggerButton("Start", this, "start");
//        Inflator.makeTriggerButton("Stop", this, "stop");
//        Inflator.makeTriggerButton("Disable", this, "disable");
//        Inflator.displayText("Status", "Startup                                        ");

        Debug.setDebugOn(true);

        // ALWAYS LAST
        Launcher.getLauncher().registerUpdateable(this);

    }


    public void update() {
//        Debug.setChannelStatus("HeadControl", Inflator.getBoolean("HeadControl", false, "Debug"));
//        Debug.setChannelStatus("HeadMotors-Low", Inflator.getBoolean("HeadMotors-Low", false, "Debug"));
    }


    public void home()
    {
//        setStatus("Homing");
        headController.home();
//        setStatus("Homed");
    }
//
//    public void start()
//    {
//        headController.launch();
//        setStatus("Running");
//    }
//
//    public void stop()
//    {
//        headController.stop();
//        setStatus("Stopped");
//    }
//
//    public void disable()
//    {
//        headController.disable();
//        setStatus("Disabled");
//    }

//    public void setStatus(String status)
//    {
//        Inflator.displayText("Status", status);
//    }
}