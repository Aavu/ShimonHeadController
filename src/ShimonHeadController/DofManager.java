package ShimonHeadController;

import ShimonHeadController.innards.NamedObject;
import ShimonHeadController.innards.util.ResourceLocator;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.*;

/**
 * Created by Guy Hoffman
 * Date: Dec 2, 2009
 * Time: 5:09:14 PM
 */
public class DofManager {

    private Map<String,Dof> dofs = new HashMap<String,Dof>(6);
    private HeadMotorController hmc;

    public DofManager(HeadMotorController hmc) {

        this.hmc = hmc;
        loadDofs();

    }

    private void loadDofs() {
        try {
            Yaml yaml = new Yaml();
            String configFile = ResourceLocator.getPathForResource("head-dof-limits.yml");
            List<Map<String, Object>> motorsConfigs = yaml.load(new FileInputStream(new File(configFile)));

            for (Map<String, Object> mc : motorsConfigs) {
                dofs.put((String) mc.get("name"), new Dof(mc));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    public List<String> getDofNames () {

        List<String> l = new ArrayList<String>(dofs.size());
//        for (String d : dofs.keySet()) {
//            l.add(d);
//        }
        l.addAll(dofs.keySet());
        return l;
    }

    public Collection<Dof> getDofs() {
        return dofs.values();
    }

    public Dof getDofByName(String name) {
        return dofs.get(name);
    }

    public void reload() {
        dofs.clear();
        loadDofs();
    }

    public class Dof extends NamedObject{

        boolean hasMin = false;
        boolean hasMax = false;
        float min, max, maxVel, maxAccel;

        public Dof(Map<String, Object> mc) {
            super((String) mc.get("name"));

            if (mc.containsKey("min")) {
                hasMin = true;
                min = (float) (double) (Double) mc.get("min");
            }

            if (mc.containsKey("max")) {
                hasMax = true;
                max = (float) (double) (Double) mc.get("max");
            }

            maxVel = (float) (double) (Double) mc.get("maxVel");
            maxAccel = (float) (double) (Double) mc.get("maxAcc");

        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public float getMaxVel() {
            return maxVel;
        }

        public float getMaxAccel() {
            return maxAccel;
        }

        public float adjustPos(float pos) {
            float adjusted = pos;
            if (hasMin) {
                adjusted = Math.max(min, adjusted);
                System.out.println("pos = " + pos);
                System.out.println("adjusted = " + adjusted);
            }
            if (hasMax) {
                adjusted = Math.min(max, adjusted);
                System.out.println("pos = " + pos);
                System.out.println("adjusted = " + adjusted);
            }
            return adjusted;
        }
    }

    public void goTo(String dofName, float pos)
    {
       	System.out.println("goto position "+pos+" dofname "+dofName);
        Dof d = dofs.get(dofName);
        hmc.goTo(dofName, d.adjustPos(pos), d.getMaxVel(), d.getMaxAccel());
    }
    
    public void goTo(String dofName, float pos, float vel)
    {
        Dof d = dofs.get(dofName);
        hmc.goTo(dofName, d.adjustPos(pos), Math.min(vel, d.getMaxVel()), d.getMaxAccel());
    }

    public void goTo(String dofName, float pos, float vel, float accel)
    {
        Dof d = dofs.get(dofName);
        hmc.goTo(dofName, d.adjustPos(pos), Math.min(vel, d.getMaxVel()), Math.min(accel, d.getMaxAccel()));
    }

    public void goTo(String dofName, short[] pos, float vel, float accel)
    {
        Dof d = dofs.get(dofName);
        hmc.goTo(dofName, pos, (short)Math.min(vel, d.getMaxVel()), (short)Math.min(accel, d.getMaxAccel()));
    }

    public void goTo(String dofName, short[] pos, short vel, short accel)
    {
//        Dof d = dofs.get(dofName);
//        System.out.println(d.getName());
        hmc.goTo(dofName, pos, vel, accel);
//        hmc.goTo(dofName, d.adjustPos(pos), Math.min(vel, d.getMaxVel()), Math.min(accel, d.getMaxAccel()));
    }

    public void goTo(String dofName, short pos, short vel, short accel)
    {
        Dof d = dofs.get(dofName);
//        hmc.goTo(dofName, pos, vel, accel);
        hmc.goTo(dofName, d.adjustPos(pos), Math.min(vel, d.getMaxVel()), Math.min(accel, d.getMaxAccel()));
    }

    public void goTo(String dofName, short pos)
    {
        Dof d = dofs.get(dofName);
//        hmc.goTo(dofName, pos, vel, accel);
        hmc.goTo(dofName, d.adjustPos(pos));
    }

    public void goTo(String dofName, float pos, float vel, float accel, float decel)
    {
        Dof d = dofs.get(dofName);
        hmc.goTo(dofName, d.adjustPos(pos), Math.min(vel, d.getMaxVel()), Math.min(accel, d.getMaxAccel()), Math.min(decel, d.getMaxAccel()));
    }

    public void set(String parameter, short value) {
        hmc.set(parameter, value);
    }

}
