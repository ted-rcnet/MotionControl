/*
 * config.h
 *
 *  Created on: 26 octobre 2013
 *      Author: devteamrcnet
 */

#ifndef CONFIG_H_
#define CONFIG_H_

/*************************************************************************************************/
/***********************************      Type de multi      *************************************/
/*************************************************************************************************/
//#define GIMBAL  /Carte en mode gimbale uniquement / ne vole pas
//#define BI
//#define TRI
//#define QUADP
//#define QUADX
//#define Y4
#define Y6
//#define HEX6
//#define HEX6X
//#define HEX6H  // New Model
//#define OCTOX8
//#define OCTOFLATP
//#define OCTOFLATX
//#define FLYING_WING
//#define VTAIL4
//#define AIRPLANE
//#define SINGLECOPTER
//#define DUALCOPTER
//#define HELI_120_CCPM
//#define HELI_90_DEG

/************************************************************************************************/
/**********************                  Type de carte                    ***********************/
/************************************************************************************************/

/* Si vous avez le module GPS, décommentez la ligne suivante pour activer et configurer le GPS/Magnéto, sinon laissez commenté */
#define FC_GPS       // RCNet FC avec module GPS/Magneto

/*********************************         Régimes moteur        *********************************/
/* Déclaration du régime moteur minimum envoyé a l'ESC
 Ceci est la vitesse de rotation minimum de vos moteurs quand ils entrent en rotation */
//#define MINTHROTTLE 1300 // pour Turnigy Plush ESCs 10A
//#define MINTHROTTLE 1120 // pour Super Simple ESCs 10A
//#define MINTHROTTLE 1064 // pour ESC flashé simonk
//#define MINTHROTTLE 1050 // pour ESC pour moteur a charbon
#define MINTHROTTLE 1150 // (*)
/****************************************************************************/
#define MAXTHROTTLE 2000 //valeur maximum envoyée a l'ESC / vitesse de rotation maximum de vos moteurs vous ne devez pas dépasser 2000
#define MINCOMMAND  1000 // valeur envoyée aux ESCs quand les moteurs ne sont pas armés, il peut être nécéssaire de déscendre en dessous de 900 avec certains ESCs
#define THROTTLE_ANGLE_CORRECTION 40 // ATester, tente de compenser la perte d'altitude due a l'angle de la machine quand elle est en mouvement


/****************************    Commande Moyenne       *****************************************/
/* Ceci est la valeur approximative du point de stabilitée de votre machine, pour un fonctionnement optimal du RTH/RTHA et du SmoothAltHold, 
il faut que cette valeur soit le plus juste possible, a terme, le MIDCMD TUNING se chargera de tout cela */
#define MIDDLECOMMAND 1500
#define AUTO_MIDCMD_TUNING	             // Active la fonction de réglage automatique de "middle comand", la valeur throttle d'équilibre de la machine
#define AUTO_MIDCMD_TUNING_TIME 	2000 // Délais de rafraichissement du midcmd tunning
#define AUTO_MIDCMD_TUNING_MIN_I 	300  // Valeur minimale de errorAltitudeI pour changer la valeur du throttle
#define AUTO_MIDCMD_TUNING_DIV		9	 // diviseur 2^n pour I to thr

/****************************                  GPS                ******************************/
/* GPS_LED_INDICATOR, permets de voir si un fix GPS est présent et le nombre de satellites a l'aide de la led orange de la FC :
   - Pas de fix GPS -> la led clignote rapidement au rythme de la reception des trames GPS
   - Fix GPS ok mais avec moins de 5 satellites -> la led est éteinte
   - Fix GPS ok et reception d'au moins 5 satéllites :
     - 1 clignotement pour 5 satellites 
* Puis 1 clignotement de plus pour chaque satellite en plus (exemple 4 clignotement pour 8 satellites) 
	 - 2 clignotement pour 6 satellites
	 - et ainsi de suite */
#define GPS_LED_INDICATOR

#define USE_MSP_WP                // Activation de la commande MSP_WP qui est utilisé par la GUI pour afficher et loguer les 
/*                                   coordonnées du point home et du point POS HOLD */
//#define DONT_RESET_HOME_AT_ARM  // Si activé, les coordonnées du point home ne sont pas réinitialisés a chaque armement 
/* 									 (vous devrez le déclarer en lançant une calibration Gyro manuellement (via la radio)) */

/* Modifier les réglages de comportement en mode GPS */

#define NAV_CONTROLS_HEADING       true      // true - la machine fait face au point qu'elle vise, le magnetomètre doit absolument être coché pour que cela fonctionne
#define NAV_TAIL_FIRST             true      // true - la machine revient en marche arrière
#define NAV_SET_TAKEOFF_HEADING    true      // true - la machine se tourne dans le même sens qu'au décollage quand elle est arrivé au dessus du point home
#define NAV_ALT_CTRL               true      // true - la machine ira a l'altitude sélectionnée a NAV_RTH_ALTITUDE avant de commencer le retour au point home
#define NAV_RTH_ALTITUDE           2000      // En cms, altitude de retour au point home, par défaut 2000 soit 20mètres
#define NAV_DESCENT_SPEED           100	     // En 1/2cm/s - permets de régler la vitesse de descente lors de l'attérissage 100 correspond aproximativement a 50cm/s
/*											    Vous pouvez augmenter si vous souhaitez que la machine descende un peu plus vite, attention a ne pas trop augmenter */

/*************************    Déclinaison magnétique    ******************************/
/* Calculez votre déclinaison magnétique puis entrez là dans le paramètre MAG_DECLINIATION, est négligeable en france car la déclinaison y est tout au plus de 
*  quelques degrés, néanmoins dans certaines zones du globe, se paramètre est très important, il est dans tous les cas conseillé de l'indiquer
*  et de le mettre a jour, pour assurer une meilleur précision des fonctions GPS, biensur, le calibrage magnéto est encore plus important
*  Pour savoir comment calculer votre déclinaison magnétique, rendez-vous sur ce post qui vous explique tout ceci en détail
*  http://www.rcnet.com/demande-aide-f38/declinaison-magnetique-bizard-t1191.html?hilit=declination#p18680 */
#define MAG_DECLINIATION  -0.88f	//Exemple pour La Rochelle, France

