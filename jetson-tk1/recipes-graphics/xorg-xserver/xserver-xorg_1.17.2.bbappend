COMPATIBLE_MACHINE = "(apalis-tk1|jetson-tk1|jetson-tk1-l4t)"

# provided by xf86-input-evdev_2.10.0
do_install_append () {
    rm -f ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}

