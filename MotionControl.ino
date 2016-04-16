/*
FlightControl Software for The RCNet FC
Software V0.9.2
This software is based on MultiWii version 2.2, credits and info below and it's under GPLV3 Licence like MWC.
This software is designed and tested only for the RCNet FC, and can't work from stock on others board, be aware that we have to delete informations and support about a lot of unused material to get a better software for our card.
For more informations about this so

MultiWiiCopter by Alexandre Dubus
www.multiwii.com
March  2013     V2.2
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 any later version. see <http://www.gnu.org/licenses/>
*/


#define MAIN_MMC
#include "MotionControl.h"
#include <Arduino.h>
#ifdef MWI_SDCARD
#include <SdFat.h>
#endif
#include "alarms.h"
#include "sensors.h"
#include "IMU.h"
#include "output.h"
#include "RX.h"
#include "SD.h"
#include "serial.h"
#include "GPS.h"
#include "EEPROM.h"
#include "LCD.h"

extern int16_t i2c_errors_count;

uint32_t currentTime = 0;
uint16_t previousTime = 0;
uint16_t cycleTime = 0; // this is the number in micro second to achieve a full loop, it can differ a little and is taken into account in the PID loop
uint16_t calibratingA = 0; // the calibration is done in the main loop. Calibrating decreases at each cycle down to 0, then we enter in a normal mode.
uint16_t calibratingB = 0; // baro calibration = get new ground pressure value
uint16_t calibratingG;
int16_t gyroADC[3], accADC[3], accSmooth[3], magADC[3];
uint16_t acc_1G;            // this is the 1G measured acceleration
uint16_t acc_25deg;
int16_t heading, magHold, headFreeModeHold; // [-180;+180]
uint8_t vbat;                   // battery voltage in 0.1V steps
uint8_t vbatMin = VBATNOMINAL;  // lowest battery voltage in 0.1V steps
uint8_t rcOptions[CHECKBOXITEMS];
int32_t BaroAlt, EstAlt, AltHold; // in cm
int16_t BaroPID = 0;
int16_t errorAltitudeI = 0;
int16_t vario = 0;              // variometer in cm/s

int32_t GPS_coord[2];
int32_t GPS_home[2];
int32_t GPS_hold[2];
int32_t GPS_mission[4];
uint8_t GPS_numSat;
uint16_t GPS_distanceToHome;           // distance to home  - unit: meter
int16_t GPS_directionToHome;          // direction to home - unit: degree
uint32_t GPS_distanceToPOI;			  // distance to POI - unit : cm
uint16_t GPS_altitude;                 // GPS altitude      - unit: meter
uint16_t GPS_speed;                     // GPS speed         - unit: cm/s
uint8_t GPS_update = 0; // a binary toogle to distinct a GPS position update
int16_t GPS_angle[2] = { 0, 0 }; // the angles that must be applied for GPS correction
uint16_t GPS_ground_course = 0;    //                   - unit: degree*10
uint8_t GPS_Present = 0;                      // Checksum from Gps serial
uint8_t GPS_Enable = 0;

int32_t baroPressure;
int32_t baroTemperature;
int32_t baroPressureSum;
struct flagsGPS_struct fGPS;

int16_t debug[4];
int16_t sonarAlt; //to think about the unit

uint8_t nav_mode = NAV_MODE_NONE; // Navigation mode
uint8_t alarmArray[16];           // array

int16_t nav[2];
int16_t nav_rated[2]; //Adding a rate controller to the navigation to make it smoother


#if defined(THROTTLE_ANGLE_CORRECTION)
  int16_t throttleAngleCorrection = 0;	// correction of throttle in lateral wind,
  int8_t  cosZ = 100;					// cos(angleZ)*100
#endif

#if defined(ARMEDTIMEWARNING)
uint32_t ArmedTimeWarningMicroSeconds = 0;
#endif

struct flags_struct f;

#if defined(LOG_VALUES) || defined(LCD_TELEMETRY)
uint16_t cycleTimeMax = 0;       // highest ever cycle timen
uint16_t cycleTimeMin = 65535;// lowest ever cycle timen
uint16_t powerMax = 0;// highest ever current;
int32_t BAROaltMax;// maximum value
#endif
#if defined(LOG_VALUES) || defined(LCD_TELEMETRY) || defined(ARMEDTIMEWARNING)  || defined(LOG_PERMANENT)
uint32_t armedTime = 0;
#endif

#if defined(INFLIGHT_ACC_CALIBRATION)
uint16_t InflightcalibratingA = 0;
int16_t AccInflightCalibrationArmed;
uint16_t AccInflightCalibrationMeasurementDone = 0;
uint16_t AccInflightCalibrationSavetoEEProm = 0;
uint16_t AccInflightCalibrationActive = 0;
#endif

// **********************
// power meter
// **********************
#if defined(POWERMETER)
#define PMOTOR_SUM 8                       // index into pMeter[] for sum
uint32_t pMeter[PMOTOR_SUM + 1]; // we use [0:7] for eight motors,one extra for sum
uint8_t pMeterV;// dummy to satisfy the paramStruct logic in ConfigurationLoop()
uint32_t pAlarm;// we scale the eeprom value from [0:255] to this value we can directly compare to the sum in pMeter[6]
uint16_t powerValue = 0;// last known current
#endif
uint16_t intPowerMeterSum, intPowerTrigger1;

// **********************
// telemetry
// **********************
#if defined(LCD_TELEMETRY)
uint8_t telemetry = 0;
uint8_t telemetry_auto = 0;
#endif
#ifdef LCD_TELEMETRY_STEP
char telemetryStepSequence [] = LCD_TELEMETRY_STEP;
uint8_t telemetryStepIndex = 0;
#endif

int16_t failsafeEvents = 0;
volatile int16_t failsafeCnt = 0;

int16_t rcData[RC_CHANS];    // interval [1000;2000]
int16_t rcCommand[4]; // interval [1000;2000] for THROTTLE and [-500;+500] for ROLL/PITCH/YAW
int16_t lookupPitchRollRC[5]; // lookup table for expo & RC rate PITCH+ROLL
int16_t lookupThrottleRC[11];     // lookup table for expo & mid THROTTLE
uint16_t rssi;               // range: [0;1023]

#if defined(SPEKTRUM)
volatile uint8_t spekFrameFlags;
volatile uint32_t spekTimeLast;
#endif

#if defined(OPENLRSv2MULTI)
uint8_t pot_P,pot_I; // OpenLRS onboard potentiometers for P and I trim or other usages
#endif

#if defined(AUTO_MIDCMD_TUNING)
uint8_t throttleChanged;
#endif

// **************
// gyro+acc IMU
// **************
 int16_t gyroData[3] = { 0, 0, 0 };
 int16_t gyroZero[3] = { 0, 0, 0 };
 int16_t angle[2] = { 0, 0 }; // absolute angle inclination in multiple of 0.1 degree    180 deg = 1800

// *************************
// motor and servo functions
// *************************
 int16_t axisPID[3];
 int16_t motor[NUMBER_MOTOR];
#if defined(SERVO)
 int16_t servo[8] = {1500,1500,1500,1500,1500,1500,1500,1500};
#endif

