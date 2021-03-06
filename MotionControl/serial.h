/*
 * serial.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef SERIAL_H_
#define SERIAL_H_

#include <Arduino.h>
#include "config.h"
#include "def.h"

uint32_t read32();
uint16_t read16();
uint8_t read8();
void headSerialResponse(uint8_t err, uint8_t s);
void headSerialReply(uint8_t s);
void inline headSerialError(uint8_t s);
void tailSerialReply();
void serializeNames(PGM_P s);
void serialCom();
void evaluateCommand();
void evaluateOtherData(uint8_t sr);
void serialize32(uint32_t a) ;
void serialize16(int16_t a);
void serialize8(uint8_t a);
void UartSendData();
bool SerialTXfree(uint8_t port);
void SerialEnd(uint8_t port);
void SerialOpen(uint8_t port, uint32_t baud);
uint8_t SerialRead(uint8_t port);
uint8_t SerialAvailable(uint8_t port);
void SerialWrite(uint8_t port, uint8_t c);
void debugmsg_append_str(const char *str);
uint8_t debugmsg_available();
uint8_t SerialPeek(uint8_t port);

#if defined(SPEKTRUM)
extern volatile uint8_t spekFrameFlags;
extern volatile uint32_t spekTimeLast;
extern volatile unsigned long timer0_overflow_count;
#endif


#define UART_NUMBER 4
#if defined(GPS_SERIAL)
#define RX_BUFFER_SIZE 256 // 256 RX buffer is needed for GPS communication (64 or 128 was too short)
#else
#define RX_BUFFER_SIZE 64
#endif
#define TX_BUFFER_SIZE 128
#define INBUF_SIZE 64



#define BIND_CAPABLE 0;  //Used for Spektrum today; can be used in the future for any RX type that needs a bind and has a MultiWii module.
#if defined(SPEK_BIND)
#define BIND_CAPABLE 1;
#endif


#ifdef DEBUGMSG
#define DEBUG_MSG_BUFFER_SIZE 128
static char debug_buf[DEBUG_MSG_BUFFER_SIZE];
static uint8_t head_debug;
static uint8_t tail_debug;
#endif

// Multiwii Serial Protocol 0
#define MSP_VERSION              0

//to multiwii developpers/committers : do not add new MSP messages without a proper argumentation/agreement on the forum
#define MSP_IDENT                100   //out message         multitype + multiwii version + protocol version + capability variable
#define MSP_STATUS               101   //out message         cycletime & errors_count & sensor present & box activation & current setting number
#define MSP_RAW_IMU              102   //out message         9 DOF
#define MSP_SERVO                103   //out message         8 servos
#define MSP_MOTOR                104   //out message         8 motors
#define MSP_RC                   105   //out message         8 rc chan and more
#define MSP_RAW_GPS              106   //out message         fix, numsat, lat, lon, alt, speed, ground course
#define MSP_COMP_GPS             107   //out message         distance home, direction home
#define MSP_ATTITUDE             108   //out message         2 angles 1 heading
#define MSP_ALTITUDE             109   //out message         altitude, variometer
#define MSP_ANALOG               110   //out message         vbat, powermetersum, rssi if available on RX
#define MSP_RC_TUNING            111   //out message         rc rate, rc expo, rollpitch rate, yaw rate, dyn throttle PID
#define MSP_PID                  112   //out message         P I D coeff (9 are used currently)
#define MSP_BOX                  113   //out message         BOX setup (number is dependant of your setup)
#define MSP_MISC                 114   //out message         powermeter trig
#define MSP_MOTOR_PINS           115   //out message         which pins are in use for motors & servos, for GUI
#define MSP_BOXNAMES             116   //out message         the aux switch names
#define MSP_PIDNAMES             117   //out message         the PID names
#define MSP_WP                   118   //out message         get a WP, WP# is in the payload, returns (WP#, lat, lon, alt, flags) WP#0-home, WP#16-poshold
#define MSP_BOXIDS               119   //out message         get the permanent IDs associated to BOXes
#define MSP_SET_RAW_RC           200   //in message          8 rc chan
#define MSP_SET_RAW_GPS          201   //in message          fix, numsat, lat, lon, alt, speed
#define MSP_SET_PID              202   //in message          P I D coeff (9 are used currently)
#define MSP_SET_BOX              203   //in message          BOX setup (number is dependant of your setup)
#define MSP_SET_RC_TUNING        204   //in message          rc rate, rc expo, rollpitch rate, yaw rate, dyn throttle PID
#define MSP_ACC_CALIBRATION      205   //in message          no param
#define MSP_MAG_CALIBRATION      206   //in message          no param
#define MSP_SET_MISC             207   //in message          powermeter trig + 8 free for future use
#define MSP_RESET_CONF           208   //in message          no param
#define MSP_SET_WP               209   //in message          sets a given WP (WP#,lat, lon, alt, flags)
#define MSP_SELECT_SETTING       210   //in message          Select Setting Number (0-2)
#define MSP_SET_HEAD             211   //in message          define a new heading hold direction
#define MSP_BIND                 240   //in message          no param
#define MSP_EEPROM_WRITE         250   //in message          no param
#define MSP_DEBUGMSG             253   //out message         debug string buffer
#define MSP_DEBUG                254   //out message         debug1,debug2,debug3,debug4

#endif /* SERIAL_H_ */
