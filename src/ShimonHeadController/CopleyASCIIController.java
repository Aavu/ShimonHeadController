package ShimonHeadController;

import com.fazecast.jSerialComm.*;
import ShimonHeadController.innards.debug.Debug;
import ShimonHeadController.innards.util.ShutdownHook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 * User: hoffman
 * Date: Mar 18, 2009
 */
public class CopleyASCIIController {

    private boolean comPortInitialized;
    private SerialPort port;

    private long sentTime;

    private byte [] readBuffer = new byte [1024];

    private static final int PATIENCE = 55; // ms

    private class Values {
        public int vel, accel, decel, mode, enable;

        private Values() {
            this.vel = Integer.MAX_VALUE;
            this.accel = Integer.MAX_VALUE;
            this.decel = Integer.MAX_VALUE;
            this.mode = Integer.MAX_VALUE;
            this.enable = Integer.MAX_VALUE;
        }
    }

    private Map<Integer, Values> lastValues = new HashMap<Integer, Values>();

    public enum Error {
        UNKNOWN(-1, "Unknown Error"),
        TOO_MUCH_DATA (1, "Too much data passed with command"),
        UNKNOWN_COMMAND(3, "Unknown command code"),
        OUT_OF_RANGE(10, "Data value out of range"),
        ILLEGAL_MOVE(18, "Illegal attempt to start a move while currently moving"),
        CAN_COMM (32,"CAN Network communications failure"),
        ASCII_PARSE (33, "ASCII command parsing error"),
        ;

        private int errnum;
        private final String cause;

        Error(int errnum, String cause) {
            this.errnum = errnum;
            this.cause = cause;
        }

        public static Error findError(int errnum)
        {
            for (Error e : Error.values()) {
                if (e.errnum == errnum) {
                    return e;
                }
            }

            Error e = UNKNOWN;
            e.errnum = errnum;
            return e;
        }

        public String toString() {
            return String.format("**** COPLEY **** [%d] %s", errnum, cause);
        }
    }

    public class CopleyError extends RuntimeException {

        private Error e;
        public CopleyError(int errnum) {
            super(Error.findError(errnum).toString());
            e = Error.findError(errnum);
        }
    }