// ************************
// EEPROM Layout definition
// ************************
 uint8_t dynP8[3], dynD8[3];

 struct gConf global_conf;
 struct config conf;
 struct permLog plog;


void annexCode() { // this code is excetuted at each loop and won't interfere with control loop if it lasts less than 650 microseconds
  static uint32_t calibratedAccTime;
  uint16_t tmp,tmp2;
  uint8_t axis,prop1,prop2;

  prop2 = 128; // prop2 was 100, is 128 now
  if (rcData[THROTTLE]>1500) { // breakpoint is fix: 1500
    if (rcData[THROTTLE]<2000) {
    	prop2 -=  ((uint16_t)conf.dynThrPID*(rcData[THROTTLE]-1500)>>9); //  /512 instead of /500
    } else {
      prop2 -= conf.dynThrPID;
    }
  }

  for(axis=0;axis<3;axis++) {
    tmp = min(abs(rcData[axis]-MIDRC),500);
    #if defined(DEADBAND)
      if (tmp>DEADBAND) { tmp -= DEADBAND; }
      else { tmp=0; }
    #endif
    if(axis!=2) { //ROLL & PITCH
    	tmp2 = tmp>>7; // 500/128 = 3.9  => range [0;3]
    	rcCommand[axis] = lookupPitchRollRC[tmp2] + ((tmp-(tmp2<<7)) * (lookupPitchRollRC[tmp2+1]-lookupPitchRollRC[tmp2])>>7);
    	prop1 = 128-((uint16_t)conf.rollPitchRate*tmp>>9); // prop1 was 100, is 128 now -- and /512 instead of /500
    	prop1 = (uint16_t)prop1*prop2>>7; // prop1: max is 128   prop2: max is 128   result prop1: max is 128
    } else {      // YAW
      rcCommand[axis] = tmp;
      prop1 = 128-((uint16_t)conf.yawRate*tmp>>9); // prop1 was 100, is 128 now -- and /512 instead of /500
    }
    dynP8[axis] = (uint16_t)conf.P8[axis]*prop1>>7; // was /100, is /128 now
    dynD8[axis] = (uint16_t)conf.D8[axis]*prop1>>7; // was /100, is /128 now
    if (rcData[axis]<MIDRC) rcCommand[axis] = -rcCommand[axis];
  }
  tmp = constrain(rcData[THROTTLE],MINCHECK,2000);
  tmp = (uint32_t)(tmp-MINCHECK)*1000/(2000-MINCHECK); // [MINCHECK;2000] -> [0;1000]
  tmp2 = tmp/100;
  rcCommand[THROTTLE] = lookupThrottleRC[tmp2] + (tmp-tmp2*100) * (lookupThrottleRC[tmp2+1]-lookupThrottleRC[tmp2]) / 100; // [0;1000] -> expo -> [conf.minthrottle;MAXTHROTTLE]

  if(f.HEADFREE_MODE) { //to optimize
    float radDiff = (heading - headFreeModeHold) * 0.0174533f; // where PI/180 ~= 0.0174533
    float cosDiff = cos(radDiff);
    float sinDiff = sin(radDiff);
    int16_t rcCommand_PITCH = rcCommand[PITCH]*cosDiff + rcCommand[ROLL]*sinDiff;
    rcCommand[ROLL] =  rcCommand[ROLL]*cosDiff - rcCommand[PITCH]*sinDiff;
    rcCommand[PITCH] = rcCommand_PITCH;
  }

  #if defined(POWERMETER_HARD)
    uint16_t pMeterRaw;               // used for current reading
    static uint16_t psensorTimer = 0;
    if (! (++psensorTimer % PSENSORFREQ)) {
      pMeterRaw =  analogRead(PSENSORPIN);
      //lcdprint_int16(pMeterRaw); LCDcrlf();
      powerValue = ( conf.psensornull > pMeterRaw ? conf.psensornull - pMeterRaw : pMeterRaw - conf.psensornull); // do not use abs(), it would induce implicit cast to uint and overrun
      if ( powerValue < 333) {  // only accept reasonable values. 333 is empirical
      #ifdef LCD_TELEMETRY
        if (powerValue > powerMax) powerMax = powerValue;
      #endif
      } else {
        powerValue = 333;
      }
      pMeter[PMOTOR_SUM] += (uint32_t) powerValue;
    }
  #endif
  #if defined(BUZZER)
    #if defined(VBAT)
      static uint8_t vbatTimer = 0;
      static uint8_t ind = 0;
      uint16_t vbatRaw = 0;
      static uint16_t vbatRawArray[8];
      if (! (++vbatTimer % VBATFREQ)) {
        vbatRawArray[(ind++)%8] = analogRead(V_BATPIN);
        for (uint8_t i=0;i<8;i++) vbatRaw += vbatRawArray[i];
        vbat = (vbatRaw*2) / conf.vbatscale; // result is Vbatt in 0.1V steps
      }
    #endif
    alarmHandler(); // external buzzer routine that handles buzzer events globally now
  #endif

   #if defined(RX_RSSI) || defined(RX_RSSI_FRSky)
    static uint8_t sig = 0;
    uint16_t rssiRaw = 0;
    static uint16_t rssiRawArray[8];
    rssiRawArray[(sig++)%8] = analogRead(RX_RSSI_PIN);
    for (uint8_t i=0;i<8;i++) rssiRaw += rssiRawArray[i];
 #if defined (RX_RSSI_FRSky)
   rssi = (rssiRaw*3)/16;
 #else
      rssi = rssiRaw / 8;
 #endif
  #endif

  if ( (calibratingA>0 && ACC ) || (calibratingG>0) ) { // Calibration phasis
    LEDPIN_TOGGLE;
  } else {
    if (f.ACC_CALIBRATED) {LEDPIN_OFF;}
    if (f.ARMED) {LEDPIN_ON;}
  }

  #if defined(LED_RING)
    static uint32_t LEDTime;
    if ( currentTime > LEDTime ) {
      LEDTime = currentTime + 50000;
      i2CLedRingState();
    }
  #endif

  #if defined(LED_FLASHER)
    auto_switch_led_flasher();
  #endif

  if ( currentTime > calibratedAccTime ) {
    if (! f.SMALL_ANGLES_25) {
      // the multi uses ACC and is not calibrated or is too much inclinated
      f.ACC_CALIBRATED = 0;
      LEDPIN_TOGGLE;
      calibratedAccTime = currentTime + 100000;
    } else {
      f.ACC_CALIBRATED = 1;
    }
  }

  serialCom();

  #if defined(POWERMETER)
    intPowerMeterSum = (pMeter[PMOTOR_SUM]/conf.pleveldiv);
    intPowerTrigger1 = conf.powerTrigger1 * PLEVELSCALE;
  #endif

  #ifdef LCD_TELEMETRY_AUTO
    static char telemetryAutoSequence []  = LCD_TELEMETRY_AUTO;
    static uint8_t telemetryAutoIndex = 0;
    static uint16_t telemetryAutoTimer = 0;
    if ( (telemetry_auto) && (! (++telemetryAutoTimer % LCD_TELEMETRY_AUTO_FREQ) )  ){
      telemetry = telemetryAutoSequence[++telemetryAutoIndex % strlen(telemetryAutoSequence)];
      LCDclear(); // make sure to clear away remnants
    }
  #endif
  #ifdef LCD_TELEMETRY
    static uint16_t telemetryTimer = 0;
    if (! (++telemetryTimer % LCD_TELEMETRY_FREQ)) {
      #if (LCD_TELEMETRY_DEBUG+0 > 0)
        telemetry = LCD_TELEMETRY_DEBUG;
      #endif
      if (telemetry) lcd_telemetry();
    }
  #endif

  #if GPS & defined(GPS_LED_INDICATOR)       // modified by MIS to use STABLEPIN LED for number of sattelites indication
    static uint32_t GPSLEDTime;              // - No GPS FIX -> LED blink at speed of incoming GPS frames
    static uint8_t blcnt;                    // - Fix and sat no. bellow 5 -> LED off
    if(currentTime > GPSLEDTime) {           // - Fix and sat no. >= 5 -> LED blinks, one blink for 5 sat, two blinks for 6 sat, three for 7 ...
      if(f.GPS_FIX && GPS_numSat >= 5) {
        if(++blcnt > 2*GPS_numSat) blcnt = 0;
        GPSLEDTime = currentTime + 150000;
        if(blcnt >= 10 && ((blcnt%2) == 0)) {STABLEPIN_ON;} else {STABLEPIN_OFF;}
      }else{
        if((GPS_update == 1) && !f.GPS_FIX) {STABLEPIN_ON;} else {STABLEPIN_OFF;}
        blcnt = 0;
      }
    }
  #endif

  #if defined(LOG_VALUES) && (LOG_VALUES >= 2)
    if (cycleTime > cycleTimeMax) cycleTimeMax = cycleTime; // remember highscore
    if (cycleTime < cycleTimeMin) cycleTimeMin = cycleTime; // remember lowscore
  #endif
  if (f.ARMED)  {
    #if defined(LCD_TELEMETRY) || defined(ARMEDTIMEWARNING) || defined(LOG_PERMANENT)
      armedTime += (uint32_t)cycleTime;
    #endif
    #if defined(VBAT)
      if ( (vbat > conf.no_vbat) && (vbat < vbatMin) ) vbatMin = vbat;
    #endif
    #ifdef LCD_TELEMETRY
      #if BARO
        if ( (BaroAlt > BAROaltMax) ) BAROaltMax = BaroAlt;
      #endif
    #endif
  }
}

