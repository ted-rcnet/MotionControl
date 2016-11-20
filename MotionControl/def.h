/*
 * def.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef DEF_H_
#define DEF_H_

/**************************************************************************************/
/***************             Proc specific definitions             ********************/
/**************************************************************************************/
#define MEGA

/**************************************************************************************/
/***************             motor and servo numbers               ********************/
/**************************************************************************************/
#if defined (AIRPLANE) || defined(FLYING_WING)|| defined(SINGLECOPTER)|| defined(DUALCOPTER)
#define FIXEDWING
#endif

#if defined(HELI_120_CCPM) || defined(HELI_90_DEG)
#define HELICOPTER
#endif

#if defined(BI) || defined(TRI) || defined(SERVO_TILT) || defined(GIMBAL) || defined(FLYING_WING) || defined(AIRPLANE) || defined(CAMTRIG) || defined(HELICOPTER) || defined(SERVO_MIX_TILT)|| defined(SINGLECOPTER)|| defined(DUALCOPTER)
#define SERVO
#endif

#if defined(GIMBAL)
#define NUMBER_MOTOR     0
#define PRI_SERVO_FROM   1 // use servo from 1 to 2
#define PRI_SERVO_TO     2
#elif defined(FLYING_WING)
#define NUMBER_MOTOR     1
#define PRI_SERVO_FROM   1 // use servo from 1 to 2
#define PRI_SERVO_TO     2

#elif defined(SINGLECOPTER)
#define NUMBER_MOTOR     1
#define PRI_SERVO_FROM   4 // use servo from 4 to 7
#define PRI_SERVO_TO     7
#elif defined(DUALCOPTER)
#define NUMBER_MOTOR     2
#define PRI_SERVO_FROM   4 // use servo from 5 to 6
#define PRI_SERVO_TO     6

#elif defined(AIRPLANE)
#if defined (USE_THROTTLESERVO)
#define NUMBER_MOTOR     0
#else
#define NUMBER_MOTOR     1
#endif
#if defined(FLAPS)
#define PRI_SERVO_FROM   3 // use servo from 3 to 8
#undef CAMTRIG             // Disable Camtrig on A2
#else
#define PRI_SERVO_FROM   4 // use servo from 4 to 8
#endif
#define PRI_SERVO_TO     8
#elif defined(BI)
#define NUMBER_MOTOR     2
#define PRI_SERVO_FROM   5 // use servo from 5 to 6
#define PRI_SERVO_TO     6
#elif defined(TRI)
#define NUMBER_MOTOR     3
#define PRI_SERVO_FROM   6 // use only servo 6
#define PRI_SERVO_TO     6
#elif defined(QUADP) || defined(QUADX) || defined(Y4)|| defined(VTAIL4)
#define NUMBER_MOTOR     4
#elif defined(Y6) || defined(HEX6) || defined(HEX6X) || defined(HEX6H)
#define NUMBER_MOTOR     6
#elif defined(OCTOX8) || defined(OCTOFLATP) || defined(OCTOFLATX)
#define NUMBER_MOTOR     8
#elif defined(HELICOPTER)
#ifdef HELI_USE_SERVO_FOR_THROTTLE
#define NUMBER_MOTOR     0 // use servo to drive throttle output
#define PRI_SERVO_FROM   4 // use servo from 4 to 8
#define PRI_SERVO_TO     8
#else
#define NUMBER_MOTOR     1 // use 1 motor for throttle
#define PRI_SERVO_FROM   4 // use servo from 4 to 7
#define PRI_SERVO_TO     7
#endif
#endif

#if (defined(SERVO_TILT)|| defined(SERVO_MIX_TILT))&& defined(CAMTRIG)
#define SEC_SERVO_FROM   1 // use servo from 1 to 3
#define SEC_SERVO_TO     3
#else
#if defined(SERVO_TILT)|| defined(SERVO_MIX_TILT)
// if A0 and A1 is taken by motors, we can use A2 and 12 for Servo tilt
#if defined(A0_A1_PIN_HEX) && (NUMBER_MOTOR == 6) && defined(PROMINI)
#define SEC_SERVO_FROM   3 // use servo from 3 to 4
#define SEC_SERVO_TO     4
#else
#if !defined(MEGA_HW_PWM_SERVOS) // if HW Gimbal is active we dont need the SW PWM defines
#define SEC_SERVO_FROM   1 // use servo from 1 to 2
#define SEC_SERVO_TO     2
#endif
#endif
#endif
#if defined(CAMTRIG)
#define SEC_SERVO_FROM   3 // use servo 3
#define SEC_SERVO_TO     3
#endif
#endif

