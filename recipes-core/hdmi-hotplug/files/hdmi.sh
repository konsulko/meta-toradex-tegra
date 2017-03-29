#!/bin/sh

export XAUTHORITY=/home/root/.Xauthority
export DISPLAY=:0.0
DISPLAY=:0.0 xrandr --output HDMI-1 --auto
lxpanelctl restart
