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
public class AttitudeBean extends MspBean {
    private int[] angle = new int[2];
    private int heading;
    private int headFreeModeHold;
    
    public AttitudeBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_ATTITUDE) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_ATTITUDE);
        }
        
        angle[0] =  CDeserializer.deserialize16(message[1], message[2], true);
        angle[1] =  CDeserializer.deserialize16(message[3], message[4], true);
        heading = CDeserializer.deserialize16(message[5], message[6], true);
        headFreeModeHold = CDeserializer.deserialize16(message[7], message[8], true);
    }

    public int[] getAngle() {
        return angle;
    }

    public void setAngle(int[] angle) {
        this.angle = angle;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public int getHeadFreeModeHold() {
        return headFreeModeHold;
    }

    public void setHeadFreeModeHold(int headFreeModeHold) {
        this.headFreeModeHold = headFreeModeHold;
    }
}