#if defined(SIRIUS_AIR) || defined(SIRIUS_AIR_GPS)
#define RCAUX2PIND17
#endif

/**************************  all the Mega types  ***********************************/
#define LEDPIN_PINMODE             pinMode (13, OUTPUT);pinMode (30, OUTPUT);
#define LEDPIN_TOGGLE              PINB  |= (1<<7); PINC  |= (1<<7);
#define LEDPIN_ON                  PORTB |= (1<<7); PORTC |= (1<<7);
#define LEDPIN_OFF                 PORTB &= ~(1<<7);PORTC &= ~(1<<7);
#define BUZZERPIN_PINMODE          pinMode (31, OUTPUT);
#if defined PILOTLAMP
#define    PL_PIN_ON    PORTC |= 1<<6;
#define    PL_PIN_OFF   PORTC &= ~(1<<6);
#else
#define BUZZERPIN_ON               PORTC |= 1<<6;
#define BUZZERPIN_OFF              PORTC &= ~(1<<6);
#endif

#if !defined(DISABLE_POWER_PIN)
#define POWERPIN_PINMODE           pinMode (37, OUTPUT);
#define POWERPIN_ON                PORTC |= 1<<0;
#define POWERPIN_OFF               PORTC &= ~(1<<0);
#else
#define POWERPIN_PINMODE           ;
#define POWERPIN_ON                ;
#define POWERPIN_OFF               ;
#endif
#define I2C_PULLUPS_ENABLE         PORTD |= 1<<0; PORTD |= 1<<1;       // PIN 20&21 (SDA&SCL)
#define I2C_PULLUPS_DISABLE        PORTD &= ~(1<<0); PORTD &= ~(1<<1);
#define PINMODE_LCD                pinMode(0, OUTPUT);
#define LCDPIN_OFF                 PORTE &= ~1; //switch OFF digital PIN 0
#define LCDPIN_ON                  PORTE |= 1;
#define STABLEPIN_PINMODE          pinMode (32, OUTPUT);
#define STABLEPIN_ON               PORTC |= 1<<5;
#define STABLEPIN_OFF              PORTC &= ~(1<<5);
#if defined(PPM_ON_THROTTLE)
//configure THROTTLE PIN (A8 pin) as input witch pullup and enabled PCINT interrupt
#define PPM_PIN_INTERRUPT        DDRK &= ~(1<<0); PORTK |= (1<<0); PCMSK2 |= (1<<0); PCICR |= (1<<2);
#else
#define PPM_PIN_INTERRUPT        attachInterrupt(4, rxInt, RISING);  //PIN 19, also used for Spektrum satellite option
#endif
#if !defined(SPEK_SERIAL_PORT)
#define SPEK_SERIAL_PORT         1
#endif
//RX PIN assignment inside the port //for PORTK
#define THROTTLEPIN                0  //PIN 62 =  PIN A8
#define ROLLPIN                    1  //PIN 63 =  PIN A9
#define PITCHPIN                   2  //PIN 64 =  PIN A10
#define YAWPIN                     3  //PIN 65 =  PIN A11
#define AUX1PIN                    4  //PIN 66 =  PIN A12
#define AUX2PIN                    5  //PIN 67 =  PIN A13
#define AUX3PIN                    6  //PIN 68 =  PIN A14
#define AUX4PIN                    7  //PIN 69 =  PIN A15
#define V_BATPIN                   A0    // Analog PIN 0
#define PSENSORPIN                 A2    // Analog PIN 2
#define PCINT_PIN_COUNT            8
#define PCINT_RX_BITS              (1<<2),(1<<4),(1<<5),(1<<6),(1<<7),(1<<0),(1<<1),(1<<3)
#define PCINT_RX_PORT              PORTK
#define PCINT_RX_MASK              PCMSK2
#define PCIR_PORT_BIT              (1<<2)
#define RX_PC_INTERRUPT            PCINT2_vect
#define RX_PCINT_PIN_PORT          PINK

