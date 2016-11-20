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
public class IdentBean extends MspBean {
    private int version;
    private int multiType;
    private int capability;
    
    public IdentBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_IDENT) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_IDENT);
        }
        version = CDeserializer.deserialize8(message[1], false);
        multiType = CDeserializer.deserialize8(message[2], false);
        capability =  (int)CDeserializer.deserialize32(message[3], message[4], message[5], message[6], false);
    }
    
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getMultiType() {
        return multiType;
    }

    public void setMultiType(int multiType) {
        this.multiType = multiType;
    }

    public int getCapability() {
        return capability;
    }

    public void setCapability(int capability) {
        this.capability = capability;
    }
}
