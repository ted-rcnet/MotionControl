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
public class StatusBean extends MspBean {
    private int cycleTime;
    private int i2cErrors;
    private int presentSensors;
    private int boxActivated;
    private int currentConfigSet;
    
    public StatusBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_STATUS) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_STATUS);
        }
        cycleTime = CDeserializer.deserialize16(message[1], message[2], false);
        i2cErrors = CDeserializer.deserialize16(message[3], message[4], true);
        presentSensors = CDeserializer.deserialize16(message[5], message[6], false);
        boxActivated = (int)CDeserializer.deserialize32(message[7], message[8], message[9], message[10], false);
        currentConfigSet = CDeserializer.deserialize8(message[11], false);
    }
    
    public int getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public int getI2cErrors() {
        return i2cErrors;
    }

    public void setI2cErrors(int i2cErrors) {
        this.i2cErrors = i2cErrors;
    }

    public int getPresentSensors() {
        return presentSensors;
    }

    public void setPresentSensors(int presentSensors) {
        this.presentSensors = presentSensors;
    }

    public int getBoxActivated() {
        return boxActivated;
    }

    public void setBoxActivated(int boxActivated) {
        this.boxActivated = boxActivated;
    }

    public int getCurrentConfigSet() {
        return currentConfigSet;
    }

    public void setCurrentConfigSet(int currentConfigSet) {
        this.currentConfigSet = currentConfigSet;
    }
}
