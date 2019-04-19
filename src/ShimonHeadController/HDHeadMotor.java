package ShimonHeadController;

import ShimonHeadController.innards.debug.Debug;

import java.util.Map;

/**
 * Created by Guy Hoffman
 * Date: Nov 16, 2009
 * Time: 6:31:41 PM
 */
public class HDHeadMotor extends HeadMotor {

    private CopleyASCIIController controller;

    private int zero, min, max, scale;
    private String homing;

    public HDHeadMotor(CopleyASCIIController asciiController) {
        controller = asciiController;
    }

    @Override
    public void init(Map<String, Object> mc) {
        super.init(mc);
        zero = (Integer)mc.get("zero");
        min = (mc.containsKey("min")) ? (Integer)mc.get("min") : Integer.MIN_VALUE;
        max = (mc.containsKey("max")) ? (Integer)mc.get("max") : Integer.MAX_VALUE;
        scale = (Integer)mc.get("scale");
        homing = (String)mc.get("homing");
    }

    public void home()
    {
        // Home
        if (homing.equals("hardstop")) {

            controller.homeHardStop(axis);
        }
        else if (homing.equals("manual"))
        {
            controller.homeManual(axis);
        }
        else throw new RuntimeException(toString() + ": invalid homing " + homing);

    }

    public boolean isHomed() {
        return controller.queryHomeEnd(axis) == 1;
    }

    public void zero() {
        goTo (0, 2f, .5f, .5f);
    }

    public void goTo(float pos, float vel, float acc, float dec) {

        int encPos = (int)(pos * scale) + zero;

        if (encPos < min) {
            System.out.println("Capped " + encPos + " @ min -> " + min );
            encPos = min ;
        }
        if (encPos > max) {
            System.out.println("Capped " + encPos + " @ max -> " + max );
            encPos = max;
        }

        Debug.doReport("HeadControl", "Moving axis " + axis + "("+getName()+") to " + encPos + " (" + (int)(vel*scale) + "/" + (int)(acc*scale )+ "," + (int)(dec*scale) +")");
//        Thread.dumpStack();

        try {
            controller.setTarget(axis, encPos, (int)(vel*scale), (int)(acc*scale), (int)(dec*scale));
        } catch (RuntimeException e ) {
            e.printStackTrace();
        }
        
    }

    public void disable() {
        controller.servoOff(axis);
    }

    public void enable() {
        controller.servoOn(axis);
    }


}
