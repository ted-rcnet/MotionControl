/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.util;

import fr.devTeam.motionConf.Erreurs.SerialException;
import fr.devTeam.motionConf.model.AttitudeData;
import fr.devTeam.motionConf.model.BoxData;
import fr.devTeam.motionConf.model.DebugMsgData;
import fr.devTeam.motionConf.model.MotorServoData;
import fr.devTeam.motionConf.model.PidConfData;
import fr.devTeam.motionConf.model.RawSerialDataListener;
import fr.devTeam.motionConf.msp.dao.StatusBean;
import fr.devTeam.motionConf.model.RealTimeData;
import fr.devTeam.motionConf.msp.dao.AltitudeBean;
import fr.devTeam.motionConf.msp.dao.AttitudeBean;
import fr.devTeam.motionConf.msp.dao.BoxBean;
import fr.devTeam.motionConf.msp.dao.BoxIdBean;
import fr.devTeam.motionConf.msp.dao.BoxNamesBean;
import fr.devTeam.motionConf.msp.dao.CompGpsBean;
import fr.devTeam.motionConf.msp.dao.DebugBean;
import fr.devTeam.motionConf.msp.dao.DebugMsgBean;
import fr.devTeam.motionConf.msp.dao.MotorBean;
import fr.devTeam.motionConf.msp.dao.PidBean;
import fr.devTeam.motionConf.msp.dao.RawGpsBean;
import fr.devTeam.motionConf.msp.dao.RawImuBean;
import fr.devTeam.motionConf.msp.dao.RcBean;
import fr.devTeam.motionConf.msp.dao.RcTuningBean;
import fr.devTeam.motionConf.msp.dao.ServoBean;
import javax.swing.event.EventListenerList;

/**
 *
 * @author EUGI7210
 */
public class Serial implements Network_iface {
    private Network network;
    private boolean connectionOpened;
    private String currentPort;
    private RealTimeData rtd;
    private BoxData boxData = new BoxData();
    private DebugMsgData debugMsg = new DebugMsgData();
    private PidConfData pidConfData = new PidConfData();
    private AttitudeData attitudeData = new AttitudeData();
    private MotorServoData motorServo = new MotorServoData();
    private final EventListenerList listeners = new EventListenerList();
    
    public Serial(RealTimeData rtd) {
        connectionOpened = false;
        network = new Network(0, this, 255);
        this.rtd = rtd;
    }
    
    public Serial () {
        connectionOpened = false;
        network = new Network(0, this, 255);
        rtd = new RealTimeData();
    }
    
    public void connect(String serialPort, int speed) throws SerialException {
        if (this.connectionOpened) {
            throw new SerialException("Connection déjà ouverte sur un port série", SerialException.E_ALREADY_CONNECTED);
        }
        
        if (!network.connect(serialPort, speed)) {
            throw new SerialException("Echec de connection au port "+serialPort, SerialException.E_CONNECT_FAILED);
        }
        
        System.out.println("Connexion ouverte sur le port "+serialPort);
        this.connectionOpened = true;
        this.currentPort = serialPort;
    }
    
    public void disconnect () throws SerialException {
        if (!network.disconnect()) {
            throw new SerialException("Echec de la deconnexion du port série "+this.currentPort);
        }
        
        System.out.println("Déconnecté du port série "+this.currentPort);
        this.connectionOpened = false;
        this.currentPort = "";
    }
    
    public boolean isConnectionOpened() {
        return this.connectionOpened;
    }
    
    public int[] getMessageArray (int mspCommand) {
	int[] message = {'$','M','<',0,mspCommand,mspCommand};
	return message;
    }
    
    public void writeMSP (int mspCommand) {
	int[] message = getMessageArray(mspCommand);
	writeRaw(message.length, message);
    }
    
    public void writeMSP (int mspCommand, int[] message) {
	int checksum = 0;
        checksum ^= message.length;
        checksum ^= mspCommand;
	int[] buffer = new int[6+message.length];
	
	buffer[0] = '$'; buffer[1] = 'M'; buffer[2] = '<'; buffer[3] = message.length; buffer[4] = mspCommand;
	
	for (int i = 0; i<message.length; i++) {
	    buffer[i+5] = message[i];
	    checksum ^= message[i];
	}
	
	buffer[buffer.length-1] = checksum;
	writeRaw(buffer.length, buffer);
    }

