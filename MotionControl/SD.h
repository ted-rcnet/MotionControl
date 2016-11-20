/*
 * SD.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef SD_H_
#define SD_H_

#include <Arduino.h>

void init_SD();
void writeGPSLog(int32_t latitude, int32_t longitude, int32_t altitude);
void writePLogToSD();
void readPLogFromSD();
void fillPlogStruct(char* key, char* value);
uint8_t getNextWp();


#endif /* SD_H_ */
