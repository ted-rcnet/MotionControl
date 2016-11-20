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
public class RcTuningBean extends MspBean {
    private int rcRate;
    private int rcExpo;
    private int rollPitchRate;
    private int yawRate;
    private int dynThrottlePid;
    private int thrMid;
    private int thrExpo;
    
    public RcTuningBean (int[] message) {
        beanFromMessage(message);
    }
        
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_RC_TUNING) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_RC_TUNING);
        }
        
        rcRate = CDeserializer.deserialize8(message[1], false);
        rcExpo = CDeserializer.deserialize8(message[2], false);
        rollPitchRate = CDeserializer.deserialize8(message[3], false);
        yawRate = CDeserializer.deserialize8(message[4], false);
        dynThrottlePid = CDeserializer.deserialize8(message[5], false);
        thrMid = CDeserializer.deserialize8(message[6], false);
        thrExpo = CDeserializer.deserialize8(message[7], false);
    }

    public int getRcRate() {
        return rcRate;
    }

    public void setRcRate(int rcRate) {
        this.rcRate = rcRate;
    }

    public int getRcExpo() {
        return rcExpo;
    }

    public void setRcExpo(int rcExpo) {
        this.rcExpo = rcExpo;
    }

    public int getRollPitchRate() {
        return rollPitchRate;
    }

    public void setRollPitchRate(int rollPitchRate) {
        this.rollPitchRate = rollPitchRate;
    }

    public int getYawRate() {
        return yawRate;
    }

    public void setYawRate(int yawRate) {
        this.yawRate = yawRate;
    }

    public int getDynThrottlePid() {
        return dynThrottlePid;
    }

    public void setDynThrottlePid(int dynThrottlePid) {
        this.dynThrottlePid = dynThrottlePid;
    }

    public int getThrMid() {
        return thrMid;
    }

    public void setThrMid(int thrMid) {
        this.thrMid = thrMid;
    }

    public int getThrExpo() {
        return thrExpo;
    }

    public void setThrExpo(int thrExpo) {
        this.thrExpo = thrExpo;
    }
    
}