/*************************    Réglages avancés GPS     *******************************/
#define GPS_LEAD_FILTER                  // Ajoute un filtre prédictif pour compenser le temps de réponse assez long d'un GPS ; il est conseillé de le laissé activé
/* 											Code basé sur les travaux de Jason Short */
//#define GPS_FILTERING                  // Ajoute une pondération sur 5 données pour filtrer les données GPS, cela peut aider si vous avez des données erronés 
/* 											de temps a autre, mais nous vous déconseillons de l'utiliser car il amène de la latence */
#define SMOOTH_GPS_HOLD        1   	     // Rend le Poshold plus doux lorsque le multi est en mouvement
#define SMOOTH_GPS_RATIO       3         // Divise l'angle par cette valeur lors de la phase d'activation, cela permets un passage en hold plus doux
#define GPS_HOLD_MIN_ALT       50		 // Altitude minimal avant activation des fontions GPS Evite de décoller en HOLD par exemple ce qui peut amener la machine 
/*											a trainer par terre */
#define GPS_WP_RADIUS        200         // Distance en centimètres a partir de laquelle on considère qu'on est arrivé a destination
#define NAV_SLEW_RATE         30         // Adds a rate control to nav output, will smoothen out nav angle spikes

/************************        AP FlightMode        **********************************/
/* Lorsque vous êtes en GPS HOLD, vous pouvez reprendre les commandes a tout moment pour ajuster la position de la machine
   Cela désactive temporairement le GPS HOLD */
#define AP_MODE 40  // Cela permets de créer une zone neutre de taille définie par la valeur 
/* dans sur laquelle les sticks ne désactivent pas le GPS HOLD */

/********                          Failsafe settings                 ********************/
/* Le failsafe surveille les 4 voies procipales, s'il n'y a plus de signal ou s'il descend en dessous de 985us (sur l'une des 4 voies)
 La procédure de failsafe s'enclanche, elle commence par une temporisation (FAILSAFE_DELAY), au terme de cette temporisation,
 La machine passe automatiquement en mode stable, les valeurs des voies Pitch/Roll/Yaw est réglé au centre et le throttle est réglé
 a la valeur entrée dans FAILSAFE_THROTTLE pendant la durée choisie dans FAILSAFE_OFF_DELAY. Au terme de ce délais, les moteurs sont
 désarmés.
 Si vous cochez en plus FAILSAFE_RTH, la machine tentera de revenir a l'aide du GPS a son point de départ, si biensur un GPS est connecté, 
 fonctionnel et qu'un point homme a pu être enregistré au décollage */
#define FAILSAFE                                  // Décommentez pour activer la fonction failsafe
#define FAILSAFE_RTHA			                  // La machine essaira de faire un RTHA en cas d'activation du failsafe 
/* !Pensez a augmenter la durée FAILSAFE_OFF_DELAY de façon a ce que la machine ai le temps de revenir avant désarmement des moteurs */
#define FAILSAFE_DELAY     10                     // Délais avant activation du failsafe (après perte de signal) 1 step = 0.1sec - Par défaut 1seconde
#define FAILSAFE_OFF_DELAY 1200                   // Délais avant désarmement automatique des moteurs, !passé ce délais, les moteurs sont désarmés, 
/* que le multi soit au sol ou non; 1 step = 0.1sec - Par défaut 2 minutes (120secondes) */
#define FAILSAFE_THROTTLE  (MINTHROTTLE + 200)    // Valeur des gaz pendant le failsafe (sauf si le FAILSAFE_RTHA est activé) 
/* doit correspondre a une valeur a laquelle votre machine descend ni trop lentement, ni trop rapidement */

/************************************    Serial com speed    *************************************/
/* Vous pouvez ici modifier la vitesse des ports série (A ne modifier que si vous êtes sûr de ce que vous faites) */
#define SERIAL0_COM_SPEED 115200   //Port utilisé par le FTDI et le module OSD
#define SERIAL1_COM_SPEED 115200
#define SERIAL2_COM_SPEED 115200   //Port utilisé par le module GPS
#define SERIAL3_COM_SPEED 115200

/* Quand des erreurs apparaissent sur le bus I2C, on arrête d'intérroger le capteur en question pendant un court instant
 * ce délais est exprimé en microsecondes et est modifiable ici (utilisateur avancé) */
#define NEUTRALIZE_DELAY 100000

/*************************************************************************************************/
/****************                         Radio                                            *******/
/*************************************************************************************************/

/* Ca paramètre permets d'ajouter une zone neutre autour du centre des stick (sauf throttle) */
#define DEADBAND 12

/* Information : Il est inutile de décommenter quoi que ce soit si vous utilisez une radio standard en PCM */

/*********************************    Recepteur PPM Sum    **************************************/
/* Ces lignes ne concerne que les radio en PPM SUM, selectionnez la ligne correspondante a votre matériel
 Vous pouvez modifier l'ordre a votre guise et en fonction de votre matériel */
//#define SERIAL_SUM_PPM         PITCH,YAW,THROTTLE,ROLL,AUX1,AUX2,AUX3,AUX4,8,9,10,11 //Pré-réglage pour Graupner/Spektrum
//#define SERIAL_SUM_PPM         ROLL,PITCH,THROTTLE,YAW,AUX1,AUX2,AUX3,AUX4,8,9,10,11 //Pré-réglage pour Robe/Hitec/Futaba
//#define SERIAL_SUM_PPM         ROLL,PITCH,YAW,THROTTLE,AUX1,AUX2,AUX3,AUX4,8,9,10,11 //Pré-réglage pour Multiplex
//#define SERIAL_SUM_PPM         PITCH,ROLL,THROTTLE,YAW,AUX1,AUX2,AUX3,AUX4,8,9,10,11 //Pré-réglage pour certaines radios Hitec/Sanwa/Autres
//#define PPM_ON_THROTTLE 	// Utiliser le PIN Throttle pour connecter le recepteur PPM

/****************************    Spektrum Satellite Reciver    *********************************/
/* Uniquement pour les recepteurs Spektrum Satellite, attention ces RX sont alimentable en 3V uniquement ! Ne connectez pas sur le +5
 Connectez le fil gris sur RX1, pin 19. Le fil noir sur la masse, le fil orange sur une alimentation 3,3V (entre 3 et 3,3V uniquement). */
//#define SPEKTRUM 1024
//#define SPEKTRUM 2048
//#define SPEK_SERIAL_PORT 1    // Choisissez le port souhaité, par défaut = 1
//**************************
// Defines that allow a "Bind" of a Spektrum or Compatible Remote Receiver (aka Satellite) via Configuration GUI.
//   Bind mode will be same as declared above, if your TX is capable.
//   Ground, Power, and Signal must come from three adjacent pins.
//   By default, these are Ground=4, Power=5, Signal=6.  These pins are in a row on most MultiWii shield boards. Pins can be overriden below.
//   Normally use 3.3V regulator is needed on the power pin!!  If your satellite hangs during bind (blinks, but won't complete bind with a solid light), go direct 5V on all pins.

