/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import javax.swing.event.EventListenerList;

/**
 *
 * @author EUGI7210
 */
public class DebugMsgData {
    private final EventListenerList listeners = new EventListenerList();
    private String debugMsg;
    
    public DebugMsgListener[] getListeners() {
        return listeners.getListeners(DebugMsgListener.class);
    }
    
    public void addDebugMsgDataListener (DebugMsgListener listener) {
        listeners.add(DebugMsgListener.class, listener);
    }
    
    public void removeDebugMsgDataListener (DebugMsgListener listener) {
        listeners.remove(DebugMsgListener.class, listener);
    }
    
    public void setDebugMsg (String msg) {
        this.debugMsg = msg;
        for (DebugMsgListener listener : getListeners()) {
                listener.newDebugMsgData(msg);
        }
    }
}
