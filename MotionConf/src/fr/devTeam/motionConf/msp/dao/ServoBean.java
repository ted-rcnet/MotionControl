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
public class ServoBean extends MspBean {
    private int[] servo = new int[8];
    
    public ServoBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_SERVO) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_SERVO);
        }
        
        for (int i = 0; i<8 ; i++) {
            servo[i] = CDeserializer.deserialize16(message[i*2+1], message[i*2+2], true);
        }
    }

    public int[] getServo() {
        return servo;
    }

    public void setServo(int[] servo) {
        this.servo = servo;
    }
}
