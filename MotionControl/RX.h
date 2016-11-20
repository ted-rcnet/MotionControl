/*
 * RX.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef RX_H_
#define RX_H_

#include <Arduino.h>

void configureReceiver();
void rxInt();
void readSBus();
void readSpektrum();
uint16_t readRawRC(uint8_t chan);
void computeRC();
void initOpenLRS(void);
void Config_OpenLRS();
void Read_OpenLRS_RC();
void spekBind();

#define FAILSAFE_DETECT_TRESHOLD  985

#endif /* RX_H_ */