void setup() {
  SerialOpen(0,SERIAL0_COM_SPEED);
  SerialOpen(1,SERIAL1_COM_SPEED);
  SerialOpen(2,SERIAL2_COM_SPEED);
  SerialOpen(3,SERIAL3_COM_SPEED);
  LEDPIN_PINMODE;
  POWERPIN_PINMODE;
  BUZZERPIN_PINMODE;
  STABLEPIN_PINMODE;
  POWERPIN_OFF;
  initOutput();
  #ifdef MULTIPLE_CONFIGURATION_PROFILES
    for(global_conf.currentSet=0; global_conf.currentSet<3; global_conf.currentSet++) {  // check all settings integrity
      readEEPROM();
    }
  #else
    global_conf.currentSet=0;
    readEEPROM();
  #endif
  readGlobalSet();
  readEEPROM();                                    // load current setting data
  blinkLED(2,40,global_conf.currentSet+1);
  configureReceiver();
  #if defined (PILOTLAMP)
    PL_INIT;
  #endif
  #if defined(OPENLRSv2MULTI)
    initOpenLRS();
  #endif
  initSensors();
  #if defined(I2C_GPS) || defined(GPS_SERIAL) || defined(GPS_FROM_OSD)
    GPS_set_pids();
  #endif
  previousTime = micros();
  #if defined(GIMBAL)
   calibratingA = 512;
  #endif
  calibratingG = 512;
  calibratingB = 200;  // 10 seconds init_delay + 200 * 25 ms = 15 seconds before ground pressure settles
  #if defined(POWERMETER)
    for(uint8_t i=0;i<=PMOTOR_SUM;i++)
      pMeter[i]=0;
  #endif
  /************************************/
  #if defined(GPS_SERIAL)
    GPS_SerialInit();
    for(uint8_t i=0;i<=5;i++){
      GPS_NewData();
      LEDPIN_ON
      delay(20);
      LEDPIN_OFF
      delay(80);
    }
    if(!GPS_Present){
      SerialEnd(GPS_SERIAL);
      SerialOpen(0,SERIAL0_COM_SPEED);
    }
    #if !defined(GPS_PROMINI)
      GPS_Present = 1;
    #endif
    GPS_Enable = GPS_Present;
  #endif
  /************************************/

  #if defined(LCD_ETPP) || defined(LCD_LCD03) || defined(OLED_I2C_128x64) || defined(LCD_TELEMETRY_STEP)
    initLCD();
  #endif
  #ifdef LCD_TELEMETRY_DEBUG
    telemetry_auto = 1;
  #endif
  #ifdef LCD_CONF_DEBUG
    configurationLoop();
  #endif
  #ifdef LANDING_LIGHTS_DDR
    init_landing_lights();
  #endif
  ADCSRA |= _BV(ADPS2) ; ADCSRA &= ~_BV(ADPS1); ADCSRA &= ~_BV(ADPS0); // this speeds up analogRead without loosing too much resolution: http://www.arduino.cc/cgi-bin/yabb2/YaBB.pl?num=1208715493/11
  #if defined(LED_FLASHER)
    init_led_flasher();
    led_flasher_set_sequence(LED_FLASHER_SEQUENCE);
  #endif
  f.SMALL_ANGLES_25=1; // important for gyro only conf
  #ifdef LOG_PERMANENT
	#ifndef LOG_PERMANENT_SD_ONLY
    // read last stored set
    readPLog();
	#endif
	#ifdef MWI_SDCARD
	readPLogFromSD();
	#endif
    plog.lifetime += plog.armed_time / 1000000;
    plog.start++;         // #powercycle/reset/initialize events
    // dump plog data to terminal
    #ifdef LOG_PERMANENT_SHOW_AT_STARTUP
      dumpPLog(0);
    #endif
    plog.armed_time = 0;   // lifetime in seconds
    //plog.running = 0;       // toggle on arm & disarm to monitor for clean shutdown vs. powercut
  #endif
  #ifdef MWI_SDCARD
	init_SD();
  #endif

  debugmsg_append_str("initialization completed\n");
}

