package ShimonHeadController;

import ShimonHeadController.innards.NamedObject;

import java.util.Map;

/**
 * Created by Guy Hoffman
 * Date: Nov 16, 2009
 * Time: 6:30:55 PM
 */
public abstract class HeadMotor extends NamedObject {

    String name;
    int axis;

    public HeadMotor() {
        super("Uninitialized ShimonHeadController.HeadMotor");
    }

    public void init(Map<String, Object> mc) {
        setName((String) mc.get("name"));
        name = (String) mc.get("name");
        axis = (Integer) mc.get("axis");
    }
    public abstract void home();
    public abstract boolean isHomed();
    public abstract void zero();

    public abstract void goTo(float pos, float vel, float acc, float dec);

    public abstract void disable();
    public abstract void enable();


}
