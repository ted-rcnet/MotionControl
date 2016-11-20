/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.AltitudeBean;
import fr.devTeam.motionConf.msp.dao.CompGpsBean;
import fr.devTeam.motionConf.msp.dao.DebugBean;
import fr.devTeam.motionConf.msp.dao.RawGpsBean;
import fr.devTeam.motionConf.msp.dao.RawImuBean;
import fr.devTeam.motionConf.msp.dao.RcBean;
import fr.devTeam.motionConf.msp.dao.StatusBean;

/**
 *
 * @author EUGI7210
 */
public abstract class RealTimeAdapter implements RealTimeDataListener {
    @Override
    public void newRcData (RcBean rcData) {}
    @Override
    public void newRawIMUData (RawImuBean rawImu) {}
    @Override
    public void newRawGPSData (RawGpsBean rawGps){}
    @Override
    public void newCompGPSData(CompGpsBean compGps) {}
    @Override
    public void newDebugData  (DebugBean debug) {}
    @Override
    public void newAltitudeData  (AltitudeBean altitude) {}
    @Override
    public void newStatusData  (StatusBean status) {}
}
