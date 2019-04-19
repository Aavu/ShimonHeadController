package ShimonHeadController.siggraph.headbehaviors;

import java.util.Timer;
import ShimonHeadController.DofManager;

/**
 * Created by Guy Hoffman
 * Date: Dec 10, 2009
 * Time: 3:04:49 PM
 */
public abstract class HeadBehavior {

    protected final DofManager dm;
    protected Timer timer;

    protected volatile boolean going;

    public HeadBehavior(DofManager dm)
    {
        this.dm = dm;
        this.timer = new Timer();
        this.going = false;
    }

    public void start()
    {
        going = true;
    }
    public void stop ()
    {
        going = false;
    }


}