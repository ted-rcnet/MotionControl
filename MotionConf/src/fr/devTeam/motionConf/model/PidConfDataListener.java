/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import java.util.EventListener;

/**
 *
 * @author EUGI7210
 */
public interface PidConfDataListener extends EventListener {
    public void newPidConfData (PidConfDataEvent ev);
}
