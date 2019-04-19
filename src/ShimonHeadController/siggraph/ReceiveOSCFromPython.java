package ShimonHeadController.siggraph;

import ShimonHeadController.siggraph.OSCReceiver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guy Hoffman
 * Date: Dec 7, 2009
 * Time: 6:51:05 PM
 */
public class ReceiveOSCFromPython extends OSCReceiver {

    public static int[] zooz2midi =
            {57, 60, 62, 63, 64,
             67, 69, 72, 74, 75,
             76, 79, 81, 84, 86,
             87, 88, 91};

    private Map<Integer, Integer>[] lastPhrase;
    private Map<Integer, Integer> lastBassPhrase;
    private iNewPhraseListener listener;
    private int startClock;

    protected ReceiveOSCFromPython(iNewPhraseListener listener) {
        super("/ShimonHeadController/siggraph", 9001);
        this.listener = listener;
        lastPhrase = new Map[3];
        lastPhrase[0] = new HashMap<Integer, Integer>(23);
        lastPhrase[1] = new HashMap<Integer, Integer>(23);
        lastPhrase[2] = new HashMap<Integer, Integer>(23);
        lastBassPhrase = new HashMap<Integer, Integer>(23);
    }


    protected void handleMessage(Object[] args) {

        if (args[0].equals("clear"))
        {
            lastPhrase[0].clear();
            lastPhrase[1].clear();
            lastBassPhrase.clear();
            lastBassPhrase.putAll(lastPhrase[2]);
            lastPhrase[2].clear();

        }

        else if (args[0].equals("playTogether"))
        {
            listener.playTogether();
        }

        else if (args[0].equals("end"))
        {
            listener.endPlayTogether();
        }

        else if (args[0].equals("startClock"))
        {
            startClock = (Integer) args[1];

        }

        else if (args[0].equals("collDump"))
        {
            if ((Integer)args[3] < 3) {
                lastPhrase[(Integer)args[3]].put((((Integer) args[2] - startClock) + 192) % 192, zooz2midi[(Integer) args[4]]);
                System.out.println("note = " + ((((Integer) args[2] - startClock) + 192) % 192) + " -> " + zooz2midi[(Integer)args[4]]);
            }
        }

        else if (args[0].equals("scale"))
        {
            // Make sure the bass is never empty
            if (lastPhrase[2].isEmpty())
                lastPhrase[2].putAll(lastBassPhrase);
            listener.newPhrase();
        }
    }


    public int getNoteForIndex(int inst, int index)
    {
        if (lastPhrase[inst].containsKey(index))
            return lastPhrase[inst].get(index);
        else
            return -1;
    }

    public int getMelodyNoteForIndex(int index)
    {
        for (int i = 0; i < 2; i++) {
            if (lastPhrase[i].containsKey(index))
                return lastPhrase[i].get(index);
        }
        return -1;
    }

    public int getNoteForIndex(int index)
    {
        for (int i = 0; i < 3; i++) {
            if (lastPhrase[i].containsKey(index))
                return lastPhrase[i].get(index);
        }
        return -1;
    }

    public Map<Integer, Integer>[] getLastPhrase() {
        return lastPhrase;
    }

    public interface iNewPhraseListener
    {
        public void newPhrase();

        void playTogether();
        void endPlayTogether();

    }
}
