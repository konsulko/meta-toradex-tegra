# always latest and greatest.
PE_tegra124m = "99"

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE_tegra124m = "(apalis-tk1-mainline)"

# provided by xf86-input-evdev_2.10.0
do_install_append () {
    rm -f ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}

PACKAGECONFIG_tegra124m ?= "dri2 dri3 xshmfence glamor xwayland udev ${XORG_CRYPTO} "