/***********************************    Recepteur SBUS     *************************************/
/* Recepteur Futaba S-Bus sur port série 1 uniquement. Il vous faut inverser le signal du S-Bus avec un Hex-Inverter comme l'IC SN74 LS 04*/
//#define SBUS

/******************* RC signal from the serial port via Multiwii Serial Protocol ***************/
//#define RCSERIAL

/*********************************    ARMEMENT/DESARMEMENT    **********************************/
/* Vous pouvez modifier la combinaison des sticks permettant d'armer les moteurs / Il n'est pas conseillé d'activer les deux options */
#define ALLOW_ARM_DISARM_VIA_TX_YAW 	//Option par défaut
//#define ALLOW_ARM_DISARM_VIA_TX_ROLL

/**************************                  RSSI                *******************************/
/*********** Activation du RSSI, deux choix RSSI, ou RSSI FRSKY ***********/
//#define RX_RSSI 	 	//Ne pas activer si vous activez le RSSI_FRSKY
#define RX_RSSI_FRSky   // Voir ici pour plus d'infos http://www.rcnet.com/tuto-multiwii/frsky-rssi-sur-multiwii-t1688.html

/*********** Choix du PIN pour le RSSI ***********/
//#define RX_RSSI_PIN A3 // Utilisation du PIN A3 pour le RSSI
#define RX_RSSI_PIN A9   // PIN ROLL Conseillé en PPM-SUM, ainsi le cable PPM et le RSSI sont l'un a coté de l'autre

/**************************                 Buzzer               *******************************/
#define BUZZER					// Activation du buzzer
#define RCOPTIONSBEEP           // Décommentez si vous souhaitez que le buzzer bip a chaque action sur l'un des aux de 1 à 4
//#define ARMEDTIMEWARNING 330  // Vous alerte après un délais en seconde si la machine est restée armée pour eviter de vider inutilement la lipo
//#define PILOTLAMP             // Décommentez si vous avez une X-Arcraft Pilot Lamp

/**************************    Surveillance voltage Batterie     *******************************/
/* Pour la surveillance du voltage de la LiPo
   La RCNet FC intègre les résistances nécéssaire a la lecture d'un voltage d'une lipo jusqu'a 4S
   voici le calcul du vbat = [0;1023]*16/PLEVELSCALE
   pour que cela ai un interret, vous devez activer le Buzzer et avoir un buzzer connecté, sinon la FC ne pourra pas vous avertir */
#define VBAT                 // Décommentez pour activer le VBAT
#define VBATSCALE        90  // Pour une lipo 3S la valeur moyenne est de 90 (il est possible que vous ayez besoins d'ajuster), pour une lipo 4S, mettre 131
#define VBATNOMINAL     126  // voltage de la batterie plein, 12,6V - utilisé uniquement pour lcd.telemetry
#define VBATLEVEL_WARN1 107  // (*) Alarme 1 - défaut 10,7V
#define VBATLEVEL_WARN2  99  // (*) Alarme 2 - défaut 9.9V
#define VBATLEVEL_CRIT   93  // (*) Alarme critique - défaut 9.3V - condition critique: si le VBAT descend en dessous de cette valeur l'arm est permanente
#define NO_VBAT          16  // (*) Evite de biper quand il n'y a pas de Lipo (connecté en FTDI par exemple)

/*************************     Stabilisation Caméra      **************************/
/* Activation et réglage de la stabilisation Pitch/Roll avec une gimbale munie de servos. 
* Décommentez une des deux options (seulement) suivant votre configuration pour l'activer */
//#define SERVO_MIX_TILT
//#define SERVO_TILT
/**** Réglages des courses, centres et proportionnalitées  des servos ****/
#define TILT_PITCH_MIN    1020    // servo course minimum, ne pas descendre en dessous de 1020
#define TILT_PITCH_MAX    2000    // servo course maximum, ne pas monter au delà de 2000
#define TILT_PITCH_MIDDLE 1500    // servo valeur du centre value
#define TILT_PITCH_PROP   10      // servo coéfficient de proportionnalitée, permets de corriger l'angle ; peut être négatif pour inverser le servo
#define TILT_PITCH_AUX_CH AUX3    // Canal Aux pour le Pitch (AUX1-AUX4), commentez pour désactiver et libérer le canal auxilière
#define TILT_ROLL_MIN     1020    // servo course minimum, ne pas descendre en dessous de 1020
#define TILT_ROLL_MAX     2000    // servo course maximum, ne pas monter au delà de 2000
#define TILT_ROLL_MIDDLE  1500    // servo coéfficient de proportionnalitée, permets de corriger l'angle ; peut être négatif pour inverser le servo
#define TILT_ROLL_PROP    10      // servo valeur du centre value
#define TILT_ROLL_AUX_CH  AUX4    // Canal Aux pour le Pitch (AUX1-AUX4), commentez pour désactiver et libérer le canal auxilière
//#define CAMTRIG				  // Activation du déclencheur d'appareil photo /caméra
/**** Réglages des courses, centre et proportionnalitée  du servo de déclenchement de l'appareil photo ****/
#define CAM_SERVO_HIGH 2000       // Position maxi du servo
#define CAM_SERVO_LOW  1020       // Position mini du servo
#define CAM_TIME_HIGH  1000       // Durée pendant laquelle le servo reste en position haute en millisecondes
#define CAM_TIME_LOW   1000       // Durée pendant laquelle le servo reste en position basse en millisecondes

/**************************************************************************************/
/*********************               Gyro filter               ************************/
/**************************************************************************************/

/*****************    Filtre passe bas pour le gyro du MPU6050   **********************/
/* Permets d'éliminer les vibrations sur le Gyro, souvent dûes à un souci de chassis, hélice(s), moteur(s)
   Attention cela ne remplace pas l'équilibrage des hélices/moteurs ou tout autre source de vibrations
   Cela ne remplace pas non plus les réglages PID si la machine sur ou sous corrige */
