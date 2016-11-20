/*
 * EEPROM.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef EEPROM_H_
#define EEPROM_H_

#include <Arduino.h>

uint8_t calculate_sum(uint8_t *cb , uint8_t siz);
void readGlobalSet();
void readEEPROM() ;
void writeGlobalSet(uint8_t b);
void writeParams(uint8_t b);
void LoadDefaults() ;
void readPLog();
void writePLog();


#endif /* EEPROM_H_ */