//#define SERVO_1_PINMODE            pinMode(34,OUTPUT);pinMode(44,OUTPUT); // TILT_PITCH - WING left
//#define SERVO_1_PIN_HIGH           PORTC |= 1<<3;PORTL |= 1<<5;
//#define SERVO_1_PIN_LOW            PORTC &= ~(1<<3);PORTL &= ~(1<<5);
//#define SERVO_2_PINMODE            pinMode(35,OUTPUT);pinMode(45,OUTPUT); // TILT_ROLL  - WING right
//#define SERVO_2_PIN_HIGH           PORTC |= 1<<2;PORTL |= 1<<4;
//#define SERVO_2_PIN_LOW            PORTC &= ~(1<<2);PORTL &= ~(1<<4);
//#define SERVO_3_PINMODE            pinMode(33,OUTPUT); pinMode(46,OUTPUT); // CAM TRIG  - alt TILT_PITCH
//#define SERVO_3_PIN_HIGH           PORTC |= 1<<4;PORTL |= 1<<3;
//#define SERVO_3_PIN_LOW            PORTC &= ~(1<<4);PORTL &= ~(1<<3);
#define SERVO_1_PINMODE            pinMode(40,OUTPUT);        // TILT_PITCH
#define SERVO_1_PIN_HIGH           PORTL |= 1<<5;
#define SERVO_1_PIN_LOW            PORTL &= ~(1<<5);
#define SERVO_2_PINMODE            pinMode(39,OUTPUT);        // TILT_ROLL
#define SERVO_2_PIN_HIGH           PORTL |= 1<<4;
#define SERVO_2_PIN_LOW            PORTL &= ~(1<<4);
#define SERVO_3_PINMODE            pinMode(38,OUTPUT);        // CAM TRIG
#define SERVO_3_PIN_HIGH           PORTL |= 1<<3;
#define SERVO_3_PIN_LOW            PORTL &= ~(1<<3);
#define SERVO_4_PINMODE            pinMode (37, OUTPUT);                   // new       - alt TILT_ROLL
#define SERVO_4_PIN_HIGH           PORTC |= 1<<0;
#define SERVO_4_PIN_LOW            PORTC &= ~(1<<0);
#define SERVO_5_PINMODE            pinMode(6,OUTPUT);                      // BI LEFT
#define SERVO_5_PIN_HIGH           PORTH |= 1<<3;
#define SERVO_5_PIN_LOW            PORTH &= ~(1<<3);
#define SERVO_6_PINMODE            pinMode(2,OUTPUT);                      // TRI REAR - BI RIGHT
#define SERVO_6_PIN_HIGH           PORTE |= 1<<4;
#define SERVO_6_PIN_LOW            PORTE &= ~(1<<4);
#define SERVO_7_PINMODE            pinMode(5,OUTPUT);                      // new
#define SERVO_7_PIN_HIGH           PORTE |= 1<<3;
#define SERVO_7_PIN_LOW            PORTE &= ~(1<<3);
#define SERVO_8_PINMODE            pinMode(3,OUTPUT);                      // new
#define SERVO_8_PIN_HIGH           PORTE |= 1<<5;
#define SERVO_8_PIN_LOW            PORTE &= ~(1<<5);

