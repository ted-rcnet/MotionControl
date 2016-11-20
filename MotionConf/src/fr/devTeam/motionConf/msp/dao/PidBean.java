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
public class PidBean extends MspBean {
    private int[] p;
    private int[] i;
    private int[] d;
    private int pidItemsCount;
    
    public PidBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_PID) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_PID);
        }
        
        pidItemsCount = (message.length-1)/3;
        p = new int[pidItemsCount];
        i = new int[pidItemsCount];
        d = new int[pidItemsCount];
        
        for (int k = 0; k<pidItemsCount ; k++ ) {
            p[k] = CDeserializer.deserialize8(message[k*3+1], false);
            i[k] = CDeserializer.deserialize8(message[k*3+2], false);
            d[k] = CDeserializer.deserialize8(message[k*3+3], false);
        }
    }

    public int[] getP() {
        return p;
    }

    public void setP(int[] p) {
        this.p = p;
    }

    public int[] getI() {
        return i;
    }

    public void setI(int[] i) {
        this.i = i;
    }

    public int[] getD() {
        return d;
    }

    public void setD(int[] d) {
        this.d = d;
    }

    public int getPidItemsCount() {
        return pidItemsCount;
    }

    public void setPidItemsCount(int pidItemsCount) {
        this.pidItemsCount = pidItemsCount;
    }
}
