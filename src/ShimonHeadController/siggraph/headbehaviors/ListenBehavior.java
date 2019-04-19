package ShimonHeadController.siggraph.headbehaviors;

import ShimonHeadController.DofManager;

import java.util.TimerTask;

/**
 * Created by Guy Hoffman
 * Date: Dec 3, 2009
 * Time: 1:20:24 PM
 */
public class ListenBehavior extends HeadBehavior {


    private boolean neckUp = false;
    private boolean headUp = false;

    private int blinkRound = 0;

    private int msDelay = 2000;

    public static final float DEFAULT_END_POSITION = -1.1f;
    private float listenPosition = DEFAULT_END_POSITION;
//    public float basePan=-1.f;
    public float neckPan=0;
    public float neckTilt=0;
    public float headTilt=0;
    public boolean breathing=true;
    

    public ListenBehavior(DofManager dm) {
        super(dm);

    }

    public void setPosition(float pos) {
        listenPosition  = pos;
    }
    public void setBasePan(float pos) {
//    	basePan=pos;
    }
    public void setNeckTilt(float pos) {
    	neckTilt=pos;
    }
    public void setNeckPan(float pos) {
    	neckPan=pos;
    }
    public void setHeadTilt(float pos) {
    	headTilt=pos;
    }
    public void setBreathing(float bool){
    	if (bool==1)
    	{
    		breathing = true;
    	}
    	else
    	{
    		breathing = false;
    	}
    }

    public void start()
    {
    	System.out.println("START");
        headUp = false;
        neckUp = false;
        
//        dm.goTo("neckPan", neckPan, 15f);
//        dm.goTo("headTilt", headTilt, 10f);
//        dm.goTo("basePan", basePan);
//        dm.goTo("neckTilt", neckTilt, 10f);
//        dm.goTo("headTilt", headTilt, 10f);
        
        if (going)
            return;
        
        super.start();

//        dm.goTo("neckPan", neckPan, 13f);
//        dm.goTo("lowerLid", .25f);
//        dm.goTo("upperLid", .25f);

        


        neckUp = false;
        headUp = false;

        timer.schedule(new TimerTask() {
            public void run() {

                if (going&&breathing) {

                    if (neckUp) {
                        System.out.println(this + "Neck up");
                        dm.goTo("neckTilt", neckTilt+.25f, 1f, 0.05f);
                    } else {
                        System.out.println(this + "Neck down");
                        dm.goTo("neckTilt", neckTilt, 1f, 0.05f);
                    }
                    neckUp = !neckUp;
                } else {
                    cancel();
                }
            }

        }, 500+ (int)(msDelay * 0.8f), msDelay);

        timer.schedule(new TimerTask() {
            public void run() {

                if (going&&breathing)  {

                    if (headUp) {
                        dm.goTo("headTilt", headTilt, 1f, 0.1f);
                        blink();
                    } else {
                        dm.goTo("headTilt", headTilt+.3f , 1f,0.1f);
                    }
                    headUp = !headUp;
                } else {
                    cancel();
                }
            }

        }, 500, msDelay);

        timer.schedule(new TimerTask() {
            public void run() {

                if (going) {

//                    float pan = (float) Math.random() ;
//                    dm.goTo("neckPan", pan * .1f - .05f, 5f, 0.5f);
                    dm.goTo("neckPan", neckPan, 5f, 0.5f);


                } else {
                    cancel();
                }
            }

        }, 2000, (long) (msDelay * 1.5f));

    }


    private void blink() {

//        if ( blinkRound++ > 3) {
//
//            Thread t = new Thread(new Runnable() {
//                public void run() {
//
//                    dm.goTo("lowerLid", 0.f);
//                    dm.goTo("upperLid", 0.f);
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