/*
 * LCD.h
 *
 *  Created on: 21 août 2013
 *      Author: EUGI7210
 */

#ifndef LCD_H_
#define LCD_H_

#define OLED_address   0x3C     // OLED at address 0x3C in 7bit

void i2c_OLED_send_cmd(uint8_t command);
void i2c_OLED_send_byte(uint8_t val);
void  i2c_OLED_init(void);
void i2c_OLED_send_char(unsigned char ascii);
void i2c_OLED_send_string(const char *string);
void i2c_OLED_send_logo(void);
void i2c_OLED_Put_Logo(void);
void i2c_OLED_set_XY(byte col, byte row);
void i2c_OLED_set_line(byte row);
void i2c_clear_OLED(void);
void i2c_ETPP_init ();
void i2c_ETPP_send_cmd (byte c);
void i2c_ETPP_send_char (char c);
void i2c_ETPP_set_cursor (byte addr);
void i2c_ETPP_set_cursor (byte col, byte row);
void i2c_ETPP_create_char (byte idx, uint8_t* array);
void ETPP_barGraph(byte num, int val);
void i2c_LCD03_init ();
void i2c_LCD03_send_cmd (byte c);
void i2c_LCD03_send_char (char c);
void i2c_LCD03_set_cursor (byte col, byte row);
void LCDprint(uint8_t i);
void LCDprintChar(const char *s);
void LCDcrlf();
void LCDclear();
void LCDsetLine(byte line);
void lcdprint_int16(int16_t v);
void initLCD();
void __u8Inc(void * var, int16_t inc);
void __u16Inc(void * var, int16_t inc);
void __s16Inc(void * var, int16_t inc);
void __nullInc(void * var, int16_t inc);
void __u8Fmt(void * var, uint8_t mul, uint8_t dec);
void __s8Fmt(void * var, uint8_t mul, uint8_t dec);
void __u16Fmt(void * var, uint8_t mul, uint8_t dec);
void __s16Fmt(void * var, uint8_t mul, uint8_t dec);
void __uAuxFmt1(void * var, uint8_t mul, uint8_t dec);
void __uAuxFmt2(void * var, uint8_t mul, uint8_t dec);
void __uAuxFmt3(void * var, uint8_t mul, uint8_t dec);
void __uAuxFmt4(void * var, uint8_t mul, uint8_t dec);
void __uAuxFmt(void * var, uint8_t mul, uint8_t dec, uint8_t aux);
void __upMFmt(void * var, uint8_t mul, uint8_t dec);
void __upSFmt(void * var, uint8_t mul, uint8_t dec);
void ConfigRefresh(uint8_t p);
void ConfigRefresh(uint8_t p);
void configurationLoop();
void fill_line1_deg();
void fill_line2_AmaxA();
void output_V();
void output_Vmin();
void output_mAh();
void fill_line1_cycle();
void fill_line2_cycleMinMax();
void output_fails();
void output_annex();
void outputSensor(uint8_t num, int16_t data, int16_t limit);
void print_uptime(uint16_t sec);
void lcd_telemetry();

#endif /* LCD_H_ */
