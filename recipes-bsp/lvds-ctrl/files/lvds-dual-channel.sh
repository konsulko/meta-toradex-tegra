#!/bin/sh
#enable the parallel RGB to LVDS controller in dual channel mode

echo out > /sys/class/gpio/gpio216/direction
echo 0 > /sys/class/gpio/gpio216/value
echo out > /sys/class/gpio/gpio219/direction
echo 1 > /sys/class/gpio/gpio219/value
echo out > /sys/class/gpio/gpio220/direction
echo 1 > /sys/class/gpio/gpio220/value
echo out > /sys/class/gpio/gpio221/direction
echo 1 > /sys/class/gpio/gpio221/value
echo out > /sys/class/gpio/gpio222/direction
echo 1 > /sys/class/gpio/gpio222/value
echo out > /sys/class/gpio/gpio223/direction
echo 1 > /sys/class/gpio/gpio223/value
echo out > /sys/class/gpio/gpio225/direction
echo 1 > /sys/class/gpio/gpio225/value
echo out > /sys/class/gpio/gpio226/direction
echo 1 > /sys/class/gpio/gpio226/value
