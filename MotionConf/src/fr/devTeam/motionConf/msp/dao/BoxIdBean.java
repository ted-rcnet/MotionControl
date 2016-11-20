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
public class BoxIdBean extends MspBean {
    private int[] id;
    private int boxCount;

    public BoxIdBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_BOXIDS) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_BOXIDS);
        }
        
        boxCount = message.length-1;
        id = new int[boxCount];
        
        for (int k = 0; k<message.length-1 ; k++) {
            id[k] = CDeserializer.deserialize8(message[k+1], false);
        }
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public int getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(int boxCount) {
        this.boxCount = boxCount;
    }
}
