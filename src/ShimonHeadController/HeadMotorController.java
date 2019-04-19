package ShimonHeadController;

import ShimonHeadController.innards.debug.Debug;
import ShimonHeadController.innards.util.ResourceLocator;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Guy Hoffman
 * Date: Nov 16, 2009
 * Time: 5:21:50 PM
 */
public class HeadMotorController {

    private Map<String,HeadMotor> motors = new HashMap<String,HeadMotor>(6);
    private CopleyASCIIController asciiController;
    private  ShimonMouth mouthController;

    public HeadMotorController() {

        asciiController = new CopleyASCIIController("copleyASCII");
        mouthController = new ShimonMouth("dynamixel");

    }

    public void init()
    {
        try {
            Yaml yaml = new Yaml();
            String configFile = ResourceLocator.getPathForResource("head-motor-config.yml");
            System.out.println(configFile);
            List<Map<String, Object>> motorsConfigs = yaml.load(new FileInputStream(new File(configFile)));

            for (Map<String, Object> mc : motorsConfigs) {
                motors.put((String) mc.get("name"), initMotor(mc));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void home()
    {
        for (HeadMotor m : motors.values()) {
            System.out.println(m.name + " " + m.axis);
            m.home();
            System.out.println("here");
        }

        boolean done = false;
        while (!done) {
            done = true;
            for (HeadMotor m : motors.values()) {
                if (!m.isHomed())
                    done = false;
            }
            try {Thread.sleep(50);} catch (InterruptedException ignored) {}
        }

        zero();

    }

    public void zero() {
        for (HeadMotor m : motors.values()) {
            m.zero();
            try {Thread.sleep(100);} catch (InterruptedException ignored) {};
        }
    }


    private HeadMotor initMotor(Map<String, Object> mc) {

        HeadMotor m = null;

        if (mc.get("type").equals("HD")) {
            m = new HDHeadMotor(asciiController);
            m.init(mc);
        }

//        else if (mc.get("type").equals("Robotis")) {
//            m = new RobotisHeadMotor(robotisController);
//            m.init(mc);
//        }

        return m;
    }

    public List<String> getMotorNames()
    {

        return Arrays.asList("basePan", "neckTilt", "neckPan", "headTilt", "lips");
    }

    private long lastCommand = 0;
    public void goTo(String dof, float pos, float velocity, float accel)
    {
        goTo(dof, pos, velocity, accel, accel);
    }

    public void goTo(String dof, float pos)
    {
        long now;
        if (dof.equalsIgnoreCase("upperLip")) {
            now = System.currentTimeMillis();
            mouthController.moveUpperLip((short) pos);
            lastCommand = now;
        } else if (dof.equalsIgnoreCase("lowerLip")) {
            now = System.currentTimeMillis();
            mouthController.moveLowerLip((short) pos);
            lastCommand = now;
        }
    }

    public void goTo(String dof, float pos, float velocity, float accel, float decel)
    {
        long now;
        if (dof.equalsIgnoreCase("upperLip")) {
            now = System.currentTimeMillis();
            mouthController.setAccel((short)accel);
            mouthController.setVelocity((short)velocity);
            mouthController.moveUpperLip((short) pos);
        } else if (dof.equalsIgnoreCase("lowerLip")) {
            now = System.currentTimeMillis();
            mouthController.setAccel((short)accel);
            mouthController.setVelocity((short)velocity);
            mouthController.moveLowerLip((short) pos);
        }

        else {
            HeadMotor m = motors.get(dof);
            now = System.currentTimeMillis();
            while (now-lastCommand < 20) {
                try {Thread.sleep(10);} catch (InterruptedException ignored) {}
                now = System.currentTimeMillis();
            }
            m.goTo(pos, velocity, accel, decel);
        }


        lastCommand = now;
    }

    public void set(String parameter, short value) {
        long now;
        if (parameter.equalsIgnoreCase("velocity")) {
            now = System.currentTimeMillis();
            mouthController.setVelocity(value);
            lastCommand = now;
        } else if (parameter.equalsIgnoreCase("acceleration")) {
            now = System.currentTimeMillis();
            mouthController.setAccel(value);
            lastCommand = now;
        }

    }

    public void goTo(String dof, short[] pos, short velocity, short accel)
    {
        if (dof.equalsIgnoreCase("lips")) {
            long now = System.currentTimeMillis();
            mouthController.setAccel(accel);
            mouthController.setVelocity(velocity);
            mouthController.mouthGesture(pos);
            lastCommand = now;
        }
    }

    public void goTo(String dof, short lip, short velocity, short accel)
    {
        if (dof.equalsIgnoreCase("upperLips")) {
            long now = System.currentTimeMillis();
            mouthController.setAccel(accel);
            mouthController.setVelocity(velocity);
            mouthController.moveUpperLip(lip);
            lastCommand = now;
        } else if (dof.equalsIgnoreCase("lowerLips")) {
            long now = System.currentTimeMillis();
            mouthController.setAccel(accel);
            mouthController.setVelocity(velocity);
            mouthController.moveLowerLip(lip);
            lastCommand = now;
        }
    }


    public static void main(String[] args) {

        Debug.setDebugOn(true);
        Debug.setChannelStatus("HeadMotors", true);
        HeadMotorController hmc = new HeadMotorController();
        hmc.init();
    }

    public void disableAll()
    {
        for (HeadMotor m : motors.values()) {
            m.disable();
        }

    }
}
