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
public class RawGpsBean extends MspBean {
    private int fix;
    private int numSat;
    private long latitude;
    private long longitude;
    private int altitude;
    private int speed;
    private int groundCourse;
    
    public RawGpsBean (int[] message) {
        beanFromMessage(message);
    }
    
    @Override
    public final void beanFromMessage(int[] message) {
        if (message[0] != Constantes.MSP_RAW_GPS) {
            throw new ClassCastException("Invalid msp message for this bean. got : "+ message[0]+" expected : "+ Constantes.MSP_RAW_GPS);
        }
        
        fix = CDeserializer.deserialize8(message[1], false);
        numSat = CDeserializer.deserialize8(message[2], false);
        latitude = CDeserializer.deserialize32(message[3], message[4], message[5], message[6], true);
        longitude = CDeserializer.deserialize32(message[7], message[8], message[9], message[10], true);
        altitude = CDeserializer.deserialize16(message[11], message[12], false);
        speed = CDeserializer.deserialize16(message[13], message[14], false);
        groundCourse = CDeserializer.deserialize16(message[15], message[16], false);
    }

    public int getFix() {
        return fix;
    }

    public void setFix(int fix) {
        this.fix = fix;
    }

    public int getNumSat() {
        return numSat;
    }

    public void setNumSat(int numSat) {
        this.numSat = numSat;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGroundCourse() {
        return groundCourse;
    }

    public void setGroundCourse(int groundCourse) {
        this.groundCourse = groundCourse;
    }
    
    
}
