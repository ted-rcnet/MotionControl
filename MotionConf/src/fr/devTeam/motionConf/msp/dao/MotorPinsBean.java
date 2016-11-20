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
public class MotorPinsBean extends MspBean {
    private int[] motorPins = new int[8];
    
    public MotorPinsBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_MOTOR_PINS) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_MOTOR_PINS);
        }
        
        for (int i = 0; i<8 ; i++) {
            motorPins[i] = CDeserializer.deserialize8(message[i+1], false);
        }
    }

    public int[] getMotorPins() {
        return motorPins;
    }

    public void setMotorPins(int[] motorPins) {
        this.motorPins = motorPins;
    }
}
