package ShimonHeadController;

/**
 * Created by Guy Hoffman
 * Date: Jan 8, 2010
 * Time: 2:34:53 PM
 */
public interface iHeadController {

    public void listen(float where);
    public void breathe(float neck, float head);
    public void track(boolean doTrack, int interval);
    public void headBang(boolean doBang);
    public void beat(int length);

    public void updateArmPositions(int[] positions);
    public void strike(int striker);

}