void go_arm() {
  if(calibratingG == 0 && f.ACC_CALIBRATED
  #if defined(FAILSAFE)
    && failsafeCnt < 2
  #endif
  #if GPS
    && f.GPS_HOME_MODE == 0
  #endif
    ) {
    if(!f.ARMED) { // arm now!
      f.ARMED = 1;
      headFreeModeHold = heading;
      #if defined(VBAT)
        if (vbat > conf.no_vbat) vbatMin = vbat;
      #endif
	  #if BARO
		calibratingB=10;
		#ifdef LCD_TELEMETRY // reset some values when arming
           BAROaltMax = BaroAlt;
        #endif
      #endif
      #ifdef LOG_PERMANENT
        plog.arm++;           // #arm events
        plog.running = 1;       // toggle on arm & disarm to monitor for clean shutdown vs. powercut
        #ifndef LOG_PERMANENT_SD_ONLY
		// write now.
        writePLog();
		#endif
		#ifdef MWI_SDCARD
		  writePLogToSD();
		#endif
      #endif
	  #ifdef AUTO_TAKE_OFF
		if (f.BARO_MODE) {
			f.ATO = 1;
		} else {
			f.ATO = 0;
		}
	  #else
		f.ATO = 0;
	  #endif
    }
  } else if(!f.ARMED) {
    blinkLED(2,255,1);
    alarmArray[8] = 1;
  }
}
void go_disarm() {
  if (f.ARMED) {
    f.ARMED = 0;
	#if defined(AUTO_MIDCMD_TUNING)
	writeParams(0);
	#endif
    #ifdef LOG_PERMANENT
      plog.disarm++;        // #disarm events
      plog.armed_time = armedTime ;   // lifetime in seconds
      if (failsafeEvents) plog.failsafe++;      // #acitve failsafe @ disarm
      if (i2c_errors_count > 10) plog.i2c++;           // #i2c errs @ disarm
      plog.running = 0;       // toggle @ arm & disarm to monitor for clean shutdown vs. powercut
        #ifndef LOG_PERMANENT_SD_ONLY
		// write now.
        writePLog();
		#endif
		#ifdef MWI_SDCARD
		  writePLogToSD();
		#endif
    #endif
  }
}
void servos2Neutral() {
  #ifdef TRI
    servo[5] = 1500; // we center the yaw servo in conf mode
    writeServos();
  #endif
  #ifdef FLYING_WING
    servo[0]  = conf.wing_left_mid;
    servo[1]  = conf.wing_right_mid;
    writeServos();
  #endif
  #ifdef AIRPLANE
    for(uint8_t i = 4; i<7 ;i++) servo[i] = 1500;
    writeServos();
  #endif
  #ifdef HELICOPTER
    servo[5] = YAW_CENTER;
    servo[3] = servo[4] = servo[6] = 1500;
    writeServos();
  #endif
}

