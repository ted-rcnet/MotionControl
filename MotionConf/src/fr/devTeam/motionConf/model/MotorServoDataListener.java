/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.MotorBean;
import fr.devTeam.motionConf.msp.dao.ServoBean;
import java.util.EventListener;

/**
 *
 * @author fanny
 */
public interface MotorServoDataListener extends EventListener {
    public void newMotorData(MotorBean motor);
    public void newServoData(ServoBean servo);
}
