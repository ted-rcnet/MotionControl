/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.PidBean;
import fr.devTeam.motionConf.msp.dao.RcTuningBean;
import javax.swing.event.EventListenerList;

/**
 *
 * @author EUGI7210
 */
public class PidConfData {
    private PidConfDataEvent ev = new PidConfDataEvent();
    private final EventListenerList listeners = new EventListenerList();
    
    public PidConfDataListener[] getListeners() {
        return listeners.getListeners(PidConfDataListener.class);
    }
    
    public void addBoxDataListener (PidConfDataListener listener) {
        listeners.add(PidConfDataListener.class, listener);
    }
    
    public void removeBoxDataListener (PidConfDataListener listener) {
        listeners.remove(PidConfDataListener.class, listener);
    }
    
    public void setPids (PidBean pids) {
        ev.setPids(pids);
        fireBoxDataEvent();
    }
    
    public void setRcTuning (RcTuningBean rcTuning) {
        ev.setRcTuning(rcTuning);
        fireBoxDataEvent();
    }
    
    public void fireBoxDataEvent () {
        if (ev.getPids()!= null && ev.getRcTuning()!= null) {
            for (PidConfDataListener listener : getListeners()) {
                listener.newPidConfData(ev);
            }
        }
    }
}
