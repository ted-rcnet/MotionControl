/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.Erreurs;

/**
 *
 * @author EUGI7210
 */
public class SerialException extends Exception {
    public static final int E_ALREADY_CONNECTED = 1;
    public static final int E_CONNECT_FAILED = 2;
    
    private int code = 0;
    
    public SerialException () {
    }
    
    public SerialException(String message) {
        super(message);
    }
    
    public SerialException(String message, int code) {
        super(message);
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
}