/**********************   Sort the Servos for the most ideal SW PWM     ************************/
// this define block sorts the above slected servos to be in a simple order from 1 - (count of total servos)
// its pretty fat but its the best way i found to get less compiled code and max speed in the ISR without loosing its flexibility
#if (PRI_SERVO_FROM == 1) || (SEC_SERVO_FROM == 1)
#define LAST_LOW SERVO_1_PIN_LOW
#define SERVO_1_HIGH SERVO_1_PIN_HIGH
#define SERVO_1_LOW SERVO_1_PIN_LOW
#define SERVO_1_ARR_POS  0
#endif
#if (PRI_SERVO_FROM <= 2 && PRI_SERVO_TO >= 2) || (SEC_SERVO_FROM <= 2 && SEC_SERVO_TO >= 2)
#undef LAST_LOW
#define LAST_LOW SERVO_2_PIN_LOW
#if !defined(SERVO_1_HIGH)
#define SERVO_1_HIGH SERVO_2_PIN_HIGH
#define SERVO_1_LOW SERVO_2_PIN_LOW
#define SERVO_1_ARR_POS 1
#else
#define SERVO_2_HIGH SERVO_2_PIN_HIGH
#define SERVO_2_LOW SERVO_2_PIN_LOW
#define SERVO_2_ARR_POS 1
#endif
#endif
#if (PRI_SERVO_FROM <= 3 && PRI_SERVO_TO >= 3) || (SEC_SERVO_FROM <= 3 && SEC_SERVO_TO >= 3)
#undef LAST_LOW
#define LAST_LOW SERVO_3_PIN_LOW
#if !defined(SERVO_1_HIGH)
#define SERVO_1_HIGH SERVO_3_PIN_HIGH
#define SERVO_1_LOW SERVO_3_PIN_LOW
#define SERVO_1_ARR_POS 2
#elif !defined(SERVO_2_HIGH)
#define SERVO_2_HIGH SERVO_3_PIN_HIGH
#define SERVO_2_LOW SERVO_3_PIN_LOW
#define SERVO_2_ARR_POS 2
#else
#define SERVO_3_HIGH SERVO_3_PIN_HIGH
#define SERVO_3_LOW SERVO_3_PIN_LOW
#define SERVO_3_ARR_POS 2
#endif
#endif
#if (PRI_SERVO_FROM <= 4 && PRI_SERVO_TO >= 4) || (SEC_SERVO_FROM <= 4 && SEC_SERVO_TO >= 4)
#undef LAST_LOW
#define LAST_LOW SERVO_4_PIN_LOW
#if !defined(SERVO_1_HIGH)
#define SERVO_1_HIGH SERVO_4_PIN_HIGH
#define SERVO_1_LOW SERVO_4_PIN_LOW
#define SERVO_1_ARR_POS 3
#elif !defined(SERVO_2_HIGH)
#define SERVO_2_HIGH SERVO_4_PIN_HIGH
#define SERVO_2_LOW SERVO_4_PIN_LOW
#define SERVO_2_ARR_POS 3
#elif !defined(SERVO_3_HIGH)
#define SERVO_3_HIGH SERVO_4_PIN_HIGH
#define SERVO_3_LOW SERVO_4_PIN_LOW
#define SERVO_3_ARR_POS 3
#else
#define SERVO_4_HIGH SERVO_4_PIN_HIGH
#define SERVO_4_LOW SERVO_4_PIN_LOW
#define SERVO_4_ARR_POS 3
#endif
#endif
#if (PRI_SERVO_FROM <= 5 && PRI_SERVO_TO >= 5) || (SEC_SERVO_FROM <= 5 && SEC_SERVO_TO >= 5)
#undef LAST_LOW
#define LAST_LOW SERVO_5_PIN_LOW
#if !defined(SERVO_1_HIGH)
#define SERVO_1_HIGH SERVO_5_PIN_HIGH
#define SERVO_1_LOW SERVO_5_PIN_LOW
#define SERVO_1_ARR_POS 4
#elif !defined(SERVO_2_HIGH)
#define SERVO_2_HIGH SERVO_5_PIN_HIGH
#define SERVO_2_LOW SERVO_5_PIN_LOW
#define SERVO_2_ARR_POS 4
#elif !defined(SERVO_3_HIGH)
#define SERVO_3_HIGH SERVO_5_PIN_HIGH
#define SERVO_3_LOW SERVO_5_PIN_LOW
#define SERVO_3_ARR_POS 4
#elif !defined(SERVO_4_HIGH)
#define SERVO_4_HIGH SERVO_5_PIN_HIGH
#define SERVO_4_LOW SERVO_5_PIN_LOW
#define SERVO_4_ARR_POS 4
#else
#define SERVO_5_HIGH SERVO_5_PIN_HIGH
#define SERVO_5_LOW SERVO_5_PIN_LOW
#define SERVO_5_ARR_POS 4
#endif
#endif
#if (PRI_SERVO_FROM <= 6 && PRI_SERVO_TO >= 6) || (SEC_SERVO_FROM <= 6 && SEC_SERVO_TO >= 6)
#undef LAST_LOW
#define LAST_LOW SERVO_6_PIN_LOW
#if !defined(SERVO_1_HIGH)
#define SERVO_1_HIGH SERVO_6_PIN_HIGH
#define SERVO_1_LOW SERVO_6_PIN_LOW
#define SERVO_1_ARR_POS 5
#elif !defined(SERVO_2_HIGH)
#define SERVO_2_HIGH SERVO_6_PIN_HIGH
#define SERVO_2_LOW SERVO_6_PIN_LOW
#define SERVO_2_ARR_POS 5
#elif !defined(SERVO_3_HIGH)
#define SERVO_3_HIGH SERVO_6_PIN_HIGH
#define SERVO_3_LOW SERVO_6_PIN_LOW
#define SERVO_3_ARR_POS 5
#elif !defined(SERVO_4_HIGH)
#define SERVO_4_HIGH SERVO_6_PIN_HIGH
#define SERVO_4_LOW SERVO_6_PIN_LOW
#define SERVO_4_ARR_POS 5
#elif !defined(SERVO_5_HIGH)
#define SERVO_5_HIGH SERVO_6_PIN_HIGH
#define SERVO_5_LOW SERVO_6_PIN_LOW
#define SERVO_5_ARR_POS 5
#else
#define SERVO_6_HIGH SERVO_6_PIN_HIGH
#define SERVO_6_LOW SERVO_6_PIN_LOW
#define SERVO_6_ARR_POS 5
#endif
#endif
#if (PRI_SERVO_FROM <= 7 && PRI_SERVO_TO >= 7) || (SEC_SERVO_FROM <= 7 && SEC_SERVO_TO >= 7)
#undef LAST_LOW
#define LAST_LOW SERVO_7_PIN_LOW
#if !defined(SERVO_1_HIGH)
#define SERVO_1_HIGH SERVO_7_PIN_HIGH
#define SERVO_1_LOW SERVO_7_PIN_LOW
#define SERVO_1_ARR_POS 6
#elif !defined(SERVO_2_HIGH)
#define SERVO_2_HIGH SERVO_7_PIN_HIGH
#define SERVO_2_LOW SERVO_7_PIN_LOW
#define SERVO_2_ARR_POS 6
#elif !defined(SERVO_3_HIGH)
#define SERVO_3_HIGH SERVO_7_PIN_HIGH
#define SERVO_3_LOW SERVO_7_PIN_LOW
#define SERVO_3_ARR_POS 6
#elif !defined(SERVO_4_HIGH)
#define SERVO_4_HIGH SERVO_7_PIN_HIGH
#define SERVO_4_LOW SERVO_7_PIN_LOW
#define SERVO_4_ARR_POS 6
#elif !defined(SERVO_5_HIGH)
#define SERVO_5_HIGH SERVO_7_PIN_HIGH
#define SERVO_5_LOW SERVO_7_PIN_LOW
#define SERVO_5_ARR_POS 6
#elif !defined(SERVO_6_HIGH)
#define SERVO_6_HIGH SERVO_7_PIN_HIGH
#define SERVO_6_LOW SERVO_7_PIN_LOW
#define SERVO_6_ARR_POS 6
#else
#define SERVO_7_HIGH SERVO_7_PIN_HIGH
#define SERVO_7_LOW SERVO_7_PIN_LOW
#define SERVO_7_ARR_POS 6
#endif
#endif
#if (PRI_SERVO_FROM <= 8 && PRI_SERVO_TO >= 8) || (SEC_SERVO_FROM <= 8 && SEC_SERVO_TO >= 8)
#undef LAST_LOW
#define LAST_LOW SERVO_8_PIN_LOW
#if !defined(SERVO_1_HIGH)
#define SERVO_1_HIGH SERVO_8_PIN_HIGH
#define SERVO_1_LOW SERVO_8_PIN_LOW
#define SERVO_1_ARR_POS 7
#elif !defined(SERVO_2_HIGH)
#define SERVO_2_HIGH SERVO_8_PIN_HIGH
#define SERVO_2_LOW SERVO_8_PIN_LOW
#define SERVO_2_ARR_POS 7
#elif !defined(SERVO_3_HIGH)
#define SERVO_3_HIGH SERVO_8_PIN_HIGH
#define SERVO_3_LOW SERVO_8_PIN_LOW
#define SERVO_3_ARR_POS 7
#elif !defined(SERVO_4_HIGH)
#define SERVO_4_HIGH SERVO_8_PIN_HIGH
#define SERVO_4_LOW SERVO_8_PIN_LOW
#define SERVO_4_ARR_POS 7
#elif !defined(SERVO_5_HIGH)
#define SERVO_5_HIGH SERVO_8_PIN_HIGH
#define SERVO_5_LOW SERVO_8_PIN_LOW
#define SERVO_5_ARR_POS 7
#elif !defined(SERVO_6_HIGH)
#define SERVO_6_HIGH SERVO_8_PIN_HIGH
#define SERVO_6_LOW SERVO_8_PIN_LOW
#define SERVO_6_ARR_POS 7
#elif !defined(SERVO_7_HIGH)
#define SERVO_7_HIGH SERVO_8_PIN_HIGH
#define SERVO_7_LOW SERVO_8_PIN_LOW
#define SERVO_7_ARR_POS 7
#else
#define SERVO_8_HIGH SERVO_8_PIN_HIGH
#define SERVO_8_LOW SERVO_8_PIN_LOW
#define SERVO_8_ARR_POS 7
#endif
#endif

