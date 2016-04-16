/*
 * alarms.h
 *
 *  Created on: 16 août 2013
 *      Author: rotule
 */

#ifndef ALARMS_H_
#define ALARMS_H_

#include <Arduino.h>

void alarmPatternComposer();
void patternDecode(uint8_t resource, uint16_t first, uint16_t second,
		uint16_t third, uint16_t cyclepause, uint16_t endpause);
void turnOff(uint8_t resource);
void PilotLampSequence(uint16_t speed, uint16_t pattern, uint8_t num_patterns);
void PilotLamp(uint8_t count);
void blinkLED(uint8_t num, uint8_t ontime, uint8_t repeat);
void setTiming(uint8_t resource, uint16_t pulse, uint16_t pause);
void toggleResource(uint8_t resource, uint8_t activate);
void i2CLedRingState();
void blinkLedRing();
void alarmHandler();
void vario_output(uint16_t d, uint8_t up) ;
void vario_signaling() ;
void auto_switch_led_flasher();
void led_flasher_autoselect_sequence();
void init_led_flasher();
void led_flasher_set_sequence(uint8_t s);
void inline switch_led_flasher(uint8_t on) ;
void init_landing_lights(void);
void inline switch_landing_lights(uint8_t on);
void auto_switch_landing_lights(void);

#endif /* ALARMS_H_ */
