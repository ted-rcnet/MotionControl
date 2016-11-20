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
import javax.swing.event.EventListenerList;

/**
 *
 * @author EUGI7210
 */
public class RealTimeData {
    private RawImuBean rawImu;
    private RawGpsBean rawGps;
    private CompGpsBean compGps;
    private DebugBean debug;
    private RcBean rc;
    private AltitudeBean altitude;
    private StatusBean status;
    
    private final EventListenerList listeners = new EventListenerList();

    public RealTimeDataListener[] getListeners() {
        return listeners.getListeners(RealTimeDataListener.class);
    }
    
    public void addRealTimeDataListener (RealTimeDataListener listener) {
        listeners.add(RealTimeDataListener.class, listener);
    }
    
    public void removeRealTimeDataListener (RealTimeDataListener listener) {
        listeners.remove(RealTimeDataListener.class, listener);
    }

    public void setStatus(StatusBean status) {
        this.status = status;
        for (RealTimeDataListener listener : getListeners()) {
            listener.newStatusData(status);
        }
    }
    
    public void setAltitude(AltitudeBean altitude) {
        this.altitude = altitude;
        for (RealTimeDataListener listener : getListeners()) {
            listener.newAltitudeData(altitude);
        }
    }
    
    public void setRawImu(RawImuBean rawImu) {
        this.rawImu = rawImu;
        for (RealTimeDataListener listener : getListeners()) {
            listener.newRawIMUData(rawImu);
        }
    }

    public void setRawGps(RawGpsBean rawGps) {
        this.rawGps = rawGps;
        for (RealTimeDataListener listener : getListeners()) {
            listener.newRawGPSData(rawGps);
        }
    }

    public void setCompGps(CompGpsBean compGps) {
        this.compGps = compGps;
        for (RealTimeDataListener listener : getListeners()) {
            listener.newCompGPSData(compGps);
        }
    }

    public void setDebug(DebugBean debug) {
        this.debug = debug;
        for (RealTimeDataListener listener : getListeners()) {
            listener.newDebugData(debug);
        }
    }

    public void setRc(RcBean rc) {
        this.rc = rc;
        for (RealTimeDataListener listener : getListeners()) {
            listener.newRcData(rc);
        }
    }

    public AltitudeBean getAltitude() {
        return altitude;
    }
    
    public RawImuBean getRawImu() {
        return rawImu;
    }

    public RawGpsBean getRawGps() {
        return rawGps;
    }

    public CompGpsBean getCompGps() {
        return compGps;
    }

    public DebugBean getDebug() {
        return debug;
    }

    public RcBean getRc() {
        return rc;
    }

    public StatusBean getStatus() {
        return status;
    }
    
}
