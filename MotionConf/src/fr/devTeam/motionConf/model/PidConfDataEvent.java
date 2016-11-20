/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.PidBean;
import fr.devTeam.motionConf.msp.dao.RcTuningBean;

/**
 *
 * @author EUGI7210
 */
public class PidConfDataEvent {
    private PidBean pids;
    private RcTuningBean rcTuning;

    public PidBean getPids() {
        return pids;
    }

    public void setPids(PidBean pids) {
        this.pids = pids;
    }

    public RcTuningBean getRcTuning() {
        return rcTuning;
    }

    public void setRcTuning(RcTuningBean rcTuning) {
        this.rcTuning = rcTuning;
    }
}