//#define MPU6050_LPF_256HZ     // Ceci est la paramètres par défaut si rien n'est décommenté
//#define MPU6050_LPF_188HZ
#define MPU6050_LPF_98HZ        // Ceci est le paramètre conseillé par le DevTeam, cela donne de très bons résultats sur chassis F450/TBS et bien d'autres
//#define MPU6050_LPF_42HZ
//#define MPU6050_LPF_20HZ
//#define MPU6050_LPF_10HZ
//#define MPU6050_LPF_5HZ       /* A n'utiliser que dans un cas très extrême, si vous devez en arriver a une telle valeur, 
/*                                 vous devriez envisager de changer d'hélices et/ou de moteurs etc. */

/********************      recalibrage du gyro si mauvaises conditions        ********************/
/* Le calibrage du gyroscope est relancé si la machine a bougé pendant le calibrage.
 * Pour rappel la machine doit rester immobile pendant le calibrage du gyro et donc a chaque démarrage */
#define GYROCALIBRATIONFAILSAFE // Active le recalibrage si la machine a bougé pendant la phase de calibrage

/*****************                Gyro smoothing    **********************************/
/* GYRO_SMOOTHING. Dans le cas ou aucune solution ne vous a permis de réduire les vibrations et après avoir tenté le filtre passe bas
 * vous pouvez tenter cette option, néanmoins nous vous le déconseillons, tout comme allez trop loin dans le filtre passe bas,
 * utiliser le gyro smoothing contourne un souci majeur de vibraton mais cela ne résouds en rien le problème
 * L'utilisation de ce paramètre est vivement déconseillé pour les multis */
//#define GYRO_SMOOTHING {20, 20, 3}    // vous pouvez spécifier une valeur pour chacune des directions roll, pitch, yaw

/************************    Moving Average Gyros    **********************************/
//#define MMGYRO 10                      //
//#define MMGYROVECTORLENGTH 15          // Length of Moving Average Vector (maximum value for tunable MMGYRO
/******************* Moving Average ServoGimbal Signal Output *************************/
//#define MMSERVOGIMBAL                  // Active Output Moving Average Function for Servos Gimbal
//#define MMSERVOGIMBALVECTORLENGHT 32   // Lenght of Moving Average Vector

/********************************************************************/
/****           altitude hold                                    ****/
/********************************************************************/

/* uncomment to disable the altitude hold feature.
 * This is useful if all of the following apply
 * + you have a baro
 * + want altitude readout
 * + do not use altitude hold feature
 * + want to save memory space
 */
//#define SUPPRESS_BARO_ALTHOLD
/* Natural alt change for rapid pilots. It's temporary switch OFF the althold when throttle stick is out of deadband defined with ALT_HOLD_THROTTLE_NEUTRAL_ZONE
 * but if it's commented: Smooth alt change routine is activated, for slow auto and aerophoto modes (in general solution from alexmos). It's slowly increase/decrease
 * altitude proportional to stick movement (+/-100 throttle gives about +/-50 cm in 1 second with cycle time about 3-4ms)
 */
//#define ALTHOLD_FAST_THROTTLE_CHANGE  
/* ATO est une fonction permettant d'avoir un décollage automatique géré au baromètre. Pour l'activer, activez le baromètre avant d'armer les moteurs
   Ensuite, la machine décollera quand le throttle aura atteint 1500, le décollage sera géré par le logiciel jusqu'a l'altitude choisi ci-après
   Si la machine est en mode GPS Hold, celui-ci sera actif dès que vous aurez dépassé l'altitude minimum d'activation du GPS, voir plus loin */
#define AUTO_TAKE_OFF		        // Activation de la fonction ATO
#define AUTO_TAKE_OFF_ALTITUDE 200  // Altitude a laquelle la machine doit aller durant l'ATO, en cm, par défaut, 200 = 2m

/********************************************************************/
/****           altitude variometer                              ****/
/********************************************************************/

/* Utilisé pour le RTHA et l'OSD, nous vous conseillons de ne pas le désactiver 
 * Il est possible de choisir entre 2 modes ou de les activer tous les deux, nous vous conseillons de laisser par défaut, les deux actifs
 * method 1 : use short term movement from baro ( bigger code size)
 * method 2 : use long term observation of altitude from baro (smaller code size */
#define VARIOMETER 12              // Valeurs par défaut (conseillée) :12 (1&2 actifs)
//#define SUPPRESS_VARIOMETER_UP   // if no signaling for up movement is desired
//#define SUPPRESS_VARIOMETER_DOWN // if no signaling for down movement is desired
//#define VARIOMETER_SINGLE_TONE   // use only one tone (BEL); neccessary for non-patched vt100 terminals

/******************** Modification manuelle de l'orientation des capteurs ***********************/ 
/*  a modifier si votre carte est installé différemment de l'orientation du manuel / de la fleche */
//#define FORCE_ACC_ORIENTATION(X, Y, Z)  {accADC[ROLL]  =  Y; accADC[PITCH]  = -X; accADC[YAW]  = Z;}
//#define FORCE_GYRO_ORIENTATION(X, Y, Z) {gyroADC[ROLL] = -Y; gyroADC[PITCH] =  X; gyroADC[YAW] = Z;}
//#define FORCE_MAG_ORIENTATION(X, Y, Z)  {magADC[ROLL]  =  X; magADC[PITCH]  =  Y; magADC[YAW]  = Z;}

/* Décallage des capteurs de 45° */
/* Si vous avez un chassis prévu pour voler en + et que votre carte ne peut être installée pour un vol en X
*  Vous pouvez ici appliquer une rotation de 45° a la carte sur l'axe Yaw pour adapter la machine a votre configuration de vol
*  Paramètre est optionnel, n'activez qu'une seule options, et faites attention a ce que l'ordre des moteurs soit respecté */
//#define SENSORS_TILT_45DEG_RIGHT        // pivote l'avant de la carte sur 45° dans le sens des aiguilles d'une montre
//#define SENSORS_TILT_45DEG_LEFT         // pivote l'avant de la carte sur 45° dans le sens inverse des aiguilles d'une montre

/*************************************************************************************************/
/**************************        Machines spécifiques          *********************************/
/*************************************************************************************************/

/*********************************    TRICOPTER    *********************************/
#define YAW_DIRECTION 1
//#define YAW_DIRECTION -1 // Si vous souhaitez inverser la direction sur l'axe Yaw
/* Ceci vous permets de modifier la course des servos */
#define TRI_YAW_CONSTRAINT_MIN 1020
#define TRI_YAW_CONSTRAINT_MAX 2000
#define TRI_YAW_MIDDLE 1500 /* (*) Entrez la position centrale du servo de queue
*  Uniquement pour le réglage initial, le trim se fait ensuite par le LCD */

