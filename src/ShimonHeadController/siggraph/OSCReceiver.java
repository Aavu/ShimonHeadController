package ShimonHeadController.siggraph;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.illposed.osc.OSCPortOut;
import ShimonHeadController.innards.util.ShutdownHook;

import java.net.InetAddress;
import java.net.SocketException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by
 * User: hoffman
 * Date: Mar 5, 2009
 */
public abstract class OSCReceiver {

    OSCPortIn receiver;
    private int port;
    private String address;

    protected OSCReceiver(String address, int port) {

        this.port = port;
        this.address = address;

        try {

            receiver = new OSCPortIn(port);
            OSCListener listener = (time, message) -> {
                for(Object m: message.getArguments()) {
                    System.out.print(m+" ");
                }
                System.out.println("\n");
                handleMessage(message.getArguments());
            };
            receiver.addListener(address, listener);
            receiver.startListening();
            System.out.println("Listening on address: " + address + ", port: " + port);

            Runtime.getRuntime().addShutdownHook(new ShutdownHook(){

                public void safeRun() {
                    OSCReceiver.this.stop();
                }
            });

        } catch (SocketException e) {
            System.out.println("Couldn't open listening port on # " + port);
        }
    }

    abstract protected void handleMessage(Object[] args);

    public void stop() {

        receiver.stopListening();
        try {
            OSCPortOut out = new OSCPortOut(InetAddress.getLocalHost(), port);
            out.send(new OSCMessage(address));
        } catch (Exception e) {
            e.printStackTrace();
        }
        receiver.close();
    }
}
