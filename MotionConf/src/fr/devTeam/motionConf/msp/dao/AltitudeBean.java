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
public class AltitudeBean extends MspBean {
    private long altitude;
    private int vario;
    
    public AltitudeBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_ALTITUDE) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_ALTITUDE);
        }
        
        altitude = CDeserializer.deserialize32(message, 1);
        vario = CDeserializer.deserialize16(message,5);
    }

    public long getAltitude() {
        return altitude;
    }

    public void setAltitude(long altitude) {
        this.altitude = altitude;
    }

    public int getVario() {
        return vario;
    }

    public void setVario(int vario) {
        this.vario = vario;
    }
}
