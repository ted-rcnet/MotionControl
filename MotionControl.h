// Only modify this file to include
// - function definitions (prototypes)
// - include files
// - extern variable definitions
// In the appropriate section

#ifndef MotionControl_H_
#define MotionControl_H_

#include <Arduino.h>
#include "config.h"
#include "def.h"

#define  VERSION  072

#ifdef __cplusplus
extern "C" {
#endif
void loop();
void setup();
#ifdef __cplusplus
} // extern "C"
#endif

void annexCode();
void servos2Neutral();

/*********** RC alias *****************/
enum rc {
	ROLL, PITCH, YAW, THROTTLE, AUX1, AUX2, AUX3, AUX4
};

enum pid {
	PIDROLL,
	PIDPITCH,
	PIDYAW,
	PIDALT,
	PIDPOS,
	PIDPOSR,
	PIDNAVR,
	PIDLEVEL,
	PIDMAG,
	PIDVEL,     // not used currently
	PIDITEMS
};

//const char pidnames[] PROGMEM =
//		"ROLL;"
//		"PITCH;"
//		"YAW;"
//		"ALT;"
//		"Pos;"
//		"PosR;"
//		"NavR;"
//		"LEVEL;"
//		"MAG;"
//		"VEL;";

enum box {
	BOXARM,
#if ACC
	BOXANGLE, BOXHORIZON,
#endif
#if BARO
	BOXBARO,
#endif
#ifdef VARIOMETER
	BOXVARIO,
#endif
#if MAG
	BOXMAG, BOXHEADFREE, BOXHEADADJ, // acquire heading for HEADFREE mode
#endif
#if defined(SERVO_TILT) || defined(GIMBAL)  || defined(SERVO_MIX_TILT)
	BOXCAMSTAB,
#endif
#if defined(CAMTRIG)
	BOXCAMTRIG,
#endif
#if GPS
	BOXGPSHOME,
	BOXGPSHOLD,
#if defined(SDCARD_MISSION)
	BOXMISSION,
#endif
	BOXPOI,
#endif
#if defined(FIXEDWING) || defined(HELICOPTER)
	BOXPASSTHRU,
#endif
#if defined(BUZZER)
	BOXBEEPERON,
#endif
#if defined(LED_FLASHER)
	BOXLEDMAX, // we want maximum illumination
	BOXLEDLOW,// low/no lights
#endif
#if defined(LANDING_LIGHTS_DDR)
	BOXLLIGHTS, // enable landing lights at any altitude
#endif
#ifdef INFLIGHT_ACC_CALIBRATION
	BOXCALIB,
#endif
#ifdef GOVERNOR_P
	BOXGOV,
#endif
#ifdef OSD_SWITCH
	BOXOSD,
#endif
	CHECKBOXITEMS
};

// This PROGMEM variables should be defined only by MotionControl.cpp
// so it is controlled by this flag
#if defined(MAIN_MMC)
char pidnames[] PROGMEM =
		"ROLL;"
		"PITCH;"
		"YAW;"
		"ALT;"
		"Pos;"
		"PosR;"
		"NavR;"
		"LEVEL;"
		"MAG;"
		"VEL;";

char boxnames[] PROGMEM = // names for dynamic generation of config GUI
		"ARM;"
#if ACC
				"ANGLE;"
				"HORIZON;"
#endif
#if BARO
		"BARO;"
#endif
#ifdef VARIOMETER
		"VARIO;"
#endif
#if MAG
		"MAG;"
		"HEADFREE;"
		"HEADADJ;"
#endif
#if defined(SERVO_TILT) || defined(GIMBAL)|| defined(SERVO_MIX_TILT)
		"CAMSTAB;"
#endif
#if defined(CAMTRIG)
		"CAMTRIG;"
#endif
#if GPS
		"GPS HOME;"
		"GPS HOLD;"
#if defined(SDCARD_MISSION)
		"MISSION;"
#endif
		"POI;"
#endif
#if defined(FIXEDWING) || defined(HELICOPTER)
		"PASSTHRU;"
#endif
#if defined(BUZZER)
		"BEEPER;"
