/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.msp;

import fr.devTeam.motionConf.util.Constantes;
import fr.devTeam.motionConf.util.Serial;
import fr.devTeam.motionConf.gui.SerialSingleton;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fanny
 */
public class TimedRealTimeRequest extends Thread {
    private Serial serial = SerialSingleton.getInstance().getSerial();

    private int[] realTimeTasks = {Constantes.MSP_RAW_IMU,
	    Constantes.MSP_DEBUG, Constantes.MSP_ALTITUDE, Constantes.MSP_RAW_GPS, 
	    Constantes.MSP_STATUS, Constantes.MSP_COMP_GPS, Constantes.MSP_ATTITUDE};
    
    private int sleepTime = 100;
    private boolean active = false;
    

    @Override
    public void run() {
	while (true) {
	    if (active) {
		for (int i = 0; i<realTimeTasks.length; i++) {
		    serial.writeMSP(realTimeTasks[i]);
		}
		try {
		    Thread.sleep(sleepTime);
		} catch (InterruptedException ex) {
		    Logger.getLogger(TimedRealTimeRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
	    } else {
		try {
		    Thread.sleep(sleepTime);
		} catch (InterruptedException ex) {
		    Logger.getLogger(TimedRealTimeRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
    }
    
    public void setInactive () {
	active = false;
    }
    
    public void setActive () {
	active = true;
    }
    
    public boolean isActive () {
	return this.active;
    }

    public int[] getRealTimeTasks() {
        return realTimeTasks;
    }

    public void setRealTimeTasks(int[] realTimeTasks) {
        this.realTimeTasks = realTimeTasks;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
}
