EESchema Schematic File Version 2
LIBS:power
LIBS:device
LIBS:transistors
LIBS:conn
LIBS:linear
LIBS:regul
LIBS:74xx
LIBS:cmos4000
LIBS:adc-dac
LIBS:memory
LIBS:xilinx
LIBS:microcontrollers
LIBS:dsp
LIBS:microchip
LIBS:analog_switches
LIBS:motorola
LIBS:texas
LIBS:intel
LIBS:audio
LIBS:interface
LIBS:digital-audio
LIBS:philips
LIBS:display
LIBS:cypress
LIBS:siliconi
LIBS:opto
LIBS:atmel
LIBS:contrib
LIBS:valves
LIBS:adapter_board-cache
EELAYER 25 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
NoConn ~ 2600 2650
$Comp
L CONN_01X08 P1
U 1 1 583CB1C7
P 2600 2500
F 0 "P1" H 2600 2950 50  0000 C CNN
F 1 "WEMO_1" V 2700 2500 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Straight_1x08" H 2600 2500 50  0001 C CNN
F 3 "" H 2600 2500 50  0000 C CNN
	1    2600 2500
	1    0    0    -1  
$EndComp
$Comp
L CONN_01X08 P3
U 1 1 583CB1DE
P 3500 2500
F 0 "P3" H 3500 2950 50  0000 C CNN
F 1 "WEMO_2" V 3600 2500 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Straight_1x08" H 3500 2500 50  0001 C CNN
F 3 "" H 3500 2500 50  0000 C CNN
	1    3500 2500
	-1   0    0    -1  
$EndComp
Wire Wire Line
	1850 2150 2400 2150
Wire Wire Line
	1850 2250 2400 2250
Wire Wire Line
	1850 2350 2400 2350
Wire Wire Line
	1850 2450 2400 2450
Wire Wire Line
	1850 2550 2400 2550
Wire Wire Line
	1850 2650 2400 2650
Wire Wire Line
	1850 2750 2400 2750
Wire Wire Line
	1850 2850 2400 2850
Wire Wire Line
	3700 2150 4400 2150
Wire Wire Line
	3700 2250 4400 2250
Wire Wire Line
	3700 2350 4600 2350
Wire Wire Line
	3700 2550 4800 2550
Wire Wire Line
	3700 2650 4700 2650
Wire Wire Line
	3700 2750 4500 2750
Wire Wire Line
	3700 2850 4400 2850
Text Label 1900 2150 0    60   ~ 0
ESP_RST
Text Label 1900 2250 0    60   ~ 0
ESP_A0
Text Label 1900 2350 0    60   ~ 0
ESP_D0
Text Label 1900 2450 0    60   ~ 0
ESP_D5
Text Label 1900 2550 0    60   ~ 0
ESP_D6
Text Label 1900 2650 0    60   ~ 0
ESP_D7
Text Label 1900 2750 0    60   ~ 0
ESP_D8
Text Label 1900 2850 0    60   ~ 0
3.3V
Text Label 3800 2150 0    60   ~ 0
ESP_TX
Text Label 3800 2250 0    60   ~ 0
ESP_RX
Text Label 3800 2350 0    60   ~ 0
ESP_SCL
Text Label 3800 2450 0    60   ~ 0
ESP_SDA
Text Label 3800 2550 0    60   ~ 0
ESP_D3
Text Label 3800 2650 0    60   ~ 0
ESP_D4
Text Label 3800 2750 0    60   ~ 0
GND
Text Label 3800 2850 0    60   ~ 0
5V
$Comp
L CONN_01X06 P2
U 1 1 583CB544
P 3100 3800
F 0 "P2" H 3100 4150 50  0000 C CNN
F 1 "VL53L0X" V 3200 3800 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Straight_1x06" H 3100 3800 50  0001 C CNN
F 3 "" H 3100 3800 50  0000 C CNN
	1    3100 3800
	-1   0    0    -1  
$EndComp
Wire Wire Line
	3300 3550 3950 3550
Wire Wire Line
	3300 3650 4500 3650
Wire Wire Line
	3300 3750 4600 3750
Wire Wire Line
	3300 3950 4700 3950
Wire Wire Line
	3300 4050 4800 4050
Text Label 3450 3550 0    60   ~ 0
VL_VIN
Text Label 3450 3650 0    60   ~ 0
VL_GND
Text Label 3450 3750 0    60   ~ 0
VL_SCL
Text Label 3450 3850 0    60   ~ 0
VL_SDA
Text Label 3450 3950 0    60   ~ 0
VL_GPIO1
Text Label 3450 4050 0    60   ~ 0
VL_SHUTDOWN
Wire Wire Line
	3950 3550 3950 3250
Wire Wire Line
	3950 3250 1850 3250
Wire Wire Line
	1850 3250 1850 2850
Wire Wire Line
	4600 3750 4600 2350
Wire Wire Line
	4500 3650 4500 2750
Wire Wire Line
	4800 4050 4800 2550
Wire Wire Line
	4700 3950 4700 2650
Wire Wire Line
	3300 3850 4900 3850
Wire Wire Line
	4900 3850 4900 2450
Wire Wire Line
	4900 2450 3700 2450
NoConn ~ 1850 2150
NoConn ~ 1850 2250
NoConn ~ 1850 2350
NoConn ~ 1850 2450
NoConn ~ 1850 2550
NoConn ~ 1850 2650
NoConn ~ 1850 2750
NoConn ~ 4400 2150
NoConn ~ 4400 2250
NoConn ~ 4400 2850
$EndSCHEMATC
