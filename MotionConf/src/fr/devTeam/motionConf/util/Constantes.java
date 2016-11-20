/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.util;

/**
 *
 * @author EUGI7210
 */
public class Constantes {
    public static final String FILE_EXTENSION = "mcf";
    public static final String FILE_DESCRIPTION = "MotionControl config file";
    
    // Liste des vitesses de port série disponibles
    public static final String SERIAL_SPEED[] = {"115200", "57600", "38400", "19200", "9600" };
    
    // Définition des axes pour les tableaux
    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;
    public static final int Z_AXIS = 2;
    
    // Définition des voies pour les tableaux
    public static final int ROLL = 0;
    public static final int PITCH = 1;
    public static final int YAW = 2;
    public static final int THROTTLE = 3;
    public static final int AUX1 = 4;
    public static final int AUX2 = 5;
    public static final int AUX3 = 6;
    public static final int AUX4 = 7;
    
    // Types de messages MSP
    public static final int MSP_IDENT               = 100;   //out message         multitype + multiwii version + protocol version + capability variable
    public static final int MSP_STATUS              = 101;   //out message         cycletime & errors_count & sensor present & box activation & current setting number
    public static final int MSP_RAW_IMU             = 102;   //out message         9 DOF
    public static final int MSP_SERVO               = 103;   //out message         8 servos
    public static final int MSP_MOTOR               = 104;   //out message         8 motors
    public static final int MSP_RC                  = 105;   //out message         8 rc chan and more
    public static final int MSP_RAW_GPS             = 106;   //out message         fix, numsat, lat, lon, alt, speed, ground course
    public static final int MSP_COMP_GPS            = 107;   //out message         distance home, direction home
    public static final int MSP_ATTITUDE            = 108;   //out message         2 angles 1 heading
    public static final int MSP_ALTITUDE            = 109;   //out message         altitude, variometer
    public static final int MSP_ANALOG              = 110;   //out message         vbat, powermetersum, rssi if available on RX
    public static final int MSP_RC_TUNING           = 111;   //out message         rc rate, rc expo, rollpitch rate, yaw rate, dyn throttle PID
    public static final int MSP_PID                 = 112;   //out message         P I D coeff (9 are used currently)
    public static final int MSP_BOX                 = 113;   //out message         BOX setup (number is dependant of your setup)
    public static final int MSP_MISC                = 114;   //out message         powermeter trig
    public static final int MSP_MOTOR_PINS          = 115;   //out message         which pins are in use for motors & servos, for GUI
    public static final int MSP_BOXNAMES            = 116;   //out message         the aux switch names
    public static final int MSP_PIDNAMES            = 117;   //out message         the PID names
    public static final int MSP_WP                  = 118;   //out message         get a WP, WP# is in the payload, returns (WP#, lat, lon, alt, flags) WP#0-home, WP#16-poshold
    public static final int MSP_BOXIDS              = 119;   //out message         get the permanent IDs associated to BOXes
    public static final int MSP_GLOBAL_CONF	    = 120;   //out message	   get the global configuration
    public static final int MSP_SET_RAW_RC          = 200;   //in message          8 rc chan
    public static final int MSP_SET_RAW_GPS         = 201;   //in message          fix, numsat, lat, lon, alt, speed
    public static final int MSP_SET_PID             = 202;   //in message          P I D coeff (9 are used currently)
    public static final int MSP_SET_BOX             = 203;   //in message          BOX setup (number is dependant of your setup)
    public static final int MSP_SET_RC_TUNING       = 204;   //in message          rc rate, rc expo, rollpitch rate, yaw rate, dyn throttle PID
    public static final int MSP_ACC_CALIBRATION     = 205;   //in message          no param
    public static final int MSP_MAG_CALIBRATION     = 206;   //in message          no param
    public static final int MSP_SET_MISC            = 207;   //in message          powermeter trig + 8 free for future use
    public static final int MSP_RESET_CONF          = 208;   //in message          no param
    public static final int MSP_SET_WP              = 209;   //in message          sets a given WP (WP#,lat, lon, alt, flags)
    public static final int MSP_SELECT_SETTING      = 210;   //in message          Select Setting Number (0-2)
    public static final int MSP_SET_HEAD            = 211;   //in message          define a new heading hold direction
    public static final int MSP_SET_COPTER_SETTINGS = 212;   //in message	   set the copter type and gimbal type
    public static final int MSP_BIND                = 240;   //in message          no param
    public static final int MSP_EEPROM_WRITE        = 250;   //in message          no param
    public static final int MSP_DEBUGMSG            = 253;   //out message         debug string buffer
    public static final int MSP_DEBUG               = 254;   //out message         debug1,debug2,debug3,debug
    
    
    // Types de multi
    public static final int TYPE_TRI          	= 1;
    public static final int TYPE_QUADP		= 2;
    public static final int TYPE_QUADX		= 3;
    public static final int TYPE_BI 		= 4;
    public static final int TYPE_Y6		= 6;
    public static final int TYPE_HEX6		= 7;
    public static final int TYPE_Y4		= 9;
    public static final int TYPE_HEX6X		= 10;
    public static final int TYPE_OCTOX8		= 11;
    public static final int TYPE_OCTOFLATP	= 12;
    public static final int TYPE_OCTOFLATX	= 13;
    public static final int TYPE_VTAIL4		= 17;
    public static final int TYPE_HEX6H  	= 18;
    
    // Paramètres flags global conf
    public static int EXT_MOTOR_FLAG 		= 0x1;
    public static int GIMBAL_FLAG	   	= 0x1;
    public static int GIMBAL_MIX_FLAG		= 0x2;
    public static int GIMBAL_CAMTRIG_FLAG       = 0x4;
    public static int TILT_PITCH_AUX_FLAG       = 0x8;
    public static int MM_GIMBAL_FLAG		= 0x16;
}
