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
public class DebugMsgBean extends MspBean {
    private String debugMsg;
    
    public DebugMsgBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_DEBUGMSG) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_DEBUGMSG);
        }
        
        StringBuffer strBuf = new StringBuffer();
        
        for (int i=1; i<message.length; i++) {
            strBuf.append(Character.toChars(message[i]));
        }
        
        debugMsg = strBuf.toString();
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }
}
