/*
 * SD.cpp
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */


#include "MotionControl.h"
#include "SD.h"
#include "config.h"
#include "def.h"
#include "EEPROM.h"
#include "serial.h"
#include "alarms.h"


extern struct flags_struct f;
extern struct permLog plog;
extern uint8_t alarmArray[16];
extern int32_t GPS_mission[4];

#ifdef MWI_SDCARD
#include <SdFat.h>
#include <SdFatUtil.h>
#define PERMANENT_LOG_FILENAME "PERM.TXT"
#define GPS_LOG_FILENAME "GPS_DATA.RAW"

SdFat sd;
ofstream gps_data;	// Log file for GPS raw data
ofstream permanent; // Log file for permanent logging
SdFile mission_file; // comma separated file for missions ( lat/lon/alt/heading )

uint8_t sdFlags = 0;
#define MFO_FLAG_ON 0x01
#define MFO_FLAG_OFF 0xFE

/* Init SD card : assign OUTPUT mode to CSPIN and start SPI mode */
void init_SD() {
	pinMode(CSPIN, OUTPUT); 	// Put CSPIN to OUTPUT for SD library
	if (!sd.begin(CSPIN, SPI_FULL_SPEED)) {
		f.SDCARD = 0;// If init fails, tell the code not to try to write on it
	} else {
		f.SDCARD = 1;
	}
}

#ifdef SDCARD_LOGGER
/* Write GPS log to SD card in csv file format*/
/* Write GPS log to SD card in csv file format*/
void writeGPSLog(int32_t latitude, int32_t longitude, int32_t altitude) {
	if (f.SDCARD == 0)
		return;
	gps_data.open(GPS_LOG_FILENAME, O_WRITE | O_CREAT | O_APPEND);
	char lat_c[11];
	char lon_c[11];
	char alt_c[11];

	snprintf(lat_c, 11, "%ld", latitude);
	snprintf(lon_c, 11, "%ld", longitude);
	snprintf(alt_c, 11, "%ld", altitude);

	gps_data << lat_c << ',' << lon_c << ',' << alt_c << endl;
	gps_data.flush();
	gps_data.close();
}

void writePLogToSD() {
	if (f.SDCARD == 0)
		return;
	char buf[12];
	plog.checksum = calculate_sum((uint8_t*) &plog, sizeof(plog));
	permanent.open(PERMANENT_LOG_FILENAME, O_WRITE | O_CREAT | O_TRUNC);

	permanent << "arm=" << plog.arm << endl;
	permanent << "disarm=" << plog.disarm << endl;
	permanent << "start=" << plog.start << endl;
	permanent << "armed_time=" << plog.armed_time << endl;
	snprintf(buf, 11, "%lu", plog.lifetime);
	permanent << "lifetime=" << buf << endl;
	permanent << "failsafe=" << plog.failsafe << endl;
	permanent << "i2c=" << plog.i2c << endl;
	memset(buf, '\0', 12);
	snprintf(buf, 5, "%hhu", plog.running);
	permanent << "running=" << buf << endl;
	memset(buf, '\0', 12);
	snprintf(buf, 5, "%hhu", plog.checksum);
	permanent << "checksum=" << buf << endl;

	permanent.flush();
	permanent.close();
}

void readPLogFromSD() {
	if (f.SDCARD == 0)
		return;
	char key[12];
	char value[32];
	char* tabPtr = key;
	int c;
	uint8_t i = 0;

	ifstream file("perm.txt");

	while ((c = file.get()) >= 0) {
		switch ((char) c) {
		case ' ':
			break;
		case '=':
			*tabPtr = '\0';
			tabPtr = value;
			break;
		case '\n':
			*tabPtr = '\0';
			tabPtr = key;
			i = 0;
			fillPlogStruct(key, value);
			memset(key, '\0', sizeof(key));
			memset(value, '\0', sizeof(value));
			break;
		default:
			i++;
			if (i <= 12) {
				*tabPtr = (char) c;
				tabPtr++;
			}
			break;
		}
	}

	if (calculate_sum((uint8_t*) &plog, sizeof(plog)) != plog.checksum) {
		blinkLED(9, 100, 3);
#if defined(BUZZER)
		alarmArray[7] = 3;
#endif
		// force load defaults
		plog.arm = plog.disarm = plog.start = plog.failsafe = plog.i2c = 11;
		plog.running = 1;
		plog.lifetime = plog.armed_time = 3;
		writePLogToSD();
	}
}

void fillPlogStruct(char* key, char* value) {
	if (strcmp(key, "arm") == 0)
		sscanf(value, "%u", &plog.arm);
	if (strcmp(key, "disarm") == 0)
		sscanf(value, "%u", &plog.disarm);
	if (strcmp(key, "start") == 0)
		sscanf(value, "%u", &plog.start);
	if (strcmp(key, "armed_time") == 0)
		sscanf(value, "%lu", &plog.armed_time);
	if (strcmp(key, "lifetime") == 0)
		sscanf(value, "%lu", &plog.lifetime);
	if (strcmp(key, "failsafe") == 0)
		sscanf(value, "%u", &plog.failsafe);
	if (strcmp(key, "i2c") == 0)
		sscanf(value, "%u", &plog.i2c);
	if (strcmp(key, "running") == 0)
		sscanf(value, "%hhu", &plog.running);
	if (strcmp(key, "checksum") == 0)
		sscanf(value, "%hhu", &plog.checksum);
}
#endif

#ifdef SDCARD_MISSION
/* Read a line from mission file and transform it in a format readable by GPS code
 * Return error code in case it can't open file or EOF encountered */
uint8_t getNextWp() {
	if (f.SDCARD == 0)
		return 3;

	char line[42];// Line format is : int32,int32,int32,int8 so we need at least 42 characters to read it
	char buf[4][11];
	char* bufPtr = buf[0];
	uint8_t j = 0;
	int16_t n;		// number of characters readed from line

	// Check if we are in the first call so open the file.
	if ((sdFlags & MFO_FLAG_ON) == 0) {
		if (mission_file.open("mission.txt", O_READ))
			sdFlags |= MFO_FLAG_ON;
	}
	if (!(sdFlags & MFO_FLAG_ON))
		return 1;

	if ((n = mission_file.fgets(line, sizeof(line))) <= 0) {
		mission_file.close();	 // last call, end of file or empty line
		sdFlags &= MFO_FLAG_OFF;
		return 2;	// return an error code to inform the main loop to make rth
	}

	// Parse file and read 4 values
	for (int16_t i = 0; i < n; i++) {
		switch (line[i]) {
		case ',':
			*bufPtr = '\0';
			bufPtr = buf[j++];
			break;
		case '\n':
			*bufPtr = '\0';
			break;
		default:
			*bufPtr = line[i];
			bufPtr++;
			break;
		}
	}

	// Put data to the global var for the main loop
	sscanf(buf[0], "%ld", &GPS_mission[LAT]);
	sscanf(buf[1], "%ld", &GPS_mission[LON]);
	sscanf(buf[2], "%ld", &GPS_mission[ALT]);
	sscanf(buf[3], "%ld", &GPS_mission[HEAD]);

	debugmsg_append_str("lat :");
	debugmsg_append_str(buf[0]);
	debugmsg_append_str(" lon :");
	debugmsg_append_str(buf[1]);
	debugmsg_append_str(" alt :");
	debugmsg_append_str(buf[2]);
	debugmsg_append_str(" head :");
	debugmsg_append_str(buf[3]);
	debugmsg_append_str("\n");

	return 0;
}
;
#endif
#endif

