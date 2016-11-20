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
public class MiscBean extends MspBean {
    private int powerMeterTrigger;
    
    public MiscBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_MISC) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_MISC);
        }
        
        powerMeterTrigger = CDeserializer.deserialize16(message[1], message[2], false);
    }

    public int getPowerMeterTrigger() {
        return powerMeterTrigger;
    }

    public void setPowerMeterTrigger(int powerMeterTrigger) {
        this.powerMeterTrigger = powerMeterTrigger;
    }
}
