/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.msp.dao;

import fr.devTeam.motionConf.util.Constantes;
import fr.devTeam.motionConf.msp.CDeserializer;

/**
 *
 * @author EUGI7210
 */
public class DebugBean extends MspBean {
    private int[] debug = new int[4];
    
    public DebugBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_DEBUG) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_DEBUG);
        }
        
        debug[0] = CDeserializer.deserialize16(message[1], message[2], true);
        debug[1] = CDeserializer.deserialize16(message[3], message[4], true);
        debug[2] = CDeserializer.deserialize16(message[5], message[6], true);
        debug[3] = CDeserializer.deserialize16(message[7], message[8], true);
    }

    public int[] getDebug() {
        return debug;
    }

    public void setDebug(int[] debug) {
        this.debug = debug;
    }
}
