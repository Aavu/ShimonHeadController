package ShimonHeadController;

import ShimonHeadController.innards.util.ResourceLocator;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Guy Hoffman
 * Date: Nov 18, 2009
 * Time: 12:06:33 PM
 */
public class HeadPoseManager {

    Map<String,Map<String, Object>> poses = new HashMap<String, Map<String, Object>>();
    private DofManager manager;

    public HeadPoseManager(DofManager dm) {
        this.manager = dm;
    }

    public void readPoseFile(String fileName)
    {
        try {
            Yaml yaml = new Yaml();
            String configFile = ResourceLocator.getPathForResource(fileName);
            List<Map<String,Object>> list = (List) yaml.load(new FileInputStream(new File(configFile)));
            for (Map<String,Object> pose : list) {
                poses.put((String)pose.get("name"), pose);
            }
            System.out.println("Read " + poses.size() + "poses");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getPoseNames()
    {
        return poses.keySet();
    }

    public void goToPose(String poseName, int duration) {

        System.out.println("Going to Pose : " + poseName);

        for (String dof : manager.getDofNames()) {
            manager.goTo(dof, (float) (double) (Double) poses.get(poseName).get(dof));
//            try {Thread.sleep(30);} catch (InterruptedException ignored) {}
        }
    }
}
