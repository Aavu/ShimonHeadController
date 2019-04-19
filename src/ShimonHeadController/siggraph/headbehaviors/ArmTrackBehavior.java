package ShimonHeadController.siggraph.headbehaviors;

import ShimonHeadController.DofManager;
import ShimonHeadController.siggraph.ArmPositionClusterer;

import java.util.TimerTask;

/**
 * Created by Guy Hoffman
 * Date: Dec 3, 2009
 * Time: 1:20:24 PM
 */
public class ArmTrackBehavior extends HeadBehavior {


    protected final ArmPositionClusterer clusterer;
    public static final int DEFAULT_INTERVAL = 1000;
    private int interval = DEFAULT_INTERVAL;


    public ArmTrackBehavior(DofManager dm, ArmPositionClusterer clusterer) {

        super(dm);
        this.clusterer = clusterer;
    }

    public void start()
    {
    	System.out.println("start armtrackingbehavior");
        super.start();

        dm.goTo("neckTilt", -0.4f, 3f);
        dm.goTo("headTilt", -0.8f, 13f);
        dm.goTo("lowerLid", .25f);
        dm.goTo("upperLid", .25f);
        
        timer.schedule(new TimerTask() {

            public void run() {

                if (going)
                {
               
                    float pan = clusterer.getCurrentClusterAngle();
                    System.out.println("**********pan="+pan);
                    if (!clusterer.isPlaying()) {
                    }
                    System.out.println("***********goto basepan: "+pan);
//                    dm.goTo("basePan", pan);
                }
                else {
                    cancel();
                }
            }

        }, 0, interval);


        timer.schedule(new TimerTask() {
            public void run() {

                if (going) {
//                   	System.out.println("start running2");
                    float pan = (float) Math.random() ;
                    dm.goTo("neckPan", pan * .4f - .4f, 2f, 0.1f);

                } else {
                    cancel();
                }
            }

        }, 250, 1250);

    }

    @Override
    public void stop() {
        System.out.println("Stopping Arm Track Behavior");
        dm.goTo("basePan", -.5f, 2f, 0.1f);
        if (!going)
            return;
        super.stop();
        dm.goTo("basePan", -.5f, 2f, 0.1f);
    }
    

    public void setInterval(int interval) {
        this.interval = interval;
    }
}