    public CopleyASCIIController(String comPortName) {

        comPortInitialized = false;
        port = SerialPort.getCommPort(comPortName);
        port.setBaudRate(9600);
        try {
            port.openPort();
            comPortInitialized = true;
            try {
                System.out.println("Setting the Baud Rate to 115200");
                byte[] buffer = "s r0x90 115200\r".getBytes();
                port.writeBytes(buffer, buffer.length);
                try {Thread.sleep(150);} catch (InterruptedException ignored) {}
            } catch (RuntimeException e) {
                System.out.println("This might mean we're already on high baudrate. Shouldn't be a problem.");
            }
            port.setBaudRate(115200);
            System.out.println("Baud Rate set to 115200");
            System.out.println("Resp: [" + readResponse()+ "]");

            Runtime.getRuntime().addShutdownHook(new ShutdownHook(){
                public void safeRun(){
                    synchronized(this){
                        System.out.println("Resetting Baud rate");
                        byte[] buffer = "s r0x90 9600\r".getBytes();
                        port.writeBytes(buffer, buffer.length);
                        System.out.println("ShutdownHook Done closing");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void setTarget(int axis, int position, int vMax, int accel, int decel)
    {
        set(axis, 0xca, position); // Set the position command to encPos counts.

        if (lastValues.containsKey(axis)) {
            Values v = lastValues.get(axis);
            if (v.vel != vMax) {
                Debug.doReport("HeadMotors", "Vel is different, can't cache");
                set(axis, 0xcb, vMax);  // Set maximum velocity to vMax counts/second.
                v.vel = vMax;
                lastValues.put(axis, v);
            }
            if (v.accel != accel) {
                Debug.doReport("HeadMotors", "Accel is different, can't cache");
                set(axis, 0xcc, accel);    // Set maximum acceleration to accel counts/second2.
                v.accel = accel;
                lastValues.put(axis, v);
            }
            if (v.decel != decel) {
                Debug.doReport("HeadMotors", "Decel is different, can't cache");
                set(axis, 0xcd, decel);    // Set maximum deceleration to accel counts/second2.
                v.decel = decel;
                lastValues.put(axis, v);
            }
            if (v.enable != 21) {
                Debug.doReport("HeadMotors", "Enable is different, can't cache");
                set(axis, 0x24, 21);    // Enable the amplifier in Programmed Position (Trajectory Generator) Mode.
                v.enable = 21;
                lastValues.put(axis, v);
            }
        } else {
            Debug.doReport("HeadMotors", "No cache, sending all values");
            set(axis, 0xc8, 0);   // Set the trajectory generator to absolute move, trapezoidal profile
            Values v = new Values();
            v.mode = 0;
            set(axis, 0xcb, vMax);  // Set maximum velocity to vMax counts/second.
            v.vel = vMax;
            set(axis, 0xcc, accel);    // Set maximum acceleration to accel counts/second2.
            v.accel = accel;
            set(axis, 0xcd, decel);    // Set maximum deceleration to decel counts/second2.
            v.decel = decel;
            set(axis, 0x24, 21);    // Enable the amplifier in Programmed Position (Trajectory Generator) Mode.
            v.enable = 21;
            lastValues.put(axis, v);
        }
        trajectory(axis, 1);    // Execute the move.
    }


    public void servoOn(int axis)
    {
        if (lastValues.containsKey(axis)) {
            Values v = lastValues.get(axis);
            if (v.enable != 21) {
                set(axis, 0x24, 21);    // Enable the amplifier in Programmed Position (Trajectory Generator) Mode.
                v.enable = 21;
                lastValues.put(axis, v);
            }
        } else {
            set(axis, 0x24, 21);    // Enable the amplifier in Programmed Position (Trajectory Generator) Mode.
            Values v = new Values();
            v.enable = 21;
            lastValues.put(axis, v);
        }
    }

    public void servoOff(int axis)
    {
        if (lastValues.containsKey(axis)) {
            Values v = lastValues.get(axis);
            if (v.enable != 0) {
                set(axis, 0x24, 0);    // Enable the amplifier in Programmed Position (Trajectory Generator) Mode.
                v.enable = 0;
                lastValues.put(axis, v);
            }
        } else {
            set(axis, 0x24, 0);    // Enable the amplifier in Programmed Position (Trajectory Generator) Mode.
            Values v = new Values();
            v.enable = 0;
            lastValues.put(axis, v);
        }
    }


    /**
     *
     * @param axis
     * @return -1 : don't know  0: no alarm  1: alarm
     */
    public int queryAlarm(int axis)
    {
//        String msg = String.format("%02d0390020001", axis+1);
//        String resp = sendMessage(msg, true, 15);
//        if (resp == null)
//            return -1;
        return 0;
//        return resp.substring(7, 11).equals("0000") ? 0 : 1;
    }

    public void resetAlarm(int axis)
    {
//        String msg = String.format("%02d050407FF00", axis+1);
//        sendMessage(msg);
    }

    /**
     *
     * @param axis
     * @return -1 : don't know  0: not home yet  1: home end
     */
    public int queryHomeEnd(int axis)
    {
        int s = queryStatus(axis);
        return s == -1 ? -1 : (s & 4096) >> 12;
    }

    /**
     *
     * @param axis
     * @return -1 : don't know  0: not home yet  1: home end
     */
    public int queryStatus(int axis)
    {
        return get(axis, 0xc9);
    }

    /**
     *
     * @param axis
     * @return -1 : don't know  or position (>= 0)
     */
    public int queryPosition(int axis) {

//        String msg = String.format("%02d0390000002", axis+1);
//        String resp = sendMessage(msg, true, 19);
//        if (resp == null)
//            return -1;
//
//        return Integer.parseInt(resp.substring(7,15),16) / 100;
        return 0;
    }

    public void homeHardStop(int axis) {
        home(axis, 532);
    }

    public void homeManual(int axis) {
        home(axis, 512);
    }

    public void home(int axis, int method) {

        System.out.println("Homing axis " + axis + " with method " + method + " ...");

        set(axis, 0xc2, method);   // Sets the homing method to use the negative hard stop as home
        set(axis, 0xc4, 80000); // Sets the slow velocity to 4000 counts/second.
        set(axis, 0xc6, 1000);  // Sets the home offset to 1000 counts.
        set(axis, 0x24, 21);    // Enables the amplifier in programmed position mode.
        trajectory(axis, 2);    // Starts the homing sequence.
    }


    private int get(int axis, int var) throws RuntimeException//YINGJIA MAY NEED THIS
    {
        String cmd = String.format("%d g r0x%02x\r", axis, var);
        String resp = command(cmd);

        return Integer.parseInt(resp.trim().substring(2));
    }

    private void set(int axis, int var, int value) throws RuntimeException
    {
        String cmd = String.format("%d s r0x%02x %d\r", axis, var, value);
        String resp = command(cmd);

        if (resp.equals("block\r")) {
            System.out.println("Blocked sent message");
            return;
        }

        assert (resp.equals("ok\r"));
    }

    private void reset(int axis) throws RuntimeException
    {
        String cmd = String.format("%d r\r", axis);
        try {
            String resp = command(cmd);
        }
        catch (CopleyError e) {
            if (e.e == Error.CAN_COMM) {
                // Ignore as per manufacturer's instructions
            } else {
                throw e;
            }
        }
    }

    private void trajectory(int axis, int kind) throws RuntimeException
    {
        String cmd = String.format("%d t %d\r", axis, kind);
        String resp = command(cmd);

        assert (resp.equals("ok\r"));
    }

    private String command(String cmd) throws RuntimeException
    {
        String resp = sendMessage(cmd);

        if (resp == null)
            throw new RuntimeException("Message sending failed");

        if (resp.charAt(0) == 'e') {
            int errnum = Integer.parseInt(resp.substring(2));
            throw new RuntimeException(Error.findError(errnum).toString() + "[" +  cmd + "]");
        }

        return resp;
    }

    private volatile boolean isSending = false;

    private String sendMessage (String message)//YINGJIA MAY NEED THIS
    {
        if (isSending)
            return "block\r";

        isSending = true;
        Debug.doReport("HeadMotors-Low", "Sending [" + message.trim() + "]");

        byte[] msg = message.getBytes();

        if (comPortInitialized) {
            try {

                // flush input
                port.readBytes(readBuffer, readBuffer.length);
//                comPort.flush();
//                comPort.read(readBuffer, 1024);

                sentTime = System.currentTimeMillis();
                port.writeBytes(msg, msg.length);
//                port.write(msg);
//                comPort.write(msg, msg.length);

                String r = readResponse();
                isSending = false;
                return r;


            } catch (IOException e) {
                System.out.println("Couldn't send Serial message");
                e.printStackTrace();
                isSending = false;
            }
        }

        isSending = false;

        return null;
    }

    private String readResponse() throws IOException {

        int readLength = 0;
        boolean done = false;
        byte[] b = new byte[1];

        long now = System.currentTimeMillis();


        readBuffer[0] = 0;

        while (!done && (now-sentTime) < PATIENCE) {
            int n = port.readBytes(b, 1);
//            int n = b[0] == (byte)-1 ? -1 : 1;
//            int n = comPort.read(b, 1);
            if (n > 0) {
                if (b[0] == '\r') {
                    done = true;
                } else {
                    readBuffer[readLength++] = b[0];
                }
            }
            now = System.currentTimeMillis();
        }

        String rv = new String(readBuffer).substring(0, readLength).trim();

        if (done) {
            Debug.doReport("HeadMotors-Low", "Read succeeded (" + readLength + " bytes ): ["+ rv +  "] \n*** Total time: " + (now - sentTime));
            return rv;
        } else {
            Debug.doReport("HeadMotors-Low", "Read failed (" + readLength + " bytes ): [" + rv +  "] \n*** Total time: " + (now - sentTime));
            return null;
        }
    }

}