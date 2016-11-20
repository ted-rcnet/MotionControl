/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.AttitudeBean;
import java.util.EventListener;

/**
 *
 * @author EUGI7210
 */
public interface AttitudeDataListener extends EventListener {
    public void newAttitudeData(AttitudeBean attitude);
}
