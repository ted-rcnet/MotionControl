/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.msp.dao;

import fr.devTeam.motionConf.util.Constantes;

/**
 *
 * @author EUGI7210
 */
public class BoxNamesBean extends MspBean {
    private String[] boxNames;
    
    public BoxNamesBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_BOXNAMES) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_BOXNAMES);
        }
        
        StringBuffer strBuf = new StringBuffer();
        
        for (int i=0; i<message.length-1; i++) {
            strBuf.append(Character.toChars(message[i+1]));
        }
        
        boxNames = strBuf.toString().split(";");
    }

    public String[] getBoxNames() {
        return boxNames;
    }

    public void setBoxNames(String[] boxNames) {
        this.boxNames = boxNames;
    }
    
    
}
