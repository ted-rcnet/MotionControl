/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.AttitudeBean;
import javax.swing.event.EventListenerList;

/**
 *
 * @author EUGI7210
 */
public class AttitudeData {
    private AttitudeBean attitude;
    private final EventListenerList listeners = new EventListenerList();
    
    public AttitudeDataListener[] getListeners() {
        return listeners.getListeners(AttitudeDataListener.class);
    }
    
    public void addAttitudeDataListener (AttitudeDataListener listener) {
        listeners.add(AttitudeDataListener.class, listener);
    }
    
    public void removeAttitudeDataListener (AttitudeDataListener listener) {
        listeners.remove(AttitudeDataListener.class, listener);
    }

    public AttitudeBean getAttitude() {
        return attitude;
    }

    public void setAttitude(AttitudeBean attitude) {
        this.attitude = attitude;
        
        for (AttitudeDataListener listener : getListeners()) {
            listener.newAttitudeData(attitude);
        }
    }
    
}
