/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.msp.dao;

import fr.devTeam.motionConf.msp.CDeserializer;
import fr.devTeam.motionConf.util.Constantes;

/**
 *
 * @author EUGI7210
 */
public class GlobalConfBean extends MspBean {
    private int numberMotors;
    private int copterType;
    private int gimbalType;
    private int mixTable;
    
    @Override
    protected void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_GLOBAL_CONF) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_RC_TUNING);
        }
        
        numberMotors = CDeserializer.deserialize8(message[1], false);
        copterType = CDeserializer.deserialize8(message[2], false);
        gimbalType = CDeserializer.deserialize8(message[3], false);
        mixTable = CDeserializer.deserialize8(message[4], false);
    }

    public int getNumberMotors() {
        return numberMotors;
    }

    public void setNumberMotors(int numberMotors) {
        this.numberMotors = numberMotors;
    }

    public int getCopterType() {
        return copterType;
    }

    public void setCopterType(int copterType) {
        this.copterType = copterType;
    }

    public int getGimbalType() {
        return gimbalType;
    }

    public void setGimbalType(int gimbalType) {
        this.gimbalType = gimbalType;
    }

    public int getMixTable() {
        return mixTable;
    }

    public void setMixTable(int mixTable) {
        this.mixTable = mixTable;
    }
}
