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
public class PidNamesBean extends MspBean {
    private String[] pidNames;
    
    public PidNamesBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_PIDNAMES) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_PIDNAMES);
        }
        
        StringBuffer strBuf = new StringBuffer();
        
        for (int i=0; i<message.length; i++) {
            strBuf.append(Character.toChars(message[i+1]));
        }
        
        pidNames = strBuf.toString().split(";");
    }

    public String[] getPidNames() {
        return pidNames;
    }

    public void setPidNames(String[] pidNames) {
        this.pidNames = pidNames;
    }
}
