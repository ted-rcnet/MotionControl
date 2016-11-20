/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.AttitudeBean;

/**
 *
 * @author EUGI7210
 */
public abstract class AttitudeDataAdapter implements AttitudeDataListener {
    
    @Override
    public void newAttitudeData(AttitudeBean attitude) {}
}