/*********************************    BICOPTER    *********************************/
/* Ceci vous permets d'inverser la course des servos */
//#define BI_PITCH_DIRECTION 1
#define BI_PITCH_DIRECTION -1

/***********************          Flying Wing               ***********************/
/* Vous pouvez changer l'orientation des servos ainsi que leur valeur maxi et mini utilisé pour tous les modes de vol, même le passThrough
*  Il est nécéssaire de configurer le sens des servos ici; pas besoins d'échanger les voies des servos sur le RX */
#define PITCH_DIRECTION_L 1   // servo gauche - orientation pitch 
#define PITCH_DIRECTION_R -1  // servo droit - orientation pitch (opposite sign to PITCH_DIRECTION_L, if servos are mounted in mirrored orientation)
#define ROLL_DIRECTION_L 1    // servo gauche - orientation roll
#define ROLL_DIRECTION_R 1    // servo droit- orientation roll  (same sign as ROLL_DIRECTION_L, if servos are mounted in mirrored orientation)
#define WING_LEFT_MID  1500   // (*) servo gauche position central - a utilisé pour le réglage initial, ensuite trimer avec le LCD
#define WING_RIGHT_MID 1500   // (*) servo droit position central - a utilisé pour le réglage initial, ensuite trimer avec le LCD
#define WING_LEFT_MIN  1020   // limiter la course du servo, doit être entre [1020;2000]
#define WING_LEFT_MAX  2000   // limiter la course du servo, doit être entre [1020;2000]
#define WING_RIGHT_MIN 1020   // limiter la course du servo, doit être entre [1020;2000]
#define WING_RIGHT_MAX 2000   // limiter la course du servo, doit être entre [1020;2000]

/***********************             Avion                       ***********************/
//#define USE_THROTTLESERVO // Permets d'utiliser un servo standard 50Hz sur le Throttle
#define SERVO_RATES      {100, 100, 100, 100, 100, 100, 100, 100} // Rates 0-100%
#define SERVO_DIRECTION  { -1,   1,   1,   -1,  1,   1,   1,   1 } // Sens des servo, -1 : inversé
//#define FLAPPERONS    AUX4            // Mix Flaps with Aileroins.
#define FLAPPERON_EP   { 1500, 1700 }   // Endpooints for flaps on a 2 way switch else set {1020,2000} and program in radio.
//#define FLAPPERON_EP   { 1200, 1500 } // Or Flapperons up for CrowMix
#define FLAPPERON_INVERT { 1, -1 }      // Change direction om flapperons { Wing1, Wing2 }
//#define FLAPS         AUX4            // Traditional Flaps on A2 invert with SERVO_DIRECTION servo[2).
#define FLAP_EP      { 1500, 1900 }     // Endpooints for flaps on a 2 way switch else set {1020,2000} and program in radio.
//#define FLAPSPEED     3               // Make flaps move slowm Higher value is Higher Speed.

/***********************      Commun pour Heli & Airplane         **********************/
#define SERVO_OFFSET     {  0,   0,   0,  0,   0,   0,  0,   0 } // (*) Adjust Servo MID Offset & Swash angles
// Selectable channels:=    ROLL,PITCH,THROTTLE,YAW,AUX1,AUX2,AUX3,AUX4

/* Governor: attempts to maintain rpm through pitch and voltage changes
 * predictive approach: observe input signals and voltage and guess appropriate corrections.
 * (the throttle curve must leave room for the governor, so 0-50-75-80-80 is ok, 0-50-95-100-100 is _not_ ok.
 * Can be toggled via aux switch.*/
//#define GOVERNOR_P 7   //proportional factor. Higher value -> higher throttle increase. Must be >=1; 0 = turn off
//#define GOVERNOR_D 4   //decay timing. Higher value -> takes longer to return throttle to normal. Must be >=1;
//#define GOVERNOR_R 10  //voltage impact correction scale in 0.1 units. Higher value -> more compensation for voltage drops. normal is value 10 <=> 1.0; 0 is off

/***********************          Heli                           ***********************/
/* Channel to control CollectivePitch */
#define COLLECTIVE_PITCH      THROTTLE
/* Set Maximum available movement for the servos. Depending on model */
#define SERVO_ENDPOINT_HIGH {2000,2000,2000,2000,2000,2000,2000,2000};
#define SERVO_ENDPOINT_LOW  {1020,1020,1020,1020,1020,1020,1020,1020};

/* Limit the range of Collective Pitch. 100% is Full Range each way and position for Zero Pitch */
#define COLLECTIVE_RANGE { 80, 0, 80 }// {Min%, ZeroPitch offset from 1500, Max%}.
#define YAW_CENTER             1500      // Use servo[5] SERVO_ENDPOINT_HIGH/LOW for the endpoits.
#define YAWMOTOR                 0       // If a motor is used as YAW Set to 1 else set to 0.
/* Servo mixing for heli 120 Use 1/10 fractions (ex.5 = 5/10 = 1/2)
 {Coll,Nick,Roll} */
#define SERVO_NICK   { +10, -10, -0 }
#define SERVO_LEFT   { +10, +5, +10 }
#define SERVO_RIGHT  { +10, +5, -10 }

/* Servo mixing for heli 90
 {Coll,Nick,Roll} */
#define SERVO_DIRECTIONS { +1, -1, -1 } // -1 will invert servo
/* Limit Maximum controll for Roll & Nick  in 0-100% */
#define CONTROL_RANGE   { 100, 100 }      //  { ROLL,PITCH }
/* use servo code to drive the throttle output. You want this for analog servo driving the throttle on IC engines.
 if inactive, throttle output will be treated as a motor output, so it can drive an ESC */
//#define HELI_USE_SERVO_FOR_THROTTLE

/***********************      Single and DualCopter Settings     ***********************/
/**** Réglages pour SingleCopter, vous pouvez inverser le mouvement des servo sur un ou plusieurs axes en mettant -1 a la place de 1 ****/
#define SINGLECOPTRER_YAW   {1, 1, 1, 1} // Gauche, Droite, Avant, Arrière
#define SINGLECOPTRER_SERVO {1,-1, 1,-1} // Pitch, Pitch, Roll, Roll
/******* Réglages servo pour DualCopter *******/
#define DUALCOPTER_SERVO {1,1} //Pitch,Roll
/* Utilisez SERVO_OFFSET et SERVO_RATES dans les sections Heli et Airplane pour le centre et les courses maximums */


