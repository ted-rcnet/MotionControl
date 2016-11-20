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
public class AnalogBean extends MspBean {
    private int vbat;
    private int powerMeterSum;
    private int rssi;
    
    public AnalogBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_ANALOG) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_ANALOG);
        }
        
        vbat = CDeserializer.deserialize8(message[1], false);
        powerMeterSum = CDeserializer.deserialize16(message[2], message[3], false);
        rssi = CDeserializer.deserialize16(message[4], message[5], false);
    }

    public int getVbat() {
        return vbat;
    }

    public void setVbat(int vbat) {
        this.vbat = vbat;
    }

    public int getPowerMeterSum() {
        return powerMeterSum;
    }

    public void setPowerMeterSum(int powerMeterSum) {
        this.powerMeterSum = powerMeterSum;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
