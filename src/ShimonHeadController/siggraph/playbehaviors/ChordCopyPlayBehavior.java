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
public class ChordCopyPlayBehavior extends PlayBehavior {

    private Timer timer = new Timer();

    private int beat;

    public ChordCopyPlayBehavior(iPlayEndListener listener, iPlayBeatListener beatListener, ReceiveOSCFromPython receiver, OSCPlayer player) {
        super(listener, beatListener, receiver, player);
    }

    @Override
    public void play() {


        beat = 0;
        going = true;
        timer.schedule(new TimerTask() {
            public void run() {
                if (going)
                    beat();
                else
                    cancel();
            }
        }, 0, 40);
    }

    private void beat() {

        super.beat(beat);

        int note = receiver.getMelodyNoteForIndex(beat);
        if (note != -1) {
            player.sendNote(note);
            player.sendNote(note+12);
            player.sendNote(note-6);
        }

        note = receiver.getNoteForIndex(2, beat);
        if (note != -1) {
            player.sendNote(note-24);
        }


        beat++;
        if (beat > 191) {
            System.out.println("----------------------------");
            end();
        }
    }


}