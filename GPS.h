/*
 * GPS.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef GPS_H_
#define GPS_H_

#include <Arduino.h>

int32_t get_P(int32_t error, struct PID_PARAM_* pid);
int32_t get_I(int32_t error, float* dt, struct PID_* pid,
		struct PID_PARAM_* pid_param);
int32_t get_D(int32_t input, float* dt, struct PID_* pid,
		struct PID_PARAM_* pid_param); // dt in milliseconds
void reset_PID(struct PID_* pid);
void SerialGpsPrint(prog_char* str);
void GPS_SerialInit();
void GPS_NewData();
void GPS_reset_home_position();
void GPS_reset_nav();
void GPS_set_pids();
void GPS_calc_longitude_scaling(int32_t lat);
void GPS_set_next_wp(int32_t* lat, int32_t* lon);
void GPS_distance_cm_bearing(int32_t* lat1, int32_t* lon1, int32_t* lat2,
		int32_t* lon2, uint32_t* dist, int32_t* bearing);
uint32_t GPS_coord_to_degrees(char* s);
uint16_t grab_fields(char* src, uint8_t mult);
uint8_t hex_c(uint8_t n);
bool GPS_newFrame(char c);
void _update_checksum(uint8_t *data, uint8_t len, uint8_t &ck_a,
		uint8_t &ck_b);
bool GPS_UBLOX_newFrame(uint8_t data);
bool UBLOX_parse_gps(void);
int32_t wrap_18000(int32_t ang);
int32_t wrap_36000(int32_t ang);

#define DIGIT_TO_VAL(_x)        (_x - '0')



#define _X 1
#define _Y 0

#define RADX100                    0.000174532925
#define CROSSTRACK_GAIN            1
#define NAV_SPEED_MIN              100    // cm/sec
#define NAV_SPEED_MAX              300    // cm/sec
#define NAV_SLOW_NAV               true
#define NAV_BANK_MAX 3000        //30deg max banking when navigating (just for security and testing)

////////////////////////////////////////////////////////////////////////////////////
// moving average filter variables
//

#define GPS_FILTER_VECTOR_LENGTH 5

struct ubx_header {
	uint8_t preamble1;
	uint8_t preamble2;
	uint8_t msg_class;
	uint8_t msg_id;
	uint16_t length;
};
struct ubx_nav_posllh {
	uint32_t time;  // GPS msToW
	int32_t longitude;
	int32_t latitude;
	int32_t altitude_ellipsoid;
	int32_t altitude_msl;
	uint32_t horizontal_accuracy;
	uint32_t vertical_accuracy;
};
struct ubx_nav_solution {
	uint32_t time;
	int32_t time_nsec;
	int16_t week;
	uint8_t fix_type;
	uint8_t fix_status;
	int32_t ecef_x;
	int32_t ecef_y;
	int32_t ecef_z;
	uint32_t position_accuracy_3d;
	int32_t ecef_x_velocity;
	int32_t ecef_y_velocity;
	int32_t ecef_z_velocity;
	uint32_t speed_accuracy;
	uint16_t position_DOP;
	uint8_t res;
	uint8_t satellites;
	uint32_t res2;
};
struct ubx_nav_velned {
	uint32_t time;  // GPS msToW
	int32_t ned_north;
	int32_t ned_east;
	int32_t ned_down;
	uint32_t speed_3d;
	uint32_t speed_2d;
	int32_t heading_2d;
	uint32_t speed_accuracy;
	uint32_t heading_accuracy;
};

enum ubs_protocol_bytes {
	PREAMBLE1 = 0xb5,
	PREAMBLE2 = 0x62,
	CLASS_NAV = 0x01,
	CLASS_ACK = 0x05,
	CLASS_CFG = 0x06,
	MSG_ACK_NACK = 0x00,
	MSG_ACK_ACK = 0x01,
	MSG_POSLLH = 0x2,
	MSG_STATUS = 0x3,
	MSG_SOL = 0x6,
	MSG_VELNED = 0x12,
	MSG_CFG_PRT = 0x00,
	MSG_CFG_RATE = 0x08,
	MSG_CFG_SET_RATE = 0x01,
	MSG_CFG_NAV_SETTINGS = 0x24
};
enum ubs_nav_fix_type {
	FIX_NONE = 0,
	FIX_DEAD_RECKONING = 1,
	FIX_2D = 2,
	FIX_3D = 3,
	FIX_GPS_DEAD_RECKONING = 4,
	FIX_TIME = 5
};
enum ubx_nav_status_bits {
	NAV_STATUS_FIX_VALID = 1
};


#endif /* GPS_H_ */
