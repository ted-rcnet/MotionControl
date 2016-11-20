/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.BoxBean;
import fr.devTeam.motionConf.msp.dao.BoxIdBean;
import fr.devTeam.motionConf.msp.dao.BoxNamesBean;
import javax.swing.event.EventListenerList;

/**
 *
 * @author fanny
 */
public class BoxData {
    private BoxDataEvent ev = new BoxDataEvent();
    private final EventListenerList listeners = new EventListenerList();
    
    public BoxDataListener[] getListeners() {
        return listeners.getListeners(BoxDataListener.class);
    }
    
    public void addBoxDataListener (BoxDataListener listener) {
        listeners.add(BoxDataListener.class, listener);
    }
    
    public void removeBoxDataListener (BoxDataListener listener) {
        listeners.remove(BoxDataListener.class, listener);
    }

    public void setBox (BoxBean box) {
        ev.setBox(box);
        fireBoxDataEvent();
    }
    
    public void setBoxId (BoxIdBean boxId) {
        ev.setBoxId(boxId);
        fireBoxDataEvent();
    }
    
    public void setBoxNames (BoxNamesBean boxNames) {
        ev.setBoxNames(boxNames);
        fireBoxDataEvent();
    }
    
    public void fireBoxDataEvent () {
        if (ev.getBox() != null && ev.getBoxId() != null && ev.getBoxNames() != null) {
            for (BoxDataListener listener : getListeners()) {
                listener.newBoxData(ev);
            }
        }
    }
}
