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
public class FasterSegmentPlayBehavior extends PlayBehavior {

    private Timer timer = new Timer();

    private int bar = 0;
    private int beat = 0;

    public final static boolean PICKABLE = true;
    private int count;


    public FasterSegmentPlayBehavior(iPlayEndListener listener, iPlayBeatListener beatListener, ReceiveOSCFromPython receiver, OSCPlayer player) {
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
                    if (count++ % (3-bar) == 0)
                        beat();
                }
                else
                    cancel();
            }
        }, 0, 10);
    }

    private void beat() {

        super.beat(beat);

        int note;

        if (bar == 0) {
            note = filterNote(receiver.getMelodyNoteForIndex(beat % 48));
            System.out.println("Bar " + bar + " : " + (beat % 48));
        }
        else if (bar == 1) {
            note = filterNote(receiver.getMelodyNoteForIndex(beat % 96));
            System.out.println("Bar " + bar + " : " + (beat % 96));
        }
        else {
            note = filterNote(receiver.getMelodyNoteForIndex(beat));
            System.out.println("Bar " + bar + " : " + beat);
        }

        if (Math.random() < 0.25f)
            note = -1;

        if (note != -1) {
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
            if (bar > 2)
                end();
        }
    }

    private int filterNote(int note)
    {
        if (bar > 2) {
            end();
            return -1;
        }

        if (note == -1)
            return -1;

        return note;
    }

}