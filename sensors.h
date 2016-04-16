/*
 * sensors.h
 *
 *  Created on: 16 août 2013
 *      Author: rotule
 */

#ifndef SENSORS_H_
#define SENSORS_H_

#include <Arduino.h>


void GYRO_Common();
void ACC_Common();
void Baro_Common();
void i2c_MS561101BA_reset();
void i2c_MS561101BA_readCalibration();
void Baro_init();
void i2c_MS561101BA_UT_Start();
void i2c_MS561101BA_UP_Start();
void i2c_MS561101BA_UP_Read();
void i2c_MS561101BA_UT_Read();
void i2c_MS561101BA_Calculate();
uint8_t Baro_update();
uint8_t Mag_getADC();
void Mag_init();
void getADC();
void Device_Mag_getADC();
void Gyro_init();
void Gyro_getADC();
void ACC_init();
void ACC_getADC();
void initSensors();



#endif /* SENSORS_H_ */