#if defined(MEGA_HW_PWM_SERVOS)
#undef SERVO_1_HIGH                                    // No software PWM's if we use hardware MEGA PWM
#endif

/**************************************************************************************/
/***************      IMU Orientations and Sensor definitions      ********************/
/**************************************************************************************/

//please submit any correction to this list.
#if defined(FC_GPS)
#define HMC5883
#define MAG_ORIENTATION(X, Y, Z)  {magADC[ROLL]  =  -X; magADC[PITCH]  =  Y; magADC[YAW]  = Z;}
#define MPU6050_I2C_AUX_MASTER // MAG connected to the AUX I2C bus of MPU6050
#undef INTERNAL_I2C_PULLUPS
#define GPS_SERIAL 2
#define GPS_BAUD   115200
#define UBLOX
#endif

#define MPU6050
#define MS561101BA
#define ACC_ORIENTATION(X, Y, Z)  {accADC[ROLL]  = -X; accADC[PITCH]  = -Y; accADC[YAW]  =  Z;}
#define GYRO_ORIENTATION(X, Y, Z) {gyroADC[ROLL] =  Y; gyroADC[PITCH] = -X; gyroADC[YAW] = -Z;}
#undef INTERNAL_I2C_PULLUPS
//servo pins on RCNet FC board are on pins 38,39,40
//#define SERVO_1_PINMODE            pinMode(40,OUTPUT);        // TILT_PITCH
//#define SERVO_1_PIN_HIGH           PORTL |= 1<<5;
//#define SERVO_1_PIN_LOW            PORTL &= ~(1<<5);
//#define SERVO_2_PINMODE            pinMode(39,OUTPUT);        // TILT_ROLL
//#define SERVO_2_PIN_HIGH           PORTL |= 1<<4;
//#define SERVO_2_PIN_LOW            PORTL &= ~(1<<4);
//#define SERVO_3_PINMODE            pinMode(38,OUTPUT);        // CAM TRIG
//#define SERVO_3_PIN_HIGH           PORTL |= 1<<3;
//#define SERVO_3_PIN_LOW            PORTL &= ~(1<<3);

