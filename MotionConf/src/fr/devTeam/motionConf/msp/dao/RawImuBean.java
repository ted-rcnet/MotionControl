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
public class RawImuBean extends MspBean {
    private int[] rawGyro = new int[3];
    private int[] rawAcc = new int[3];
    private int[] rawMag = new int[3];
    
    public RawImuBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_RAW_IMU) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_RAW_IMU);
        }
        
        rawAcc[Constantes.X_AXIS] = CDeserializer.deserialize16(message[1], message[2], true);
        rawAcc[Constantes.Y_AXIS] = CDeserializer.deserialize16(message[3], message[4], true);
        rawAcc[Constantes.Z_AXIS] = CDeserializer.deserialize16(message[5], message[6], true);
        rawGyro[Constantes.X_AXIS] = CDeserializer.deserialize16(message[7], message[8], true);
        rawGyro[Constantes.Y_AXIS] = CDeserializer.deserialize16(message[9], message[10], true);
        rawGyro[Constantes.Z_AXIS] = CDeserializer.deserialize16(message[11], message[12], true);
        rawMag[Constantes.X_AXIS] = CDeserializer.deserialize16(message[13], message[14], true);
        rawMag[Constantes.Y_AXIS] = CDeserializer.deserialize16(message[15], message[16], true);
        rawMag[Constantes.Z_AXIS] = CDeserializer.deserialize16(message[17], message[18], true);
    }
    
    public int[] getRawGyro() {
        return rawGyro;
    }

    public void setRawGyro(int[] rawGyro) {
        this.rawGyro = rawGyro;
    }

    public int[] getRawAcc() {
        return rawAcc;
    }

    public void setRawAcc(int[] rawAcc) {
        this.rawAcc = rawAcc;
    }

    public int[] getRawMag() {
        return rawMag;
    }

    public void setRawMag(int[] rawMag) {
        this.rawMag = rawMag;
    }
}