/****************  SECTION  6 - OPTIONAL FEATURES         *****************************/


/*****************                DFRobot LED RING    *********************************/
/* Activation d'I2C DFRobot LED RING */
//#define LED_RING
/********************************    LED FLASHER    ***********************************/
//#define LED_FLASHER
//#define LED_FLASHER_DDR DDRB
//#define LED_FLASHER_PORT PORTB
//#define LED_FLASHER_BIT PORTB4
//#define LED_FLASHER_INVERT
//#define LED_FLASHER_SEQUENCE        0b00000000      // leds éteintes
//#define LED_FLASHER_SEQUENCE_ARMED  0b00000101      // flash deux fois
//#define LED_FLASHER_SEQUENCE_MAX    0b11111111      // allumé fixe
//#define LED_FLASHER_SEQUENCE_LOW    0b00000000      // allumé faiblement
/*******************************    Landing lights    *********************************/
/* Landing lights
 Use an output pin to control landing lights.
 They can be switched automatically when used in conjunction
 with altitude data from a sonar unit. */
//#define LANDING_LIGHTS_DDR DDRC
//#define LANDING_LIGHTS_PORT PORTC
//#define LANDING_LIGHTS_BIT PORTC0
//#define LANDING_LIGHTS_INVERT
/* altitude above ground (in cm) as reported by sonar */
//#define LANDING_LIGHTS_AUTO_ALTITUDE 50
/* adopt the flasher pattern for landing light LEDs */
//#define LANDING_LIGHTS_ADOPT_LED_FLASHER_PATTERN

/*************************    INFLIGHT ACC Calibration    *****************************/
/* Ce paramètre active la possibilité de calibrer acceleromètre en vol */
//#define INFLIGHT_ACC_CALIBRATION

/*******************************    OSD Switch    *************************************/
// This adds a box that can be interpreted by OSD in activation status (to switch on/off the overlay for instance)
//#define OSD_SWITCH

/********************************************************************/
/****           powermeter (battery capacity monitoring)         ****/
/********************************************************************/

/* Active la surveillance de la consommation electrique (en mAh), permets de créer une alarme visible dans l'OSD, la GUI ou sur un LCD
Description complète et tutoriel ici :  http://www.multiwii.com/wiki/index.php?title=Powermeter
	Deux Options :
 1 - hard: - (utilisation d'un détecteur matériel, donne de très bons résultats après réglages)
 2 - soft: - (méthode logiciel, résultats correctes a +-5% environ avec des ESC plush/mystery en 2s et 3s, correct avec des supersimple esc */
//#define POWERMETER_SOFT
//#define POWERMETER_HARD
/* PLEVELSCALE is the step size you can use to set alarm */
#define PLEVELSCALE 50 // if you change this value for other granularity, you must search for comments in code to change accordingly
/* larger PLEVELDIV will get you smaller value for power (mAh equivalent) */
#define PLEVELDIV 5000 // (*) default for soft - if you lower PLEVELDIV, beware of overrun in uint32 pMeter
#define PLEVELDIVSOFT PLEVELDIV // for soft always equal to PLEVELDIV; for hard set to 5000
#define PSENSORNULL 510 // (*) set to analogRead() value for zero current; for I=0A my sensor gives 1/2 Vss; that is approx 2.49Volt;
#define PINT2mA 13 // (*) for telemtry display: one integer step on arduino analog translates to mA (example 4.9 / 37 * 100


/**************************************************************************************/
/***********************        LCD/OLED - display settings       *********************/
/**************************************************************************************/
/* http://www.multiwii.com/wiki/index.php?title=Extra_features#LCD_.2F_OLED */

/*****************************   The type of LCD     **********************************/
/* choice of LCD attached for configuration and telemetry, see notes below */
//#define LCD_DUMMY       // No Physical LCD attached.  With this & LCD_CONF defined, TX sticks still work to set gains, by watching LED blink.
//#define LCD_SERIAL3W    // Alex' initial variant with 3 wires, using rx-pin for transmission @9600 baud fixed
//#define LCD_TEXTSTAR    // SERIAL LCD: Cat's Whisker LCD_TEXTSTAR Module CW-LCD-02 (Which has 4 input keys for selecting menus)
//#define LCD_VT100       // SERIAL LCD: vt100 compatible terminal emulation (blueterm, putty, etc.)
//#define LCD_TTY         // SERIAL LCD: useful to tweak parameters over cable with arduino IDE 'serial monitor'
//#define LCD_ETPP        // I2C LCD: Eagle Tree Power Panel LCD, which is i2c (not serial)
//#define LCD_LCD03       // I2C LCD: LCD03, which is i2c
//#define OLED_I2C_128x64 // I2C LCD: OLED http://www.multiwii.com/forum/viewtopic.php?f=7&t=1350
/******************************   Display settings   ***********************************/
#define LCD_SERIAL_PORT 0    // must be 0 on Pro Mini and single serial boards; Set to your choice on any Mega based board
//#define SUPPRESS_OLED_I2C_128x64LOGO  // suppress display of OLED logo to save memory

/* double font height for better readability. Reduces visible #lines by half.
 * The lower part of each page is accessible under the name of shifted keyboard letter :
 * 1 - ! , 2 - @ , 3 - # , 4 - $ , 5 - % , 6 - ^ , 7 - & , 8 - * , 9 - (
 * You must add both to your lcd.telemetry.* sequences
 */
//#define DISPLAY_FONT_DSIZE //currently only aplicable for OLED_I2C_128x64
/* style of display - AUTODETECTED via LCD_ setting - only activate to override defaults */
//#define DISPLAY_2LINES
//#define DISPLAY_MULTILINE
//#define MULTILINE_PRE 2  // multiline configMenu # pref lines
//#define MULTILINE_POST 6 // multiline configMenu # post lines
/********************************    Navigation     ***********************************/
/* keys to navigate the LCD menu */
#define LCD_MENU_PREV 'p'
#define LCD_MENU_NEXT 'n'
#define LCD_VALUE_UP 'u'
#define LCD_VALUE_DOWN 'd'
#define LCD_MENU_SAVE_EXIT 's'
#define LCD_MENU_ABORT 'x'

/**************************************************************************************/
/***********************      LCD configuration menu         **************************/
/**************************************************************************************/

/* uncomment this line if you plan to use a LCD or OLED for tweaking parameters
 * http://www.multiwii.com/wiki/index.php?title=Extra_features#Configuration_Menu */