// ******** Main Loop *********
void loop () {
  static uint8_t rcDelayCommand; // this indicates the number of time (multiple of RC measurement at 50Hz) the sticks must be maintained to run or switch off motors
  static uint8_t rcSticks;       // this hold sticks position for command combos
  uint8_t axis,i;
  int16_t error,errorAngle;
  int16_t delta,deltaSum;
  int16_t PTerm,ITerm,DTerm;
  int16_t PTermACC = 0 , ITermACC = 0 , PTermGYRO = 0 , ITermGYRO = 0;
  static int16_t lastGyro[3] = {0,0,0};
  static int16_t delta1[3],delta2[3];
  static int16_t errorGyroI[3] = {0,0,0};
  static int16_t errorAngleI[2] = {0,0};
  static uint32_t rcTime  = 0;
  static int16_t initialThrottleHold;
#ifdef CYCLETIME_FIXATED
  static uint32_t timestamp_fixated = 0;
#endif
  #if defined(LOG_GPS_POSITION)
	static uint32_t logGpsTime = 0;
  #endif
  #if defined(SMOOTH_GPS_HOLD)
	static uint32_t gpsHoldActivationTime = 0;
  #endif
#if defined(AUTO_MIDCMD_TUNING)
	static uint32_t timeCounter = 0;
#endif
  #if defined(SPEKTRUM)
    if (spekFrameFlags == 0x01) readSpektrum();
  #endif

  #if defined(OPENLRSv2MULTI)
    Read_OpenLRS_RC();
  #endif

  if (currentTime > rcTime ) { // 50Hz
    rcTime = currentTime + 20000;
    computeRC();
    // Failsafe routine - added by MIS
    #if defined(FAILSAFE)
      if ( failsafeCnt > (5*FAILSAFE_DELAY) && f.ARMED) {                  // Stabilize, and set Throttle to specified level
        for(i=0; i<3; i++) rcData[i] = MIDRC;                               // after specified guard time after RC signal is lost (in 0.1sec)
        rcData[THROTTLE] = conf.failsafe_throttle;
        if (failsafeCnt > 5*(FAILSAFE_DELAY+FAILSAFE_OFF_DELAY)) {          // Turn OFF motors after specified Time (in 0.1sec)
          go_disarm();     // This will prevent the copter to automatically rearm if failsafe shuts it down and prevents
          f.OK_TO_ARM = 0; // to restart accidentely by just reconnect to the tx - you will have to switch off first to rearm
        }
        failsafeEvents++;
      }
      if ( failsafeCnt > (5*FAILSAFE_DELAY) && !f.ARMED) {  //Turn of "Ok To arm to prevent the motors from spinning after repowering the RX with low throttle and aux to arm
          go_disarm();     // This will prevent the copter to automatically rearm if failsafe shuts it down and prevents
          f.OK_TO_ARM = 0; // to restart accidentely by just reconnect to the tx - you will have to switch off first to rearm
      }
      failsafeCnt++;
    #endif
    // end of failsafe routine - next change is made with RcOptions setting

    // ------------------ STICKS COMMAND HANDLER --------------------
    // checking sticks positions
    uint8_t stTmp = 0;
    for(i=0;i<4;i++) {
      stTmp >>= 2;
      if(rcData[i] > MINCHECK) stTmp |= 0x80;      // check for MIN
      if(rcData[i] < MAXCHECK) stTmp |= 0x40;      // check for MAX
    }
    if(stTmp == rcSticks) {
      if(rcDelayCommand<250) rcDelayCommand++;
    } else rcDelayCommand = 0;
    rcSticks = stTmp;

    // perform actions
    if (rcData[THROTTLE] <= MINCHECK) {            // THROTTLE at minimum
      errorGyroI[ROLL] = 0; errorGyroI[PITCH] = 0; errorGyroI[YAW] = 0;
      errorAngleI[ROLL] = 0; errorAngleI[PITCH] = 0;
      if (conf.activate[BOXARM] > 0) {             // Arming/Disarming via ARM BOX
        if ( rcOptions[BOXARM] && f.OK_TO_ARM ) go_arm(); else if (f.ARMED) go_disarm();
      }
    }
    if(rcDelayCommand == 20) {
      if(f.ARMED) {                   // actions during armed
        #ifdef ALLOW_ARM_DISARM_VIA_TX_YAW
          if (conf.activate[BOXARM] == 0 && rcSticks == THR_LO + YAW_LO + PIT_CE + ROL_CE) go_disarm();    // Disarm via YAW
        #endif
        #ifdef ALLOW_ARM_DISARM_VIA_TX_ROLL
          if (conf.activate[BOXARM] == 0 && rcSticks == THR_LO + YAW_CE + PIT_CE + ROL_LO) go_disarm();    // Disarm via ROLL
        #endif
      } else {                        // actions during not armed
        i=0;
        if (rcSticks == THR_LO + YAW_LO + PIT_LO + ROL_CE) {    // GYRO calibration
          calibratingG=512;
          #if GPS
            GPS_reset_home_position();
          #endif
          #if BARO
            calibratingB=10;  // calibrate baro to new ground level (10 * 25 ms = ~250 ms non blocking)
          #endif
        }
        #if defined(INFLIGHT_ACC_CALIBRATION)
         else if (rcSticks == THR_LO + YAW_LO + PIT_HI + ROL_HI) {    // Inflight ACC calibration START/STOP
            if (AccInflightCalibrationMeasurementDone){                // trigger saving into eeprom after landing
              AccInflightCalibrationMeasurementDone = 0;
              AccInflightCalibrationSavetoEEProm = 1;
            }else{
              AccInflightCalibrationArmed = !AccInflightCalibrationArmed;
              #if defined(BUZZER)
               if (AccInflightCalibrationArmed) alarmArray[0]=2; else   alarmArray[0]=3;
              #endif
            }
         }
        #endif
        #ifdef MULTIPLE_CONFIGURATION_PROFILES
          if      (rcSticks == THR_LO + YAW_LO + PIT_CE + ROL_LO) i=1;    // ROLL left  -> Profile 1
          else if (rcSticks == THR_LO + YAW_LO + PIT_HI + ROL_CE) i=2;    // PITCH up   -> Profile 2
          else if (rcSticks == THR_LO + YAW_LO + PIT_CE + ROL_HI) i=3;    // ROLL right -> Profile 3
          if(i) {
            global_conf.currentSet = i-1;
            writeGlobalSet(0);
            readEEPROM();
            blinkLED(2,40,i);
            alarmArray[0] = i;
          }
        #endif
        if (rcSticks == THR_LO + YAW_HI + PIT_HI + ROL_CE) {            // Enter LCD config
          #if defined(LCD_CONF)
            configurationLoop(); // beginning LCD configuration
          #endif
          previousTime = micros();
        }
        #ifdef ALLOW_ARM_DISARM_VIA_TX_YAW
          else if (conf.activate[BOXARM] == 0 && rcSticks == THR_LO + YAW_HI + PIT_CE + ROL_CE) go_arm();      // Arm via YAW
        #endif
        #ifdef ALLOW_ARM_DISARM_VIA_TX_ROLL
          else if (conf.activate[BOXARM] == 0 && rcSticks == THR_LO + YAW_CE + PIT_CE + ROL_HI) go_arm();      // Arm via ROLL
        #endif
        #ifdef LCD_TELEMETRY_AUTO
          else if (rcSticks == THR_LO + YAW_CE + PIT_HI + ROL_LO) {              // Auto telemetry ON/OFF
            if (telemetry_auto) {
              telemetry_auto = 0;
              telemetry = 0;
            } else
              telemetry_auto = 1;
          }
        #endif
        #ifdef LCD_TELEMETRY_STEP
          else if (rcSticks == THR_LO + YAW_CE + PIT_HI + ROL_HI) {              // Telemetry next step
            telemetry = telemetryStepSequence[++telemetryStepIndex % strlen(telemetryStepSequence)];
            #ifdef OLED_I2C_128x64
              if (telemetry != 0) i2c_OLED_init();
            #endif
            LCDclear();
          }
        #endif
        else if (rcSticks == THR_HI + YAW_LO + PIT_LO + ROL_CE) calibratingA=512;     // throttle=max, yaw=left, pitch=min
        else if (rcSticks == THR_HI + YAW_HI + PIT_LO + ROL_CE) f.CALIBRATE_MAG = 1;  // throttle=max, yaw=right, pitch=min
        i=0;
        if      (rcSticks == THR_HI + YAW_CE + PIT_HI + ROL_CE) {conf.angleTrim[PITCH]+=2; i=1;}
        else if (rcSticks == THR_HI + YAW_CE + PIT_LO + ROL_CE) {conf.angleTrim[PITCH]-=2; i=1;}
        else if (rcSticks == THR_HI + YAW_CE + PIT_CE + ROL_HI) {conf.angleTrim[ROLL] +=2; i=1;}
        else if (rcSticks == THR_HI + YAW_CE + PIT_CE + ROL_LO) {conf.angleTrim[ROLL] -=2; i=1;}
        if (i) {
          writeParams(1);
          rcDelayCommand = 0;    // allow autorepetition
          #if defined(LED_RING)
            blinkLedRing();
          #endif
        }
      }
    }
    #if defined(LED_FLASHER)
      led_flasher_autoselect_sequence();
    #endif

    #if defined(INFLIGHT_ACC_CALIBRATION)
      if (AccInflightCalibrationArmed && f.ARMED && rcData[THROTTLE] > MINCHECK && !rcOptions[BOXARM] ){ // Copter is airborne and you are turning it off via boxarm : start measurement
        InflightcalibratingA = 50;
        AccInflightCalibrationArmed = 0;
      }
      if (rcOptions[BOXCALIB]) {      // Use the Calib Option to activate : Calib = TRUE Meausrement started, Land and Calib = 0 measurement stored
        if (!AccInflightCalibrationActive && !AccInflightCalibrationMeasurementDone){
          InflightcalibratingA = 50;
        }
      }else if(AccInflightCalibrationMeasurementDone && !f.ARMED){
        AccInflightCalibrationMeasurementDone = 0;
        AccInflightCalibrationSavetoEEProm = 1;
      }
    #endif

    uint16_t auxState = 0;
    for(i=0;i<4;i++)
      auxState |= (rcData[AUX1+i]<1300)<<(3*i) | (1300<rcData[AUX1+i] && rcData[AUX1+i]<1700)<<(3*i+1) | (rcData[AUX1+i]>1700)<<(3*i+2);
    for(i=0;i<CHECKBOXITEMS;i++)
      rcOptions[i] = (auxState & conf.activate[i])>0;

	#if defined(FAILSAFE) && defined(FAILSAFE_RTH)
	/*if (failsafeCnt > 5*FAILSAFE_DELAY) {
		rcOptions[BOXGPSHOME] = 1;
		rcOptions[BOXBARO] = 1;
		rcOptions[BOXVARIO] = 1;
		rcOptions[BOXMAG] = 1;
	}*/
		#define FAILSAFE_TEST || (failsafeCnt > 5*FAILSAFE_DELAY)
	#else
		#define FAILSAFE_TEST
	#endif

    // note: if FAILSAFE is disable, failsafeCnt > 5*FAILSAFE_DELAY is always false
    #if ACC
      if ( rcOptions[BOXANGLE] || (failsafeCnt > 5*FAILSAFE_DELAY) ) {
        // bumpless transfer to Level mode
        if (!f.ANGLE_MODE) {
          errorAngleI[ROLL] = 0; errorAngleI[PITCH] = 0;
          f.ANGLE_MODE = 1;
        }
      } else {
        // failsafe support
        f.ANGLE_MODE = 0;
      }
      if ( rcOptions[BOXHORIZON] ) {
        f.ANGLE_MODE = 0;
        if (!f.HORIZON_MODE) {
          errorAngleI[ROLL] = 0; errorAngleI[PITCH] = 0;
          f.HORIZON_MODE = 1;
        }
      } else {
        f.HORIZON_MODE = 0;
      }
    #endif

    if (rcOptions[BOXARM] == 0) f.OK_TO_ARM = 1;
    #if !defined(GPS_LED_INDICATOR)
      if (f.ANGLE_MODE || f.HORIZON_MODE) {STABLEPIN_ON;} else {STABLEPIN_OFF;}
    #endif

	#if BARO
		if (rcOptions[BOXBARO] FAILSAFE_TEST) {
			if (!f.BARO_MODE) {
				f.BARO_MODE = 1;
				fGPS.autoLanding = 0;
				AltHold = EstAlt;
			#if defined(MIDDLECOMMAND)
				initialThrottleHold = conf.middleCmd;
			#if defined(AUTO_MIDCMD_TUNING)
				timeCounter = millis() + AUTO_MIDCMD_TUNING_TIME;
			#endif
			#else 	//MIDDLECOMMAND
				initialThrottleHold = rcCommand[THROTTLE];
			#endif  //MIDDLECOMMAND
				errorAltitudeI = 0;
				BaroPID = 0;
			}
        } else {
            f.BARO_MODE = 0;
            #if GPS
	      fGPS.autoLanding = 0;
	      fGPS.ascending = 0;
            #endif
        }
      #ifdef VARIOMETER
        if (rcOptions[BOXVARIO] FAILSAFE_TEST) {
          if (!f.VARIO_MODE) {
            f.VARIO_MODE = 1;
          }
        } else {
          f.VARIO_MODE = 0;
        }
      #endif
    #endif
    #if MAG
      if (rcOptions[BOXMAG] FAILSAFE_TEST) {
        if (!f.MAG_MODE) {
          f.MAG_MODE = 1;
          magHold = heading;
        }
      } else {
        f.MAG_MODE = 0;
      }
      if (rcOptions[BOXHEADFREE]) {
        if (!f.HEADFREE_MODE) {
          f.HEADFREE_MODE = 1;
        }
      } else {
        f.HEADFREE_MODE = 0;
      }
      if (rcOptions[BOXHEADADJ]) {
        headFreeModeHold = heading; // acquire new heading
      }
    #endif

    #if GPS
      static uint8_t GPSNavReset = 1;
      if (f.GPS_FIX && GPS_numSat >= 5) {
        if (rcOptions[BOXGPSHOME] FAILSAFE_TEST) {  // if both GPS_HOME & GPS_HOLD are checked => GPS_HOME is the priority
          if (!f.GPS_HOME_MODE)  {
            f.GPS_HOME_MODE = 1;
            f.GPS_HOLD_MODE = 0;
            f.GPS_MISSION_MODE = 0;
            f.GPS_POI_MODE = 0;
			#if defined(SMOOTH_GPS_HOLD)
				f.SMOOTH_HOLD = 1;
			#endif
            GPSNavReset = 0;
              if (rcOptions[BOXBARO] && NAV_ALT_CTRL && EstAlt < (NAV_RTH_ALTITUDE-50)) {  // if both GPS_HOME and BARO are checked, we try to go up and do autolanding
                GPS_hold[LAT] = GPS_coord[LAT];
                GPS_hold[LON] = GPS_coord[LON];
                GPS_set_next_wp(&GPS_hold[LAT],&GPS_hold[LON]);
                AltHold = NAV_RTH_ALTITUDE;
                fGPS.ascending = 1;
              } else {	// No BARO, so just a RTH
                GPS_set_next_wp(&GPS_home[LAT],&GPS_home[LON]);
              }
            nav_mode    = NAV_MODE_WP;
          }
        } else {
          f.GPS_HOME_MODE = 0;
          if (rcOptions[BOXGPSHOLD] && abs(rcCommand[ROLL])< AP_MODE && abs(rcCommand[PITCH]) < AP_MODE
			#ifdef GPS_HOLD_MIN_ALT
        		  && EstAlt > GPS_HOLD_MIN_ALT
			#endif
          	  ) {
            if (!f.GPS_HOLD_MODE) {
              f.GPS_HOLD_MODE = 1;
              GPSNavReset = 0;
                GPS_hold[LAT] = GPS_coord[LAT];
                GPS_hold[LON] = GPS_coord[LON];
                GPS_set_next_wp(&GPS_hold[LAT],&GPS_hold[LON]);
                nav_mode = NAV_MODE_POSHOLD;

			  #if defined(SMOOTH_GPS_HOLD)
				gpsHoldActivationTime = millis() + SMOOTH_GPS_HOLD * 1000;
			  #endif
            }
          } else {
        	  f.GPS_HOLD_MODE = 0;
        	if (rcOptions[BOXPOI]) {	// POI option, compute the distance between POI and tries to keep the distance
        		if (abs(rcCommand[PITCH]) < AP_MODE) {
					if (!f.GPS_POI_MODE) {
		        		f.GPS_POI_MODE = 1;
						GPSNavReset = 0;
						GPS_hold[LAT] = GPS_coord[LAT];
						GPS_hold[LON] = GPS_coord[LON];
						GPS_set_next_wp(&GPS_hold[LAT],&GPS_hold[LON]);
						nav_mode = NAV_MODE_POI;
					}
        		} else {
					// Get distance to POI and set magHold to point the front of copter to the POI point
					int32_t bearing;
					GPS_distance_cm_bearing(&GPS_coord[LAT], &GPS_coord[LON], &GPS_hold[LAT], &GPS_hold[LON], &GPS_distanceToPOI, &bearing);
        		}
        	} else {
        		f.GPS_POI_MODE = 0;
				#if defined(SDCARD_MISSION)
					if (rcOptions[BOXMISSION]) {
						debugmsg_append_str("mission");
						if (fGPS.waypointReached || fGPS.waypointReaded == 0) {
							fGPS.waypointReaded = 1;
							if (getNextWp() == 0) {
								GPS_set_next_wp(&GPS_mission[LAT],&GPS_mission[LON]);
								AltHold = GPS_mission[ALT];
								magHold = GPS_mission[HEAD];
							}  else {
								GPS_set_next_wp(&GPS_home[LAT],&GPS_home[LON]);
							}
						}

						f.GPS_MISSION_MODE = 1;
						f.GPS_HOME_MODE = 0;
						f.GPS_HOLD_MODE = 0;
						nav_mode    = NAV_MODE_WP;
					} else {
						f.GPS_MISSION_MODE = 0;
						fGPS.waypointReached = 0;
						fGPS.waypointReaded = 0;
				#endif
				f.GPS_HOLD_MODE = 0;
				// both boxes are unselected here, nav is reset if not already done
				if (GPSNavReset == 0 ) {
				  GPSNavReset = 1;
				  GPS_reset_nav();
				}
				#if defined(SDCARD_MISSION)
			  }
			}
			#endif
          }
        }
      } else {
        f.GPS_HOME_MODE = 0;
        f.GPS_MISSION_MODE = 0;
        f.GPS_HOLD_MODE = 0;
        f.GPS_POI_MODE = 0;
        #if !defined(I2C_GPS)
          nav_mode = NAV_MODE_NONE;
        #endif
      }
    #endif

    #if defined(FIXEDWING) || defined(HELICOPTER)
      if (rcOptions[BOXPASSTHRU]) {f.PASSTHRU_MODE = 1;}
      else {f.PASSTHRU_MODE = 0;}
    #endif

  } else { // not in rc loop
    static uint8_t taskOrder=0; // never call all functions in the same loop, to avoid high delay spikes
    if(taskOrder>4) taskOrder-=5;
    switch (taskOrder) {
      case 0:
        taskOrder++;
        #if MAG
          if (Mag_getADC()) break; // max 350 µs (HMC5883) // only break when we actually did something
        #endif
      case 1:
        taskOrder++;
        #if BARO
          if (Baro_update() != 0 ) break;
        #endif
      case 2:
        taskOrder++;
        #if BARO
          if (getEstimatedAltitude() !=0) break;
        #endif
      case 3:
        taskOrder++;
        #if GPS
          if(GPS_Enable) GPS_NewData();
          break;
        #endif
      case 4:
        taskOrder++;
        #if SONAR
          Sonar_update();debug[2] = sonarAlt;
        #endif
        #ifdef LANDING_LIGHTS_DDR
          auto_switch_landing_lights();
        #endif
        #ifdef VARIOMETER
          if (f.VARIO_MODE) vario_signaling();
        #endif
        break;
    }
  }

  #if defined(LOG_GPS_POSITION)
	if (currentTime > logGpsTime && f.ARMED == 1 && f.GPS_FIX == 1) {
		logGpsTime = currentTime + (LOG_GPS_POSITION*1000000);

		writeGPSLog(GPS_coord[LAT], GPS_coord[LON], EstAlt);
	}
  #endif

  computeIMU();
  // Measure loop rate just afer reading the sensors
  currentTime = micros();
  cycleTime = currentTime - previousTime;
  previousTime = currentTime;

  #ifdef CYCLETIME_FIXATED
    if (conf.cycletime_fixated) {
      if ((micros()-timestamp_fixated)>conf.cycletime_fixated) {
      } else {
         while((micros()-timestamp_fixated)<conf.cycletime_fixated) ; // waste away
      }
      timestamp_fixated=micros();
    }
  #endif
  //***********************************
  //**** Experimental FlightModes *****
  //***********************************
  #if defined(ACROTRAINER_MODE)
    if(f.ANGLE_MODE){
      if (abs(rcCommand[ROLL]) + abs(rcCommand[PITCH]) >= ACROTRAINER_MODE ) {
        f.ANGLE_MODE=0;
        f.HORIZON_MODE=0;
        f.MAG_MODE=0;
        f.BARO_MODE=0;
        f.GPS_HOME_MODE=0;
        f.GPS_HOLD_MODE=0;
      }
    }
  #endif

 //***********************************

  #if MAG
    if (abs(rcCommand[YAW]) <70 && f.MAG_MODE) {
      int16_t dif = heading - magHold;
      if (dif <= - 180) dif += 360;
      if (dif >= + 180) dif -= 360;
      if ( f.SMALL_ANGLES_25 ) rcCommand[YAW] -= dif*conf.P8[PIDMAG]>>5;
    } else magHold = heading;
  #endif


  #if BARO
    if (f.BARO_MODE) {
      static uint8_t isAltHoldChanged = 0;
      #if defined(ALTHOLD_FAST_THROTTLE_CHANGE)
        if (abs(rcCommand[THROTTLE]-initialThrottleHold) > ALT_HOLD_THROTTLE_NEUTRAL_ZONE) {
          errorAltitudeI = 0;
          isAltHoldChanged = 1;
          rcCommand[THROTTLE] += (rcCommand[THROTTLE] > initialThrottleHold) ? -ALT_HOLD_THROTTLE_NEUTRAL_ZONE : ALT_HOLD_THROTTLE_NEUTRAL_ZONE;
        } else {
			#if defined(AUTO_MIDCMD_TUNING)
			if (timeCounter >= millis()) {
				if (errorAltitudeI > AUTO_MIDCMD_TUNING_MIN_I) {  // Don't do anything if I isn't representative
					conf.middleCmd = initialThrottleHold = errorAltitudeI >> AUTO_MIDCMD_TUNING_DIV;
				}
				timeCounter = millis() + AUTO_MIDCMD_TUNING_TIME;
			}
			#endif
          if (isAltHoldChanged) {
            AltHold = EstAlt;
            isAltHoldChanged = 0;
          }

		  #if GPS
			static int16_t AltHoldCorr = 0;
			if (abs(NAV_RTH_ALTITUDE-EstAlt) < 50 && fGPS.ascending == 1) {
				fGPS.ascending = 0; // Tell the GPS code that the altitude is OK
				GPS_set_next_wp(&GPS_home[LAT],&GPS_home[LON]);
			}
			if (fGPS.autoLanding == 1) {
				AltHoldCorr -= NAV_DESCENT_SPEED;

				if (EstAlt < 20 && abs(vario) < 5 ) {  // Altitude is low and no movement detected
				   fGPS.autoLanding = 0;				// Ok, we have finished
				   go_disarm();						// Disarm
				}
			    if(abs(AltHoldCorr) > 500) {
					AltHold += AltHoldCorr/500;
					AltHoldCorr %= 500;
				}
			  errorAltitudeI = 0;
			  isAltHoldChanged = 1;
			}
		  #endif
          rcCommand[THROTTLE] = initialThrottleHold + BaroPID;
        }
      #else
		static int16_t AltHoldCorr = 0;
		#if GPS
		if (fGPS.ascending == 1) {
			rcCommand[THROTTLE] = initialThrottleHold;
		}
		if (abs(NAV_RTH_ALTITUDE-EstAlt) < 50 && fGPS.ascending == 1) {
			fGPS.ascending = 0; // Tell the GPS code that the altitude is OK
			GPS_set_next_wp(&GPS_home[LAT], &GPS_home[LON]);
		}


		if (abs(rcCommand[THROTTLE]-
				#ifdef AUTO_MIDCMD_TUNING
					MIDDLECOMMAND
				#else
					initialThrottleHold
				#endif
		) > ALT_HOLD_THROTTLE_NEUTRAL_ZONE || fGPS.autoLanding == 1) {
			// Slowly increase/decrease AltHold proportional to stick movement ( +100 throttle gives ~ +50 cm in 1 second with cycle time about 3-4ms)
			if (fGPS.autoLanding == 1) {
				AltHoldCorr -= NAV_DESCENT_SPEED;

				if (EstAlt < 20 && abs(vario) < 5) { // Altitude is low and no movement detected
					fGPS.autoLanding = 0;				// Ok, we have finished
					go_disarm();						// Disarm
				}
			} else {
				#ifdef AUTO_MIDCMD_TUNING
				AltHoldCorr += rcCommand[THROTTLE] - MIDDLECOMMAND;
				#else
				AltHoldCorr += rcCommand[THROTTLE] - initialThrottleHold;
				#endif
			}
			if (abs(AltHoldCorr) > 500) {
				AltHold += AltHoldCorr / 500;
				AltHoldCorr %= 500;
			}
			errorAltitudeI = 0;
			isAltHoldChanged = 1;
		} else {
			#if defined(AUTO_MIDCMD_TUNING)
			if (timeCounter <= millis()) {
				if ((abs(errorAltitudeI) > AUTO_MIDCMD_TUNING_MIN_I) && fGPS.ascending != 1 && fGPS.autoLanding != 1 && EstAlt > 150) { // Don't do anything if I isn't representative
					conf.middleCmd = (initialThrottleHold += errorAltitudeI >> AUTO_MIDCMD_TUNING_DIV);
					//errorAltitudeI = 0;
				}
				timeCounter = millis() + AUTO_MIDCMD_TUNING_TIME;
			}
			#endif   // AUTO_MIDCMD_TUNING
			if (isAltHoldChanged) {
				AltHold = EstAlt;
				isAltHoldChanged = 0;
			}
		}

		#else
		  if (abs(rcCommand[THROTTLE]-initialThrottleHold)>ALT_HOLD_THROTTLE_NEUTRAL_ZONE) {
			AltHoldCorr+= rcCommand[THROTTLE] - initialThrottleHold;
			if(abs(AltHoldCorr) > 500) {
            AltHold += AltHoldCorr/500;
            AltHoldCorr %= 500;
          }
          errorAltitudeI = 0;
          isAltHoldChanged = 1;
        } else if (isAltHoldChanged) {
          AltHold = EstAlt;
          isAltHoldChanged = 0;
        }
		#endif

		if (f.ATO == 1) {
			if (rcCommand[THROTTLE] >= MIDDLECOMMAND) {
				f.ATO = 0;
				AltHold = AUTO_TAKE_OFF_ALTITUDE;
				rcCommand[THROTTLE] = MIDDLECOMMAND;
			} else {
				rcCommand[THROTTLE] = MINCHECK - 1;
			}
		} else {
			rcCommand[THROTTLE] = initialThrottleHold + BaroPID;
		}
      #endif
    }
  #endif
	#if defined(THROTTLE_ANGLE_CORRECTION)
		if(f.ANGLE_MODE || f.HORIZON_MODE) {
		 rcCommand[THROTTLE]+= throttleAngleCorrection;
	}
	#endif

#if GPS
    if ( (f.GPS_HOME_MODE || f.GPS_HOLD_MODE) && f.GPS_FIX_HOME ) {
      float sin_yaw_y = sin(heading*0.0174532925f);
      float cos_yaw_x = cos(heading*0.0174532925f);
      #if defined(NAV_SLEW_RATE)
        nav_rated[LON]   += constrain(wrap_18000(nav[LON]-nav_rated[LON]),-NAV_SLEW_RATE,NAV_SLEW_RATE);
        nav_rated[LAT]   += constrain(wrap_18000(nav[LAT]-nav_rated[LAT]),-NAV_SLEW_RATE,NAV_SLEW_RATE);
        GPS_angle[ROLL]   = (nav_rated[LON]*cos_yaw_x - nav_rated[LAT]*sin_yaw_y) /10;
        GPS_angle[PITCH]  = (nav_rated[LON]*sin_yaw_y + nav_rated[LAT]*cos_yaw_x) /10;
      #else
        GPS_angle[ROLL]   = (nav[LON]*cos_yaw_x - nav[LAT]*sin_yaw_y) /10;
        GPS_angle[PITCH]  = (nav[LON]*sin_yaw_y + nav[LAT]*cos_yaw_x) /10;
      #endif

	  #if defined(SMOOTH_GPS_HOLD)
		if (f.GPS_HOLD_MODE == 1 && gpsHoldActivationTime > millis()) {
			GPS_angle[ROLL]   /= SMOOTH_GPS_RATIO;
			GPS_angle[PITCH]  /= SMOOTH_GPS_RATIO;
		}

		if (f.GPS_HOLD_MODE == 1 && gpsHoldActivationTime <= millis() && f.SMOOTH_HOLD == 1) {
			f.SMOOTH_HOLD = 0;
            GPS_reset_nav();
            nav_mode = NAV_MODE_POSHOLD; // Re-launch the nav processor to re-evaluate the hold
			GPS_hold[LAT] = GPS_coord[LAT];
			GPS_hold[LON] = GPS_coord[LON];
		}
	  #endif
    } else {
      if (f.GPS_POI_MODE && f.GPS_FIX_HOME ) {
    	  if (GPS_distanceToPOI >= 100) { // Minimal radius for the POI circle is 1m
    		  GPS_angle[ROLL] = 0;
    	  }
      } else {
		  GPS_angle[ROLL]  = 0;
		  GPS_angle[PITCH] = 0;
      }
    }
  #endif

  //**** PITCH & ROLL & YAW PID ****
  int16_t prop;
  prop = min(max(abs(rcCommand[PITCH]),abs(rcCommand[ROLL])),500); // range [0;500]

  for(axis=0;axis<3;axis++) {
    if ((f.ANGLE_MODE || f.HORIZON_MODE) && axis<2 ) { // MODE relying on ACC
      // 50 degrees max inclination
      errorAngle = constrain((rcCommand[axis]<<1) + GPS_angle[axis],-500,+500) - angle[axis] + conf.angleTrim[axis]; //16 bits is ok here
      PTermACC = ((int32_t)errorAngle*conf.P8[PIDLEVEL])>>7;                          // 32 bits is needed for calculation: errorAngle*P8[PIDLEVEL] could exceed 32768   16 bits is ok for result
      PTermACC = constrain(PTermACC,-conf.D8[PIDLEVEL]*5,+conf.D8[PIDLEVEL]*5);

      errorAngleI[axis]     = constrain(errorAngleI[axis]+errorAngle,-10000,+10000);    // WindUp     //16 bits is ok here
      ITermACC              = ((int32_t)errorAngleI[axis]*conf.I8[PIDLEVEL])>>12;            // 32 bits is needed for calculation:10000*I8 could exceed 32768   16 bits is ok for result
    }
    if ( !f.ANGLE_MODE || f.HORIZON_MODE || axis == 2 ) { // MODE relying on GYRO or YAW axis
      if (abs(rcCommand[axis])<500) error =          (rcCommand[axis]<<6)/conf.P8[axis] ; // 16 bits is needed for calculation: 500*64 = 32000      16 bits is ok for result if P8>5 (P>0.5)
                               else error = ((int32_t)rcCommand[axis]<<6)/conf.P8[axis] ; // 32 bits is needed for calculation

      error -= gyroData[axis];

      PTermGYRO = rcCommand[axis];

      errorGyroI[axis]  = constrain(errorGyroI[axis]+error,-16000,+16000);         // WindUp   16 bits is ok here
      if (abs(gyroData[axis])>640) errorGyroI[axis] = 0;
      ITermGYRO = ((errorGyroI[axis]>>7)*conf.I8[axis])>>6;                        // 16 bits is ok here 16000/125 = 128 ; 128*250 = 32000
    }
    if ( f.HORIZON_MODE && axis<2) {
      PTerm = ((int32_t)PTermACC*(512-prop) + (int32_t)PTermGYRO*prop)>>9;         // the real factor should be 500, but 512 is ok
      ITerm = ((int32_t)ITermACC*(512-prop) + (int32_t)ITermGYRO*prop)>>9;
    } else {
      if ( f.ANGLE_MODE && axis<2) {
        PTerm = PTermACC;
        ITerm = ITermACC;
      } else {
        PTerm = PTermGYRO;
        ITerm = ITermGYRO;
      }
    }

    PTerm -= ((int32_t)gyroData[axis]*dynP8[axis])>>6; // 32 bits is needed for calculation

    delta          = gyroData[axis] - lastGyro[axis];  // 16 bits is ok here, the dif between 2 consecutive gyro reads is limited to 800
    lastGyro[axis] = gyroData[axis];
    deltaSum       = delta1[axis]+delta2[axis]+delta;
    delta2[axis]   = delta1[axis];
    delta1[axis]   = delta;

    DTerm = ((int32_t)deltaSum*dynD8[axis])>>5;        // 32 bits is needed for calculation

    axisPID[axis] =  PTerm + ITerm - DTerm;
  }

  mixTable();
  writeServos();
  writeMotors();
}
