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
import java.util.EventListener;

/**
 *
 * @author EUGI7210
 */
public interface RealTimeDataListener extends EventListener {
    public void newRcData (RcBean rcData);
    public void newRawIMUData (RawImuBean rawImu);
    public void newRawGPSData (RawGpsBean rawGps);
    public void newCompGPSData(CompGpsBean compGps);
    public void newDebugData  (DebugBean debug);
    public void newAltitudeData(AltitudeBean altitude);
    public void newStatusData (StatusBean status);
}
