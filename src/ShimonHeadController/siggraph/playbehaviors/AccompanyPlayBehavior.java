package ShimonHeadController.siggraph.playbehaviors;

import ShimonHeadController.siggraph.OSCPlayer;
import ShimonHeadController.siggraph.ReceiveOSCFromPython;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Guy Hoffman
 * Date: Dec 10, 2009
 * Time: 3:07:45 PM
 */
public class AccompanyPlayBehavior extends PlayBehavior {

    private Timer timer = new Timer();

    private int beat;

    private int[] chord = new int[2];
    public AccompanyPlayBehavior(iPlayEndListener listener, iPlayBeatListener beatListener, ReceiveOSCFromPython receiver, OSCPlayer player) {
        super(listener, beatListener, receiver, player);
    }

    @Override
    public void play() {


        beat = 0;
        going = true;


        chord[0] = ReceiveOSCFromPython.zooz2midi[((int) (Math.random() * ReceiveOSCFromPython.zooz2midi.length))];
        chord[1] = ReceiveOSCFromPython.zooz2midi[((int) (Math.random() * ReceiveOSCFromPython.zooz2midi.length))];

        timer.schedule(new TimerTask() {
            public void run() {
                if (going)
                    beat();
                else
                    cancel();
            }
        }, 0, 30);
    }

    private void beat() {

        super.beat(beat);

        int note = receiver.getNoteForIndex(2, beat);
        if (note != -1) {
            System.out.println(beat + "=>" + note);
            player.sendNote(note-12);
        }

        if (beat % 24 == 0) {
            player.sendNote(chord[0]+12);
            player.sendNote(chord[1]+18);
            player.sendNote(chord[0]-6);
        }



        beat++;
        if (beat > 191) {
            System.out.println("----------------------------");
            beat = 0;
        }
    }


}