//#define LCD_CONF
/* to include setting the aux switches for AUX1 -> AUX4 via LCD */
//#define LCD_CONF_AUX
/* optional exclude some functionality - uncomment to suppress some unwanted telemetry pages */
//#define SUPPRESS_LCD_CONF_AUX34

/**************************************************************************************/
/***********************      LCD       telemetry            **************************/
/**************************************************************************************/

/* to monitor system values (battery level, loop time etc. with LCD
 * http://www.multiwii.com/wiki/index.php?title=LCD_Telemetry */

/********************************    Activation     ***********************************/
//#define LCD_TELEMETRY
/* to enable automatic hopping between a choice of telemetry pages uncomment this. */
//#define LCD_TELEMETRY_AUTO "123452679" // pages 1 to 9 in ascending order
//#define LCD_TELEMETRY_AUTO  "212232425262729" // strong emphasis on page 2
/* manual stepping sequence; first page of the sequence gets loaded at startup to allow non-interactive display */
//#define LCD_TELEMETRY_STEP "0123456789" // should contain a 0 to allow switching off.
/* optional exclude some functionality - uncomment to suppress some unwanted telemetry pages */
//#define SUPPRESS_TELEMETRY_PAGE_1
//#define SUPPRESS_TELEMETRY_PAGE_2
//#define SUPPRESS_TELEMETRY_PAGE_3
//#define SUPPRESS_TELEMETRY_PAGE_4
//#define SUPPRESS_TELEMETRY_PAGE_5
//#define SUPPRESS_TELEMETRY_PAGE_6
//#define SUPPRESS_TELEMETRY_PAGE_7
//#define SUPPRESS_TELEMETRY_PAGE_8
//#define SUPPRESS_TELEMETRY_PAGE_9

/********************** Capteurs additionnels indépendants ***************************/
/* Sonar uniquement a des fins de test visuels - le sonar n'est pas actuellement utilisé par le code de vol */
//#define SRF02 // use the Devantech SRF i2c sensors
//#define SRF08
//#define SRF10
//#define SRF23

/* interleaving delay in micro seconds between 2 readings WMP/NK in a WMP+NK config
 if the ACC calibration time is very long (20 or 30s), try to increase this delay up to 4000
 it is relevent only for a conf with NK */
#define INTERLEAVING_DELAY 3000

/*************************       your individual mixing       *************************/
/* if you want to override an existing entry in the mixing table, you may want to avoid editing the
 * mixTable() function for every version again and again.
 * howto: http://www.multiwii.com/wiki/index.php?title=Config.h#Individual_Mixing
 */
//#define MY_PRIVATE_MIXING "filename.h"
//#define LEAVE_HEADROOM_FOR_MOTORS 4 // leave room for gyro corrrections only for first 4 motors

/*************   Modifier l'assignation des PINs (utilisateur avancé)    *************************/
/* Ne décommentez que les lignes pour lesquels vous souhaitez modifier l'assignation de PIN, attention, ces modifications ne sont pas anodines 
 * la conception de la carte fait que certains PINs ne peuvent être utilisé pour une autre destination que celle prévue a cet effet, 
 * vous effectuez ces modifications sous votre responsabilitée */
/* you may need to change PINx and PORTx plus #shift according to the desired pin! */
//#define OVERRIDE_V_BATPIN                   A0 // instead of A3    // Analog PIN 3
//#define OVERRIDE_LEDPIN_PINMODE             pinMode (A1, OUTPUT); // use A1 instead of d13
//#define OVERRIDE_LEDPIN_TOGGLE              PINC |= 1<<1; // PINB |= 1<<5;     //switch LEDPIN state (digital PIN 13)
//#define OVERRIDE_LEDPIN_OFF                 PORTC &= ~(1<<1); // PORTB &= ~(1<<5);
//#define OVERRIDE_LEDPIN_ON                  PORTC |= 1<<1;    // was PORTB |= (1<<5);
//#define OVERRIDE_BUZZERPIN_PINMODE          pinMode (A2, OUTPUT); // use A2 instead of d8
//#define OVERRIDE_BUZZERPIN_ON               PORTC |= 1<<2 //PORTB |= 1;
//#define OVERRIDE_BUZZERPIN_OFF              PORTC &= ~(1<<2); //PORTB &= ~1;


/****************           baord naming                           *******************/


/*
 * this name is displayed together with the MultiWii version number
 * upon powerup on the LCD.
 * If you are without a DISPLAYD then You may enable LCD_TTY and
 * use arduino IDE's serial monitor to view the info.
 *
 * You must preserve the format of this string!
 * It must be 16 characters total,
 * The last 4 characters will be overwritten with the version number.
 */
#define BOARD_NAME "MultiWii   V-.--"
//                  123456789.123456

/*************      Support multiple configuration profiles in EEPROM     ************/
//#define MULTIPLE_CONFIGURATION_PROFILES

/*************************************************************************************************/
/*****************                                                                 ***************/
/****************  SECTION  7 - TUNING & DEVELOPER                                  **************/
/*****************                                                                 ***************/
/*************************************************************************************************/

/************ Experimental: force a stable, fixated (high) cycle time       **********/
/* when activated, the displayed cycle time in GUI will not be correct.
 * Tunable via LCD config menu.
 * value of 0 turns the feature off.
 */
//#define CYCLETIME_FIXATED 9000 // (*)

/********   special ESC with extended range [0-2000] microseconds  ********************/
//#define EXT_MOTOR_RANGE

/***********************     motor, servo and other presets     ***********************/
/* A l'armement, les moteurs ne tournent pas au ralenti, mais dès que vous poussez le throttle ils démarrent
 Ce mode peut se révéler dangereux, vous pourriez pousser accidentellement les gaz alors que les moteurs sont armés */
//#define MOTOR_STOP
/* Certaines radios ne retournent pas 1500 au centre des sticks, vous pouvez modifier ici la valeur */
#define MIDRC 1500

/********************************************************************/
/****           ESCs calibration                                 ****/
/********************************************************************/

/* Pour calibrer les ESC
   Pour plus d'informations sur la méthode avec la radio et avec cette fonction, voir le post suivant
   http://www.rcnet.com/post17521.html#p17521
   Attention, vous ne pouvez pas voler ainsi, vous devrez flasher de nouveau la carte en ayant décommenté cette fonction */
#define ESC_CALIB_LOW  MINCOMMAND
#define ESC_CALIB_HIGH 2000
//#define ESC_CALIB_CANNOT_FLY  // Décommentez pour activer le mode calibrage des ESC