/**************************************************************************************/
/***************              Sensor Type definitions              ********************/
/**************************************************************************************/

#if defined(MPU6050)
#define ACC 1
#else
#define ACC 0
#endif

#if defined(HMC5883)
#define MAG 1
#else
#define MAG 0
#endif

#if defined(MPU6050)
#define GYRO 1
#else
#define GYRO 0
#endif

#if defined(MS561101BA)
#define BARO 1
#else
#define BARO 0
#endif

#if defined(GPS_SERIAL)
#define GPS 1
#else
#define GPS 0
#endif

#if defined(SRF02) || defined(SRF08) || defined(SRF10) || defined(SRC235) || defined(TINY_GPS_SONAR)
#define SONAR 1
#else
#define SONAR 0
#endif

/**************************************************************************************/
/***************      Multitype decleration for the GUI's          ********************/
/**************************************************************************************/
#if defined(TRI)
#define MULTITYPE 1
#elif defined(QUADP)
#define MULTITYPE 2
#elif defined(QUADX)
#define MULTITYPE 3
#elif defined(BI)
#define MULTITYPE 4
#elif defined(GIMBAL)
#define MULTITYPE 5
#elif defined(Y6)
#define MULTITYPE 6
#elif defined(HEX6)
#define MULTITYPE 7
#elif defined(FLYING_WING)
#define MULTITYPE 8
#elif defined(Y4)
#define MULTITYPE 9
#elif defined(HEX6X)
#define MULTITYPE 10
#elif defined(OCTOX8)
#define MULTITYPE 11   //the JAVA GUI is the same for all 8 motor configs
#elif defined(OCTOFLATP)
#define MULTITYPE 12   //12  for MultiWinGui
#elif defined(OCTOFLATX)
#define MULTITYPE 13   //13  for MultiWinGui
#elif defined(AIRPLANE)|| defined(SINGLECOPTER)|| defined(DUALCOPTER)
#define MULTITYPE 14
#elif defined (HELI_120_CCPM)
#define MULTITYPE 15
#elif defined (HELI_90_DEG)
#define MULTITYPE 16
#elif defined(VTAIL4)
#define MULTITYPE 17
#elif defined(HEX6H)
#define MULTITYPE 18
#endif

