/*
 * output.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef OUTPUT_H_
#define OUTPUT_H_


#include <Arduino.h>

void writeServos();
void writeMotors();
void writeAllMotors(int16_t mc);
void initOutput();
void initializeServo();
void mixTable();




#endif /* OUTPUT_H_ */
