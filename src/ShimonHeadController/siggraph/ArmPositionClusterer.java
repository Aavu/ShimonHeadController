package ShimonHeadController.siggraph;

/**
 * Created by Guy Hoffman
 * Date: Dec 5, 2009
 * Time: 5:01:09 PM
 */
public class ArmPositionClusterer  {

    private static final int CLOSE = 393;  // mm
    private static final int RANGE = 1385; // mm

    private float currentClusterAngle = 0;

    private int[] decay = new int[4];

    // Positions are in millimeters

    private int getArmCentroid(int ... armPositions) {

        int sum = 0;
        int n = 0;

        int idx = 0;
//        System.out.println("---");
        for (int pos : armPositions) {

//            System.out.println("idx: " + idx + ", pos = " + pos + " decay: " + decay[idx]);
            boolean outLier = true;

            // filter outliers
            if (decay[idx] > 0) {
//                System.out.println("decay = " + decay[idx]);
                for (int other : armPositions) {
                    if (other != pos && Math.abs(other-pos) < CLOSE) {
                        outLier = false;
                        break;
                    }
                }
            }
            if (!outLier) {
//                System.out.println("*** pos = " + pos);
                sum += pos;
                n++;
            }

            idx++;
        }

//        System.out.println("((float) sum / n); = " + ((float) sum / n));

        if (sum == 0)
            return RANGE / 2;
        return (int) ((float) sum / n);
    }


    private float linearToAngle(int position)
    {
        // normalize to -1 -> 1
        float normalized = (float)(position - (RANGE / 2)) / (RANGE / 2);

        // Let's try a linear approximation first - over 2 randians
        return normalized;
    }

    public void updateArmPositions(int ... armPositions)
    {

        currentClusterAngle = linearToAngle(getArmCentroid(armPositions));
//       	System.out.println("update arm positions "+currentClusterAngle);
        for (int i = 0; i < decay.length; i++) {
            decay[i]--;
            if (decay[i] < 0)
                decay[i] = 0;

        }
    }


    public float getCurrentClusterAngle() {
        return currentClusterAngle;
    }

    public void hit(int arm) {
        decay[arm] = 100;
    }

    public boolean isPlaying()
    {
        for (int i = 0; i < decay.length; i++) {
            if (decay[i] > 0)
                return true;
        }

        return false;
    }
}
