package ShimonHeadController.siggraph.headbehaviors;

import ShimonHeadController.DofManager;

import java.util.TimerTask;

/**
 * Created by Guy Hoffman
 * Date: Dec 3, 2009
 * Time: 1:20:24 PM
 */
public class TriggeredHeadBangBehavior extends HeadBehavior {


    public void setNeckOffset(float neckOffset) {
        this.neckOffset = neckOffset;
    }

    private float neckOffset = .1f;
    private int blinkRound = 0;
    private int msDelay;
    private static final int DEFAULT_DELAY = 480;

    private boolean neckPanEnabled = true;

    public TriggeredHeadBangBehavior(DofManager dm) {
        super(dm);
        this.msDelay = DEFAULT_DELAY;
    }

    public void start()
    {
        super.start();

//        dm.goTo("lowerLid", .25f);
//        dm.goTo("upperLid", .25f);
        dm.goTo("neckTilt", neckOffset - 0.1f);



        timer.schedule(new TimerTask() {
            public void run() {

                if (going) {

                    if (neckPanEnabled) {
                        float pan = (float) Math.random() ;
//                        dm.goTo("neckPan", (pan -.5f), 13f);
                    } else {
//                        dm.goTo("neckPan", 0, 13f);
                    }

                } else {
                    cancel();
                }
            }

        }, 0, msDelay * 4);

    }

    private long lastBeat = 0;
    public void beat(int length)
    {
    	System.out.println("********* neckOffset = "+neckOffset);

        if (System.currentTimeMillis() - lastBeat < 500) {
            return;
        }

        while (length < 500) {
            length *=2;
        }

        lastBeat = System.currentTimeMillis();

        if (going)
        {
            // Up
            dm.goTo("neckTilt", neckOffset + .2f, 4f, 0.5f, 0.2f);

            timer.schedule(new TimerTask() {
                public void run() {
                    // Down
                    dm.goTo("neckTilt", neckOffset - 0.2f, 13f, 0.5f, 0.5f);
                }
            }, length / 2);

            timer.schedule(new TimerTask() {
                public void run() {
                    // Up
                    dm.goTo("headTilt", -0.1f, 30f, 1f, .5f);
                }
            }, length / 5);

            timer.schedule(new TimerTask() {
                public void run() {
                    // Down
                    dm.goTo("headTilt", -0.4f, 50f, 1f, 1f);
                    blink();
                }
            }, length / 2 + length / 5);
        }
    }

    public void setDelay(int msDelay)
    {
        this.msDelay = msDelay;
    }


    @Override
    public void stop() {

//        dm.goTo("headTilt", 0, 51f, 1f);
//        try{Thread.sleep(500);} catch(InterruptedException ignored){}
//        dm.goTo("headTilt", 0, 52f, 1f);

        dm.goTo("neckTilt", neckOffset );

        super.stop();

    }

    private void blink() {

//        if ( blinkRound++ > 4) {
//
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//
//                dm.goTo("lowerLid", 0);
//                dm.goTo("upperLid", 0);
//
//                try{Thread.sleep(200);} catch (InterruptedException ignored) {}
//
//                dm.goTo("lowerLid", .25f);
//                dm.goTo("upperLid", .25f);
//
//            }
//        });
//
//        t.start();
//            blinkRound = 0;
//        }
    }


    public void enableNeckPan(boolean b) {
        neckPanEnabled = b;
    }
}