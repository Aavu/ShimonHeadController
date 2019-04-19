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
public class FasterCopyPlayBehavior extends PlayBehavior {

    private Timer timer = new Timer();

    private int bar = 0;
    private int beat = 0;

    public final static boolean PICKABLE = true;
    private int count;


    public FasterCopyPlayBehavior(iPlayEndListener listener, iPlayBeatListener beatListener, ReceiveOSCFromPython receiver, OSCPlayer player) {
        super(listener, beatListener, receiver, player);
    }

    @Override
    public void play() {

        bar = 0;
        going = true;
        count = 0;

        timer.schedule(new TimerTask() {

            public void run() {
                if (going){
                    if (count++ % (4-bar) == 0)
                        beat();
                }
                else
                    cancel();
            }
        }, 0, 15);
    }

    private void beat() {

        super.beat(beat);

        int note = filterNote(receiver.getMelodyNoteForIndex(beat));
        if (note != -1) {
            System.out.println(beat + "=>" + note);
            player.sendNote(note);
        }
        note = receiver.getNoteForIndex(2, beat);
        if (note != -1) {
            player.sendNote(note-24);
        }
        
        beat++;
        if (beat > 191) {
            beat = 0;
            System.out.println("----------------------------");
            bar++;
        }
    }

    private int filterNote(int note)
    {
        if (bar > 3) {
            end();
            return -1;
        }

        if (note == -1)
            return -1;

        return note;
    }

}