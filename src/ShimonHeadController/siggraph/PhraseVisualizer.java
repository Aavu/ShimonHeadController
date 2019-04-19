package ShimonHeadController.siggraph;

import ShimonHeadController.siggraph.playbehaviors.PlayBehavior;
//import jesse.visualizer.PointViewer;

import java.util.Map;

/**
 * Created by
 * User: hoffman
 * Date: Apr 2, 2009
 */
//public class PhraseVisualizer extends PointViewer implements PlayBehavior.iPlayBeatListener {
//
////    private TextOverlayPool top;
//    private float[][] colors = new float[][]
//            {
//                    {.5f, .25f, .25f},
//                    {.25f, .5f, .25f},
//                    {.25f, .25f, .5f}
//            };
//    private int beat;
//
//
//    public PhraseVisualizer() {
//        super(256, 0.05f, true);
//    }
//
//
//    public void setup() {
//        super.setup();
//
//        init();
//        glContext.getWindow().setTitle("Phrase Visualizer");
//
//
//    }
//
//    private void init()
//    {
//        for (int i = 0; i < numPoints; i++) {
//            setLocation(i, -1, -1);
//            setColor(i, .1f, .1f, .1f, 1f);
//        }
//        setColor(255, 1f, 1f, 1f, 1f);
//
//    }
//    public void setPhrase(Map<Integer, Integer>[] phrase) {
//
//
//
//        int pt = 0;
//        for (int inst = 0; inst < 3; inst++) {
//            Map<Integer, Integer> m = phrase[inst];
//            for (int b = 0; b < 192; b++) {
//                if (m.containsKey(b)) {
//                    float k = m.get(b);
//                    float x = (float) b / 192f * 2  - 1f;
//                    float y = (float)(k - 50) / 40f * 2 - 1f;
//                    setLocation(pt, x, y);
//                    setColor(pt, colors[inst][0], colors[inst][1], colors[inst][2], 1f);
//                    pt++;
//                }
//            }
//        }
//    }
//
//    public void beat(int beat) {
//
//        this.beat = beat;
//
//
//    }
//
//    public void update() {
//
////        double height = glContext.getWindow().getHeight();
////        top.writeText(""+beat, 5, (int) height/2-25);
//
//        float x = (float) beat / 192f * 2  - 1f;
//        setLocation(255, x, 0);
//
//        super.update();
//
//    }
//}