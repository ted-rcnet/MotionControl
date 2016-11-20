/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import java.util.EventListener;

/**
 *
 * @author fanny
 */
public interface BoxDataListener extends EventListener {
    public void newBoxData (BoxDataEvent ev);
}
