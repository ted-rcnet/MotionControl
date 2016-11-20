/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.MotorBean;
import fr.devTeam.motionConf.msp.dao.ServoBean;
import javax.swing.event.EventListenerList;

/**
 *
 * @author fanny
 */
public class MotorServoData {
    private MotorBean motor;
    private ServoBean servo;
    
    private final EventListenerList listeners = new EventListenerList();
    
       
    public MotorServoDataListener[] getListeners() {
        return listeners.getListeners(MotorServoDataListener.class);
    }
    
    public void addMotorServoDataListener (MotorServoDataListener listener) {
        listeners.add(MotorServoDataListener.class, listener);
    }
    
    public void removeMotorServoDataListener (MotorServoDataListener listener) {
        listeners.remove(MotorServoDataListener.class, listener);
    }

    public MotorBean getMotor() {
        return motor;
    }

    public void setMotor(MotorBean motor) {
        this.motor = motor;
        
        for (MotorServoDataListener listener : getListeners()) {
            listener.newMotorData(motor);
        }
    }

    public ServoBean getServo() {
        return servo;
    }

    public void setServo(ServoBean servo) {
        this.servo = servo;
        
        for (MotorServoDataListener listener : getListeners()) {
            listener.newServoData(servo);
        }
    }
}
