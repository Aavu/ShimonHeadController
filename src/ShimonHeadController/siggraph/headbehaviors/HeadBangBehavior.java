package ShimonHeadController.siggraph.headbehaviors;

import ShimonHeadController.DofManager;

/**
 * Created by Guy Hoffman
 * Date: Dec 3, 2009
 * Time: 1:20:24 PM
 */
public class HeadBangBehavior extends HeadBehavior {


    private boolean neckUp = false;
    private boolean headUp = false;

    public void setNeckOffset(float neckOffset) {
//        this.neckOffset = neckOffset;
    }

    private float neckOffset = -.2f;
    private int blinkRound = 0;
    private int msDelay;
    private static final int DEFAULT_DELAY = 480;
    private float scale = 1f;
    private float speed = 1f;
    private boolean neckPanEnabled = true;

    public HeadBangBehavior(DofManager dm) {
        super(dm);
        this.msDelay = DEFAULT_DELAY;

    }
//
//    public void start()
//    {
////        dm.goTo("neckTilt",  0, 4f * speed);
//        if (going)
//            return;
//        
//        super.start();
//
//        neckUp = false;
//        headUp = false;
//
//        dm.goTo("lowerLid", .25f);
//        dm.goTo("upperLid", .25f);
//        
//        timer.schedule(new TimerTask() {
//            public void run() {
//
//                if (going) {
//
//                    if (neckUp) {
////                        dm.goTo("neckTilt", neckOffset + 0, 4f * speed);
//                        dm.goTo("neckTilt",  0, 4f * speed);
//                    } else {
////                        dm.goTo("neckTilt", neckOffset-(0.4f * scale), 13f * speed);
//                        dm.goTo("neckTilt", -(0.4f * scale), 13f * speed);
//                    }
//                    neckUp = !neckUp;
//                } else {
//                    cancel();
//                }
//            }
//
//        }, (int)(msDelay * 0.8f), msDelay);
// 
//        timer.schedule(new TimerTask() {
//            public void run() {
//
//                if (going)  {
//
//                    if (headUp) {
//                        dm.goTo("headTilt", (-0.3f * scale), 50f * speed);
//                        blink();
//                    } else {
//                        dm.goTo("headTilt", (.2f * scale), 30f * speed);
//                    }
//                    headUp = !headUp;
//                } else {
//                    cancel();
//                }
//            }
//
//        }, 0, msDelay);
//
//        timer.schedule(new TimerTask() {
//            public void run() {
//
//                if (going) {
//
//                    if (neckPanEnabled) {
//                        float pan = (float) Math.random() ;
////                        dm.goTo("neckPan", (pan -.5f) * scale, 13f);
//                    } else {
////                        dm.goTo("neckPan", 0, 13f);
//                    }
//
//                } else {
//                    cancel();
//                }
//            }
//
//        }, 0, msDelay * 4);
//
//    }
//    
//
//    public void setDelay(int msDelay)
//    {
//        this.msDelay = msDelay;
//    }
//
//    public void setScale(float scale)
//    {
//        this.scale = scale;
//    }
//    public void setSpeed(float scale)
//    {
//        this.speed= scale;
//    }
//
//    @Override
//    public void stop() {
//        dm.goTo("headTilt", 0, 51f, 1f);
//        try{Thread.sleep(500);} catch(InterruptedException ignored){}
//        dm.goTo("headTilt", 0, 52f, 1f);
//
//        super.stop();
//
//    }
//
//    private void blink() {
//
////        if ( blinkRound++ > 4) {
////
////        Thread t = new Thread(new Runnable() {
////            public void run() {
////
////                dm.goTo("lowerLid", 0);
////                dm.goTo("upperLid", 0);
////
////                try{Thread.sleep(200);} catch (InterruptedException ignored) {}
////
////                dm.goTo("lowerLid", .25f);
////                dm.goTo("upperLid", .25f);
////
////            }
////        });
////
////        t.start();
////            blinkRound = 0;
////        }
//    }
//
//
//    public void enableNeckPan(boolean b) {
////        neckPanEnabled = b;
//    }
}
