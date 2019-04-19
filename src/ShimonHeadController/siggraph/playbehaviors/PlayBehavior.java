package ShimonHeadController.siggraph.playbehaviors;

import ShimonHeadController.siggraph.OSCPlayer;
import ShimonHeadController.siggraph.ReceiveOSCFromPython;

/**
 * Created by Guy Hoffman
 * Date: Dec 10, 2009
 * Time: 3:04:49 PM
 */
public abstract class PlayBehavior {

    private iPlayEndListener endListener;
    private iPlayBeatListener beatListener;
    protected ReceiveOSCFromPython receiver;
    protected OSCPlayer player;
    protected volatile boolean going;

    protected PlayBehavior(iPlayEndListener endListener, iPlayBeatListener beatListener, ReceiveOSCFromPython receiver, OSCPlayer player) {
        this.endListener = endListener;
        this.beatListener = beatListener;
        this.receiver = receiver;
        this.player = player;
    }

    public abstract void play ();

    public void beat (int index)
    {
        beatListener.beat(index);
    }

    public void end() {
        going = false;
        endListener.playDone();
    }

    public interface iPlayEndListener {
        public void playDone();
    }

    public interface iPlayBeatListener {
        public void beat(int index);
    }

}
