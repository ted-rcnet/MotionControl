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
public class BoxBean extends MspBean {
    private int[] boxes;
    private int boxCount;
    
    public BoxBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_BOX) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_BOX);
        }
        
        boxCount = (message.length-1)/2;
        boxes = new int[boxCount];
        
        for (int k = 0; k<boxCount ; k++) {
            boxes[k] = CDeserializer.deserialize16(message[k*2+1], message[k*2+2], false);
        }
    }

    public int[] getBoxes() {
        return boxes;
    }

    public void setBoxes(int[] boxes) {
        this.boxes = boxes;
    }

    public int getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(int boxCount) {
        this.boxCount = boxCount;
    }
}
