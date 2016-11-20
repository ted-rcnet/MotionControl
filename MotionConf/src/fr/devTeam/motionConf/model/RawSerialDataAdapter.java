/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

/**
 *
 * @author EUGI7210
 */
public abstract class RawSerialDataAdapter implements RawSerialDataListener {
    @Override
    public void newRawSerialData(byte[] rawData) {}
}
