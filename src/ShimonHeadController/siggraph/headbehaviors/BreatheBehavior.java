package ShimonHeadController.siggraph.headbehaviors;

import ShimonHeadController.DofManager;

import java.util.TimerTask;

/**
 * Created by Guy Hoffman
 * Date: Dec 3, 2009
 * Time: 1:20:24 PM
 */
public class BreatheBehavior extends HeadBehavior {


    private boolean neckUp = false;
    private boolean headUp = false;

    private int blinkRound = 0;

    private int msDelay = 2000;

    public static final float DEFAULT_NECK_POSITION = 0f;
    private float neckPosition = DEFAULT_NECK_POSITION;
    private float headPosition = 0;


    public BreatheBehavior(DofManager dm) {
        super(dm);

    }

    public void setPosition(float neck, float head) {
        neckPosition  = neck;
        headPosition = head;
    }
    
    public void setNeckPosition(float neck) {
        neckPosition  = neck;
    }

    public void setHeadPosition(float head) {
        headPosition  = head;
    }

    public void start()
    {

        dm.goTo("neckTilt", neckPosition, 6f);
        dm.goTo("headTilt", headPosition, 30f);

        
        if (going) {
            return;

        }

        super.start();

        neckUp = false;
        headUp = false;

        timer.schedule(new TimerTask() {
            public void run() {
            	System.out.println("going ="+going);
                if (going) {

                    if (neckUp) {
                        System.out.println(this + "Neck up");
                        dm.goTo("neckTilt", neckPosition + .3f, 1f, 0.05f);
                    } else {
                        System.out.println(this + "Neck down");
                        dm.goTo("neckTilt", neckPosition - .0f, 1f, 0.05f);
                    }
                    neckUp = !neckUp;
                } else {
                    cancel();
                }
            }

        }, (int)(msDelay * 0.8f), msDelay);

        timer.schedule(new TimerTask() {
            public void run() {

                if (going)  {

                    if (headUp) {
                        dm.goTo("headTilt", headPosition -.5f, 13f, 0.1f);
//                        dm.goTo("headTilt", headPosition -.2f, 1f, 0.1f);
//                        blink();
                    } else {
                        dm.goTo("headTilt", headPosition -.3f, 1f,0.1f);
                    }
                    headUp = !headUp;
                } else {
                    cancel();
                }
            }

        }, 0, msDelay);

    }


    private void blink() {

//        if ( blinkRound++ > 4) {
//
//            Thread t = new Thread(new Runnable() {
//                public void run() {
//
//                    dm.goTo("lowerLid", 0);
//                    dm.goTo("upperLid", 0);
//
//                    try{Thread.sleep(200);} catch (InterruptedException ignored) {}
//
//                    dm.goTo("lowerLid", .25f);
//                    dm.goTo("upperLid", .25f);
//
//                }
//            });
//
//            t.start();
//            blinkRound = 0;
//        }
    }

}