#endif
#if defined(LED_FLASHER)
		"LEDMAX;"
		"LEDLOW;"
#endif
#if defined(LANDING_LIGHTS_DDR)
		"LLIGHTS;"
#endif
#ifdef INFLIGHT_ACC_CALIBRATION
		"CALIB;"
#endif
#ifdef GOVERNOR_P
		"GOVERNOR;"
#endif
#ifdef OSD_SWITCH
		"OSD SW;"
#endif
;

uint8_t boxids[] PROGMEM = { // permanent IDs associated to boxes. This way, you can rely on an ID number to identify a BOX function.
		0, //"ARM;"
#if ACC
				1, //"ANGLE;"
				2, //"HORIZON;"
#endif
#if BARO
				3, //"BARO;"
#endif
#ifdef VARIOMETER
				4, //"VARIO;"
#endif
#if MAG
				5, //"MAG;"
				6, //"HEADFREE;"
				7, //"HEADADJ;"
#endif
#if defined(SERVO_TILT) || defined(GIMBAL)|| defined(SERVO_MIX_TILT)
				8, //"CAMSTAB;"
#endif
#if defined(CAMTRIG)
				9, //"CAMTRIG;"
#endif
#if GPS
				10, //"GPS HOME;"
				11, //"GPS HOLD;"
#if defined(SDCARD_MISSION)
				12, //"MISSION;"
#endif
				13, //"POI;
#endif
#if defined(FIXEDWING) || defined(HELICOPTER)
				14, //"PASSTHRU;"
#endif
#if defined(BUZZER)
				15, //"BEEPER;"
#endif
#if defined(LED_FLASHER)
		16, //"LEDMAX;"
		17,//"LEDLOW;"
#endif
#if defined(LANDING_LIGHTS_DDR)
		18, //"LLIGHTS;"
#endif
#ifdef INFLIGHT_ACC_CALIBRATION
		19, //"CALIB;"
#endif
#ifdef GOVERNOR_P
		20, //"GOVERNOR;"
#endif
#ifdef OSD_SWITCH
		21, //"OSD_SWITCH;"
#endif
	};
#endif


struct flags_struct {
	uint8_t OK_TO_ARM :1;
	uint8_t ARMED :1;
	uint8_t I2C_INIT_DONE :1; // For i2c gps we have to now when i2c init is done, so we can update parameters to the i2cgps from eeprom (at startup it is done in setup())
	uint8_t ACC_CALIBRATED :1;
	uint8_t NUNCHUKDATA :1;
	uint8_t ANGLE_MODE :1;
	uint8_t HORIZON_MODE :1;
	uint8_t MAG_MODE :1;
	uint8_t BARO_MODE :1;
	uint8_t GPS_HOME_MODE :1;
	uint8_t GPS_HOLD_MODE :1;
	uint8_t GPS_POI_MODE :1;
	uint8_t SMOOTH_HOLD :1;
	uint8_t GPS_MISSION_MODE :1;
	uint8_t HEADFREE_MODE :1;
	uint8_t PASSTHRU_MODE :1;
	uint8_t GPS_FIX :1;
	uint8_t GPS_FIX_HOME :1;
	uint8_t SMALL_ANGLES_25 :1;
	uint8_t CALIBRATE_MAG :1;
	uint8_t VARIO_MODE :1;
	uint8_t SDCARD :1;
	uint8_t ATO :1;
};

// ******************
// rc functions
// ******************
#define MINCHECK 1100
#define MAXCHECK 1900

#define ROL_LO  (1<<(2*ROLL))
#define ROL_CE  (3<<(2*ROLL))
#define ROL_HI  (2<<(2*ROLL))
#define PIT_LO  (1<<(2*PITCH))
#define PIT_CE  (3<<(2*PITCH))
#define PIT_HI  (2<<(2*PITCH))
#define YAW_LO  (1<<(2*YAW))
#define YAW_CE  (3<<(2*YAW))
#define YAW_HI  (2<<(2*YAW))
#define THR_LO  (1<<(2*THROTTLE))
#define THR_CE  (3<<(2*THROTTLE))
#define THR_HI  (2<<(2*THROTTLE))


