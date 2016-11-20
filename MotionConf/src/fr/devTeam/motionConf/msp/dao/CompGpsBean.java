/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.msp.dao;

import fr.devTeam.motionConf.util.Constantes;
import fr.devTeam.motionConf.msp.CDeserializer;

/**
 *
 * @author rotule
 */
public class CompGpsBean extends MspBean {
    private int distanceToHome;
    private int directionToHome;
    private int update;
    
    public CompGpsBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_COMP_GPS) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_COMP_GPS);
        }
        
        distanceToHome = CDeserializer.deserialize16(message[1],message[2],false);
        directionToHome = CDeserializer.deserialize16(message[3],message[4],true);
        update = CDeserializer.deserialize8(message[5],false);
    }

    public int getDistanceToHome() {
        return distanceToHome;
    }

    public void setDistanceToHome(int distanceToHome) {
        this.distanceToHome = distanceToHome;
    }

    public int getDirectionToHome() {
        return directionToHome;
    }

    public void setDirectionToHome(int directionToHome) {
        this.directionToHome = directionToHome;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }
    
    
}
