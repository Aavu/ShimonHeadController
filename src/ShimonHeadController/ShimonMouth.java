package ShimonHeadController;

import java.util.Scanner;
import ShimonHeadController.innards.util.ShutdownHook;

public class ShimonMouth
{

    // Protocol version
    int PROTOCOL_VERSION                = 1;                   // See which protocol version is used in the Dynamixel

    // Default setting
    int BAUDRATE                        = 57600;
    String DEVICENAME;
    // Control table address
    byte ADDR_MX_TORQUE_ENABLE          = 24;
    byte ADDR_MX_GOAL_POSITION          = 30;
    byte ADDR_MX_MOVING_SPEED           = 32;
    byte ADDR_MX_GOAL_ACCELERATION      = 73;
    byte ADDR_MX_RETURN_DELAY_TIME      = 5;

    byte LEN_MX_GOAL_POSITION           = 2;

    byte UPPER_LIP                      = 1;
    byte LOWER_LIP                      = 2;
    byte TORQUE_ENABLE                  = 1;                   // Value for enabling the torque
    byte TORQUE_DISABLE                 = 0;                   // Value for disabling the torque
    byte RETURN_TIME                    = 0;

    int COMM_SUCCESS                    = 0;                   // Communication Success result value
    int COMM_TX_FAIL                    = -1001;               // Communication Tx Failed

    Dynamixel dynamixel = new Dynamixel();
    int port_num;

    public ShimonMouth() {
        DEVICENAME = "/dev/ttyUSB2";
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ShimonMouth(String comPortName) {
        DEVICENAME = "/dev/"+comPortName;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int init() {

        // Instead of getch
        Scanner scanner = new Scanner(System.in);
        int dxl_comm_result = COMM_TX_FAIL;                        // Communication result
        byte dxl_error = 0;                                        // Dynamixel error

        // Initialize PortHandler Structs
        // Set the port path
        // Get methods and members of PortHandlerLinux or PortHandlerWindows
        port_num = dynamixel.portHandler(DEVICENAME);

        // Initialize PacketHandler Structs
        dynamixel.packetHandler();

        // Open port
        if (dynamixel.openPort(port_num))
        {
            System.out.println("Succeeded to open the dynamixel port!");
        }
        else
        {
            System.out.println("Failed to open the port!");
            System.out.println("Press any key to terminate...");
            scanner.nextLine();
            scanner.close();
            return 0;
        }

        // Set port baudrate
        if (dynamixel.setBaudRate(port_num, BAUDRATE))
        {
            System.out.println("Succeeded to change the baudrate!");
        }
        else
        {
            System.out.println("Failed to change the baudrate!");
            System.out.println("Press any key to terminate...");
            scanner.nextLine();
            scanner.close();
            return 0;
        }

        // Enable Dynamixel Torque
        dynamixel.write1ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_TORQUE_ENABLE, TORQUE_ENABLE);
        verifyConnection();

        dynamixel.write1ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_TORQUE_ENABLE, TORQUE_ENABLE);
        verifyConnection();

        dynamixel.write1ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_RETURN_DELAY_TIME, RETURN_TIME);
        dynamixel.write1ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_RETURN_DELAY_TIME, RETURN_TIME);

        scanner.close();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(){
            public void safeRun(){
                synchronized(this){
                    close();
                    System.out.println("ShutdownHook Done closing");
                }
            }
        });
        return 1;
    }

    public void close() {
        System.out.println("Close port");
        // Disable Dynamixel Torque
        dynamixel.write1ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_TORQUE_ENABLE, TORQUE_DISABLE);
        verifyConnection();
        dynamixel.write1ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_TORQUE_ENABLE, TORQUE_DISABLE);
        verifyConnection();
        // Close port
        dynamixel.closePort(port_num);
    }

    private void verifyConnection() {
        int dxl_comm_result;
        byte dxl_error;

        if ((dxl_comm_result = dynamixel.getLastTxRxResult(port_num, PROTOCOL_VERSION)) != COMM_SUCCESS)
        {
            System.out.println(dynamixel.getTxRxResult(PROTOCOL_VERSION, dxl_comm_result));
        }
        else if ((dxl_error = dynamixel.getLastRxPacketError(port_num, PROTOCOL_VERSION)) != 0)
        {
            System.out.println(dynamixel.getRxPacketError(PROTOCOL_VERSION, dxl_error));
        }
        else
        {
//            System.out.println("Dynamixel has been successfully connected");
        }
    }

    public void mouthGesture(short[] position) {
        System.out.println("Mouth gesture");

        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_GOAL_POSITION, position[0]);
        verifyConnection();
        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_GOAL_POSITION, position[1]);
        verifyConnection();
    }

    public void moveUpperLip(short upperLip) {
        System.out.println("upperLip gesture");

        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_GOAL_POSITION, upperLip);
        verifyConnection();
    }

    public void moveLowerLip(short lowerLip) {
        System.out.println("lowerLip gesture");

        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_GOAL_POSITION, lowerLip);
        verifyConnection();
    }

    public void mouthGesture(short position) {
        System.out.println("Mouth gesture");

        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_GOAL_POSITION, position);
        verifyConnection();
        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_GOAL_POSITION, position);
        verifyConnection();
    }

    public void setAccel(short accel) {
        System.out.println("Set accel");
        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_GOAL_ACCELERATION, accel);
//		verifyConnection();
        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_GOAL_ACCELERATION, accel);
//		verifyConnection();
    }

    public void setVelocity(short vel) {
        System.out.println("Set vel");
        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, UPPER_LIP, ADDR_MX_MOVING_SPEED, vel);
//		verifyConnection();
        dynamixel.write2ByteTxRx(port_num, PROTOCOL_VERSION, LOWER_LIP, ADDR_MX_MOVING_SPEED, vel);
//		verifyConnection();
    }

}