struct gConf {
	uint8_t currentSet;
	int16_t accZero[3];
	int16_t magZero[3];
	uint8_t checksum;      // MUST BE ON LAST POSITION OF STRUCTURE !
};
//
struct config {
	uint8_t checkNewConf;
	uint8_t P8[PIDITEMS], I8[PIDITEMS], D8[PIDITEMS];
	uint8_t rcRate8;
	uint8_t rcExpo8;
	uint8_t rollPitchRate;
	uint8_t yawRate;
	uint8_t dynThrPID;
	uint8_t thrMid8;
	uint8_t thrExpo8;
	int16_t angleTrim[2];
	uint16_t activate[CHECKBOXITEMS];
	uint8_t powerTrigger1;
#ifdef FLYING_WING
	uint16_t wing_left_mid;
	uint16_t wing_right_mid;
#endif
#ifdef TRI
	uint16_t tri_yaw_middle;
#endif
#if defined HELICOPTER || defined(AIRPLANE)|| defined(SINGLECOPTER)|| defined(DUALCOPTER)
	int16_t servoTrim[8];
#endif
#if defined(GYRO_SMOOTHING)
	uint8_t Smoothing[3];
#endif
#if defined (FAILSAFE)
	int16_t failsafe_throttle;
#endif
#ifdef VBAT
	uint8_t vbatscale;
	uint8_t vbatlevel_warn1;
	uint8_t vbatlevel_warn2;
	uint8_t vbatlevel_crit;
	uint8_t no_vbat;
#endif
#ifdef POWERMETER
	uint16_t psensornull;
	uint16_t pleveldivsoft;
	uint16_t pleveldiv;
	uint8_t pint2ma;
#endif
#ifdef CYCLETIME_FIXATED
	uint16_t cycletime_fixated;
#endif
#ifdef MMGYRO
	uint8_t mmgyro;
#endif
#ifdef ARMEDTIMEWARNING
	uint16_t armedtimewarning;
#endif
	int16_t minthrottle;
#ifdef GOVERNOR_P
	int16_t governorP;
	int16_t governorD;
	int8_t governorR;
#endif
#ifdef MIDDLECOMMAND
	uint16_t middleCmd;
#endif
	uint8_t checksum;      // MUST BE ON LAST POSITION OF CONF STRUCTURE !
};

#ifdef LOG_PERMANENT
struct permLog{
	uint16_t arm;           // #arm events
	uint16_t disarm;        // #disarm events
	uint16_t start;         // #powercycle/reset/initialize events
	uint32_t armed_time;   // copy of armedTime @ disarm
	uint32_t lifetime;      // sum (armed) lifetime in seconds
	uint16_t failsafe;      // #failsafe state @ disarm
	uint16_t i2c;           // #i2c errs state @ disarm
	uint8_t running; // toggle on arm & disarm to monitor for clean shutdown vs. powercut
	uint8_t checksum;      // MUST BE ON LAST POSITION OF CONF STRUCTURE !
};
#endif


#define LAT  0
#define LON  1
#define ALT  2
#define HEAD 3
// default POSHOLD control gains
#define POSHOLD_P              .11
#define POSHOLD_I              0.7
#define POSHOLD_IMAX           20        // degrees
#define POSHOLD_RATE_P         9.0
#define POSHOLD_RATE_I         0.5      // Wind control
#define POSHOLD_RATE_D         0.13     // try 2 or 3 for POSHOLD_RATE 1
#define POSHOLD_RATE_IMAX      20        // degrees
// default Navigation PID gains
#define NAV_P                  2.0
#define NAV_I                  0.20      // Wind control
#define NAV_D                  0.08      //
#define NAV_IMAX               20        // degrees
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Serial GPS only variables
//navigation mode
#define NAV_MODE_NONE          0
#define NAV_MODE_POSHOLD       1
#define NAV_MODE_WP            2
#define NAV_MODE_POI		   3

struct flagsGPS_struct {
	uint8_t autoLanding :1;
	uint8_t ascending :1;
	uint8_t waypointReached :1;
	uint8_t waypointReaded :1;
};

#endif /* MotionControl_H_ */