/****           internal frequencies                             ****/
/* frequenies for rare cyclic actions in the main loop, depend on cycle time
 time base is main loop cycle time - a value of 6 means to trigger the action every 6th run through the main loop
 example: with cycle time of approx 3ms, do action every 6*3ms=18ms
 value must be [1; 65535] */
#define LCD_TELEMETRY_FREQ 23       // to send telemetry data over serial 23 <=> 60ms <=> 16Hz (only sending interlaced, so 8Hz update rate)
#define LCD_TELEMETRY_AUTO_FREQ 967 // to step to next telemetry page 967 <=> 3s
#define PSENSORFREQ 6               // to read hardware powermeter sensor 6 <=> 18ms
#define VBATFREQ PSENSORFREQ        // to read battery voltage - keep equal to PSENSORFREQ unless you know what you are doing

/***********************         Servo Refreshrates            ***********************/
/* Default 50Hz Servo refresh rate*/
#define SERVO_RFR_50HZ
/* up to 160Hz servo refreshrate .. works with the most analog servos*/
//#define SERVO_RFR_160HZ
/* up to 300Hz refreshrate it is as fast as possible (100-300Hz depending on the cound of used servos and the servos state).
 for use with digital servos
 dont use it with analog servos! thay may get damage. (some will work but be careful) */
//#define SERVO_RFR_300HZ

/***********************             HW PWM Servos             ***********************/
/* Ne pas toucher svp, inutile pour la FC RCNet qui est a base de 2560, laissez décommenté.
 HW PWM Servo outputs for Arduino Mega.. moves:
 Pitch   = pin 44
 Roll    = pin 45
 CamTrig = pin 46
 SERVO4  = pin 11 (assigned to PPM or SPECTRUM CH9 on copter configuration)
 SERVO5  = pin 12 (assigned to PPM or SPECTRUM CH10 on copter configuration)
 this option disable other software PWM's for servos - only five hardware controlled servos avaliable
 */
#define MEGA_HW_PWM_SERVOS // Il est conseillé de ne pas désactiver

/********************************************************************/
/****           Serial command handling - MSP and other          ****/
/********************************************************************/

/* to reduce memory footprint, it is possible to suppress handling of serial commands.
 * This does _not_ affect handling of RXserial, Spektrum or GPS. Those will not be affected and still work the same.
 * Enable either one or both of the following options  */

/* Remove handling of all commands of the New MultiWii Serial Protocol.
 * This will disable use of the GUI, winGUI, android apps and any other program that makes use of the MSP.
 * You must find another way (like LCD_CONF) to tune the parameters or live with the defaults.
 * If you run a LCD/OLED via i2c or serial/Bluetooth, this is safe to use */
//#define SUPPRESS_ALL_SERIAL_MSP // saves approx 2700 bytes
/* Remove handling of other serial commands.
 * This includes navigating via serial the lcd.configuration menu, lcd.telemetry and permanent.log .
 * Navigating via stick inputs on tx is not affected and will work the same.  */
//#define SUPPRESS_OTHER_SERIAL_COMMANDS // saves  approx 0 to 100 bytes, depending on features enabled

/********************************************************************/
/****           diagnostics   (developpers)                      ****/
/********************************************************************/

/* to log values like max loop time and others to come
 logging values are visible via LCD config
 set to 1, enable 'R' option to reset values, max current, max altitude
 set to 2, adds min/max cycleTimes
 set to 3, adds additional powerconsumption on a per motor basis (this uses the big array and is a memory hog, if POWERMETER <> PM_SOFT) */
//#define LOG_VALUES 1
/* Permanent logging to eeprom - survives (most) upgrades and parameter resets.
 * used to track number of flights etc. over lifetime of controller board.
 * Writes to end of eeprom - should not conflict with stored parameters yet.
 * Logged values: accumulated lifetime, #powercycle/reset/initialize events, #arm events, #disarm events, last armedTime,
 *                #failsafe@disarm, #i2c_errs@disarm
 * To activate set to size of eeprom for your mcu: promini 328p: 1023 ; 2560: 4095.
 * Enable one or more options to show the log
 */
#define LOG_PERMANENT 4095
//#define LOG_PERMANENT_SHOW_AT_STARTUP // enable to display log at startup
//#define LOG_PERMANENT_SHOW_AT_L // enable to display log when receiving 'L'
//#define LOG_PERMANENT_SHOW_AFTER_CONFIG // enable to display log after exiting LCD config menu
//#define LOG_PERMANENT_SERVICE_LIFETIME 36000 // in seconds; service alert at startup after 10 hours of armed time

/* Logging to SDCARD module
 * More information at www.rcnet.com
 */
#define MWI_SDCARD	            // Activation des fonctions utilisant le module SD, inutile de désactiver si le module est absent.
#define SDCARD_MISSION		    // Activation du modem mission
#define SDCARD_LOGGER		    // Activation du permlog et données GPS (si activé ci-après) sur le carte SD
#define LOG_PERMANENT_SD_ONLY   // Désactiver le permlog dans l'eeprom
#define LOG_GPS_POSITION 2	    // Ecrire les données GPS sur la SD, le paramètre permets de choisir le nombre de secondes entres deux ecritures
#define CSPIN 53  				// Utilisation du PIN 53, ne pas toucher SVP
/* to add debugging code
 not needed and not recommended for normal operation
 will add extra code that may slow down the main loop or make copter non-flyable */
//#define DEBUG
/* Use this to trigger LCD configuration without a TX - only for debugging - do NOT fly with this activated */
//#define LCD_CONF_DEBUG
/* Use this to trigger telemetry without a TX - only for debugging - do NOT fly with this activated */
//#define LCD_TELEMETRY_DEBUG    //This form rolls between all screens, LCD_TELEMETRY_AUTO must also be defined.
//#define LCD_TELEMETRY_DEBUG 6  //This form stays on the screen specified.
/* Enable string transmissions from copter to GUI */
#define DEBUGMSG

/********************************************************************/
/****           Regression testing                               ****/
/********************************************************************/

/* for development only:
 to allow for easier and reproducable config sets for test compiling, different sets of config parameters are kept
 together. This is meant to help detecting compile time errors for various features in a coordinated way.
 It is not meant to produce your flying firmware
 To use:
 - do not set any options in config.h,
 - enable with #define COPTERTEST 1, then compile
 - if possible, check for the size
 - repeat with other values of 2, 3, 4 etc.
 */
//#define COPTERTEST 1

#endif /* CONFIG_H_ */