/**************************************************************************************/
/***************          Some unsorted "chain" defines            ********************/
/**************************************************************************************/

#if defined(POWERMETER_HARD) || defined(POWERMETER_SOFT)
#define POWERMETER
#endif

#if defined PILOTLAMP
#define    PL_CHANNEL OCR0B  //use B since A can be used by camstab
#define    PL_ISR TIMER0_COMPB_vect
#define    PL_INIT   TCCR0A=0;TIMSK0|=(1<<OCIE0B);PL_CHANNEL=PL_IDLE;PilotLamp(PL_GRN_OFF);PilotLamp(PL_BLU_OFF);PilotLamp(PL_RED_OFF);PilotLamp(PL_BZR_OFF);
#define    BUZZERPIN_ON PilotLamp(PL_BZR_ON);
#define    BUZZERPIN_OFF PilotLamp(PL_BZR_OFF);
#define    PL_GRN_ON    25    // 100us
#define    PL_GRN_OFF   50    // 200us
#define    PL_BLU_ON    75    // 300us
#define    PL_BLU_OFF   100    // 400us
#define    PL_RED_ON    125    // 500us
#define    PL_RED_OFF   150    // 600us
#define    PL_BZR_ON    175    // 700us
#define    PL_BZR_OFF   200    // 800us
#define    PL_IDLE      125    // 100us
#endif

#if defined(PILOTLAMP) || defined(VBAT)
#define BUZZER
#endif

#if defined(MWI_SDCARD)
#define LOGFILE_GPS 0
#define LOGFILE_PERM 1
#endif

//all new Special RX's must be added here
//this is to avoid confusion :)
#if !defined(SERIAL_SUM_PPM) && !defined(SPEKTRUM) && !defined(SBUS) && !defined(RCSERIAL)
#define STANDARD_RX
#endif

// Spektrum Satellite
#if defined(SPEKTRUM)
#define SPEK_FRAME_SIZE 16
#if (SPEKTRUM == 1024)
#define SPEK_CHAN_SHIFT  2       // Assumes 10 bit frames, that is 1024 mode.
#define SPEK_CHAN_MASK   0x03    // Assumes 10 bit frames, that is 1024 mode.
#define SPEK_DATA_SHIFT          // Assumes 10 bit frames, that is 1024 mode.
#define SPEK_BIND_PULSES 3
#endif
#if (SPEKTRUM == 2048)
#define SPEK_CHAN_SHIFT  3       // Assumes 11 bit frames, that is 2048 mode.
#define SPEK_CHAN_MASK   0x07    // Assumes 11 bit frames, that is 2048 mode.
#define SPEK_DATA_SHIFT >> 1     // Assumes 11 bit frames, that is 2048 mode.
#define SPEK_BIND_PULSES 5
#endif
#if defined(SPEK_BIND)
#if !defined(SPEK_BIND_GROUND)
#define SPEK_BIND_GROUND 4
#endif
#if !defined(SPEK_BIND_POWER)
#define SPEK_BIND_POWER  5
#endif
#if !defined(SPEK_BIND_DATA)
#define SPEK_BIND_DATA   6
#endif
#endif
#endif

#if defined(SBUS)
#define RC_CHANS 18
#elif defined(SPEKTRUM) || defined(SERIAL_SUM_PPM)
#define RC_CHANS 12
#else
#define RC_CHANS 8
#endif

