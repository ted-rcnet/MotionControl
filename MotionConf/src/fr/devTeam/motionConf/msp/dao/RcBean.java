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
public class RcBean extends MspBean {
    private int[] rcData = new int[8];
    
    public RcBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_RC) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_RC);
        }
        
        for (int i = 0; i<8 ; i++) {
            rcData[i] = CDeserializer.deserialize16(message[i*2+1], message[i*2+2], true);
        }
    }
    
    public int[] getRcData() {
        return rcData;
    }

    public void setRcData(int[] rcData) {
        this.rcData = rcData;
    }

}
