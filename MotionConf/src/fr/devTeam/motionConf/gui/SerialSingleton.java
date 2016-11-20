/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.gui;

import fr.devTeam.motionConf.util.Serial;
import fr.devTeam.motionConf.model.RealTimeData;

/**
 *
 * @author fanny
 */
public class SerialSingleton {
    private static SerialSingleton INSTANCE = new SerialSingleton();
    
    private RealTimeData rtd = new RealTimeData();
    private Serial serial = new Serial(this.rtd);
    
    private SerialSingleton() {}
    
    public static SerialSingleton getInstance () {
	return SerialSingleton.INSTANCE;
    }
    
    public RealTimeData getRtd() {
	return rtd;
    }

    public Serial getSerial() {
	return serial;
    }
}
