/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf;

import fr.devTeam.motionConf.msp.dao.BoxBean;
import fr.devTeam.motionConf.msp.dao.PidBean;
import fr.devTeam.motionConf.msp.dao.RcTuningBean;
import java.io.Serializable;

/**
 *
 * @author EUGI7210
 */
public class Parameters implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private BoxBean[] boxes;
    private RcTuningBean rcTuning;
    private PidBean pid;
    
    private int curConfigSet;
    private static Parameters INSTANCE = new Parameters();
    
    private Parameters () {}
    

    public static Parameters getInstance() {
        return INSTANCE;
    }

    public static void setInstance(Parameters INSTANCE) {
        Parameters.INSTANCE = INSTANCE;
    }

    public int getCurInstance() {
        return curConfigSet;
    }

    public void setCurInstance(int curConfigSet) {
        this.curConfigSet = curConfigSet;
    }

    public BoxBean[] getBoxes() {
        return boxes;
    }

    public void setBoxes(BoxBean[] boxes) {
        this.boxes = boxes;
    }

    public RcTuningBean getRcTuning() {
        return rcTuning;
    }

    public void setRcTuning(RcTuningBean rcTuning) {
        this.rcTuning = rcTuning;
    }

    public PidBean getPid() {
        return pid;
    }

    public void setPid(PidBean pid) {
        this.pid = pid;
    }
}