    public void writeRawString (String message) {
//        if (trtr.isActive()) {
//            trtr.setInactive();
            network.writeSerial(message);
//            trtr.setActive();
//        } else {
//            network.writeSerial(message);
//        }
    }
    
    public void writeRaw(int numBytes, int[] message) {
	network.writeSerial(numBytes, message);
    }
    
    @Override
    public void writeLog(int id, String text) {
        System.out.println("Network:"+id+":"+text);
    }
    
    @Override
    public void parseInput(int id, int numBytes, int[] message) {
        switch (message[0]) {
            case Constantes.MSP_IDENT:
                break;
            case Constantes.MSP_STATUS:
                rtd.setStatus(new StatusBean(message)); break;
	    case Constantes.MSP_RAW_IMU :
		rtd.setRawImu(new RawImuBean(message)); break;
            case Constantes.MSP_RC :
                rtd.setRc(new RcBean(message)); break;
            case Constantes.MSP_DEBUG:
                rtd.setDebug(new DebugBean(message)); break;
            case Constantes.MSP_ALTITUDE:
                rtd.setAltitude(new AltitudeBean(message)); break;
            case Constantes.MSP_COMP_GPS:
                rtd.setCompGps(new CompGpsBean(message)); break;
	    case Constantes.MSP_RAW_GPS :
		rtd.setRawGps(new RawGpsBean(message)); break;
            case Constantes.MSP_BOX :
                boxData.setBox(new BoxBean(message)); break;
            case Constantes.MSP_BOXIDS :
                boxData.setBoxId(new BoxIdBean(message)); break;
            case Constantes.MSP_BOXNAMES :
                boxData.setBoxNames(new BoxNamesBean(message)); break;
            case Constantes.MSP_PID :
                pidConfData.setPids(new PidBean(message)); break;
            case Constantes.MSP_RC_TUNING :
                pidConfData.setRcTuning(new RcTuningBean(message)); break;
            case Constantes.MSP_ATTITUDE :
                attitudeData.setAttitude(new AttitudeBean(message)); break;
            case Constantes.MSP_MOTOR :
                motorServo.setMotor(new MotorBean(message)); break;
            case Constantes.MSP_SERVO :
                motorServo.setServo(new ServoBean(message)); break;
            case Constantes.MSP_DEBUGMSG :
                debugMsg.setDebugMsg(new DebugMsgBean(message).getDebugMsg()); break;
        }
    }
    
    @Override
    public void rawInput (byte[] message) {
        for (RawSerialDataListener listener : getListeners()) {
            listener.newRawSerialData(message);
        }
    }
    
    public RawSerialDataListener[] getListeners() {
        return listeners.getListeners(RawSerialDataListener.class);
    }
    
    public void addRawSerialDataListener (RawSerialDataListener listener) {
        listeners.add(RawSerialDataListener.class, listener);
    }
    
    public void removeRawSerialDataListener (RawSerialDataListener listener) {
        listeners.remove(RawSerialDataListener.class, listener);
    }
    
    @Override
    public void networkDisconnected(int id) {
        this.connectionOpened = false;
    }
    
    public String getCurrentPort() {
        return this.currentPort;
    }

    public BoxData getBoxData() {
        return boxData;
    }

    public void setBoxData(BoxData boxData) {
        this.boxData = boxData;
    }

    public PidConfData getPidConfData() {
        return pidConfData;
    }

    public void setPidConfData(PidConfData pidConfData) {
        this.pidConfData = pidConfData;
    }

    public AttitudeData getAttitudeData() {
        return attitudeData;
    }

    public void setAttitudeData(AttitudeData attitudeData) {
        this.attitudeData = attitudeData;
    }

    public MotorServoData getMotorServo() {
        return motorServo;
    }

    public void setMotorServo(MotorServoData motorServo) {
        this.motorServo = motorServo;
    }

    public DebugMsgData getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(DebugMsgData debugMsg) {
        this.debugMsg = debugMsg;
    }
}