#if !(defined(DISPLAY_2LINES)) && !(defined(DISPLAY_MULTILINE))
#if (defined(LCD_VT100)) || (defined(OLED_I2C_128x64))
#define DISPLAY_MULTILINE
#else
#define DISPLAY_2LINES
#endif
#endif

#if (defined(LCD_VT100))
#if !(defined(MULTILINE_PRE))
#define MULTILINE_PRE 6
#endif
#if !(defined(MULTILINE_POST))
#define MULTILINE_POST 9
#endif
#elif (defined(OLED_I2C_128x64) && defined(DISPLAY_FONT_DSIZE))
#if !(defined(MULTILINE_PRE))
#define MULTILINE_PRE 1
#endif
#if !(defined(MULTILINE_POST))
#define MULTILINE_POST 3
#endif
#elif (defined(OLED_I2C_128x64))
#if !(defined(MULTILINE_PRE))
#define MULTILINE_PRE 3
#endif
#if !(defined(MULTILINE_POST))
#define MULTILINE_POST 5
#endif
#endif

#if !defined(ALT_HOLD_THROTTLE_NEUTRAL_ZONE)
#define ALT_HOLD_THROTTLE_NEUTRAL_ZONE 40
#endif

/**************************************************************************************/
/***************         override default pin assignments ?        ********************/
/**************************************************************************************/
#ifdef OVERRIDE_V_BATPIN
#define V_BATPIN OVERRIDE_V_BATPIN
#endif
#ifdef OVERRIDE_LEDPIN_PINMODE
#define LEDPIN_PINMODE OVERRIDE_LEDPIN_PINMODE
#define LEDPIN_TOGGLE  OVERRIDE_LEDPIN_TOGGLE
#define LEDPIN_OFF     OVERRIDE_LEDPIN_OFF
#define LEDPIN_ON      OVERRIDE_LEDPIN_ON
#endif
#ifdef OVERRIDE_BUZZERPIN_PINMODE
#define BUZZERPIN_PINMODE OVERRIDE_BUZZERPIN_PINMODE
#define BUZZERPIN_ON      OVERRIDE_BUZZERPIN_ON
#define BUZZERPIN_OFF     OVERRIDE_BUZZERPIN_OFF
#endif

/**************************************************************************************/
/********* enforce your sensors orientation - possibly overriding board defaults  *****/
/**************************************************************************************/
#ifdef FORCE_GYRO_ORIENTATION
#define GYRO_ORIENTATION FORCE_GYRO_ORIENTATION
#endif
#ifdef FORCE_ACC_ORIENTATION
#define ACC_ORIENTATION FORCE_ACC_ORIENTATION
#endif
#ifdef FORCE_MAG_ORIENTATION
#define MAG_ORIENTATION FORCE_MAG_ORIENTATION
#endif

/**************************************************************************************/
/***************               Error Checking Section              ********************/
/**************************************************************************************/

#ifndef NUMBER_MOTOR
#error "NUMBER_MOTOR is not set, most likely you have not defined any type of multicopter"
#endif

#if (defined(LCD_DUMMY) || defined(LCD_SERIAL3W) || defined(LCD_TEXTSTAR) || defined(LCD_VT100) || defined(LCD_TTY) || defined(LCD_ETPP) || defined(LCD_LCD03) || defined(OLED_I2C_128x64) )
#define HAS_LCD
#endif

#if (defined(LCD_CONF) || defined(LCD_TELEMETRY)) && !(defined(HAS_LCD) )
#error "LCD_CONF or LCD_TELEMETRY defined, and choice of LCD not defined.  Uncomment one of LCD_SERIAL3W, LCD_TEXTSTAR, LCD_VT100, LCD_TTY or LCD_ETPP, LCD_LCD03, OLED_I2C_128x64"
#endif

#if defined(POWERMETER) && !(defined(VBAT))
#error "to use powermeter, you must also define and configure VBAT"
#endif

#if defined(LCD_TELEMETRY_AUTO) && !(defined(LCD_TELEMETRY))
#error "to use automatic telemetry, you MUST also define and configure LCD_TELEMETRY"
#endif

#if defined(LCD_TELEMETRY_STEP) && !(defined(LCD_TELEMETRY))
#error "to use single step telemetry, you MUST also define and configure LCD_TELEMETRY"
#endif

#endif /* DEF_H_ */
