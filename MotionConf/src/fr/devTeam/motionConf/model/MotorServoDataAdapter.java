/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.MotorBean;
import fr.devTeam.motionConf.msp.dao.ServoBean;

/**
 *
 * @author fanny
 */
public abstract class MotorServoDataAdapter implements MotorServoDataListener {
    @Override
    public void newMotorData(MotorBean motor) {}
    @Override
    public void newServoData(ServoBean servo) {}
}
