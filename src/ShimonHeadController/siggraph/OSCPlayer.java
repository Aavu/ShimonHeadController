package ShimonHeadController.siggraph;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by Guy Hoffman
 * Date: Dec 7, 2009
 * Time: 7:17:03 PM
 */
public class OSCPlayer {

    private OSCPortOut sender;

    public OSCPlayer() {
        try {
            sender = new OSCPortOut(InetAddress.getLocalHost(), 7400);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendNote(int note)
    {
        System.out.println("OSCPlayer note = " + note);
        Object[] args = new Object[2];
        args[0] = note;
        args[1] = 80;
        OSCMessage msg = new OSCMessage("", args);
        try {
            sender.send(msg);
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }


}
