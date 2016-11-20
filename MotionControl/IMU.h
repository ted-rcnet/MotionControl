/*
 * IMU.h
 *
 *  Created on: 19 août 2013
 *      Author: rotule
 */

#ifndef IMU_H_
#define IMU_H_

#include <Arduino.h>

void computeIMU ();
int16_t _atan2(int32_t y, int32_t x);
float InvSqrt (float x);
void rotateV(struct fp_vector *v,float* delta);
void getEstimatedAttitude();
uint8_t getEstimatedAltitude();

#define UPDATE_INTERVAL 25000    // 40hz update rate (20hz LPF on acc)
#define BARO_TAB_SIZE   21

#define ACC_Z_DEADBAND (acc_1G>>5) // was 40 instead of 32 now


#define applyDeadband(value, deadband)  \
  if(abs(value) < deadband) {           \
    value = 0;                          \
  } else if(value > 0){                 \
    value -= deadband;                  \
  } else if(value < 0){                 \
    value += deadband;                  \
  }


//******  advanced users settings *******************
/* Set the Low Pass Filter factor for ACC
   Increasing this value would reduce ACC noise (visible in GUI), but would increase ACC lag time
   Comment this if  you do not want filter at all.
   unit = n power of 2 */
// this one is also used for ALT HOLD calculation, should not be changed
#ifndef ACC_LPF_FACTOR
  #define ACC_LPF_FACTOR 4 // that means a LPF of 16
#endif

/* Set the Gyro Weight for Gyro/Acc complementary filter
   Increasing this value would reduce and delay Acc influence on the output of the filter*/
#ifndef GYR_CMPF_FACTOR
  #define GYR_CMPF_FACTOR 600
#endif

/* Set the Gyro Weight for Gyro/Magnetometer complementary filter
   Increasing this value would reduce and delay Magnetometer influence on the output of the filter*/
#define GYR_CMPFM_FACTOR 250

//****** end of advanced users settings *************
#define INV_GYR_CMPF_FACTOR   (1.0f / (GYR_CMPF_FACTOR  + 1.0f))
#define INV_GYR_CMPFM_FACTOR  (1.0f / (GYR_CMPFM_FACTOR + 1.0f))

#define GYRO_SCALE (4 / 16.4 * PI / 180.0 / 1000000.0) // MPU6050)
// +-2000/sec deg scale
// for WMP, empirical value should be #define GYRO_SCALE (1.0f/200e6f)
// !!!!should be adjusted to the rad/sec and be part defined in each gyro sensor

typedef struct fp_vector {
  float X,Y,Z;
} t_fp_vector_def;

typedef union {
  float A[3];
  t_fp_vector_def V;
} t_fp_vector;

typedef struct int32_t_vector {
  int32_t X,Y,Z;
} t_int32_t_vector_def;

typedef union {
  int32_t A[3];
  t_int32_t_vector_def V;
} t_int32_t_vector;


#endif /* IMU_H_ */
