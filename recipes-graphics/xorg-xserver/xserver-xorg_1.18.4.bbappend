# always latest and greatest.
PE_tegra124m = "99"

FILESEXTRAPATHS_prepend := "${THISDIR}/xserver-xorg-${PV}:"

SRC_URI_append_tegra124m = "\
            file://0001-HACK-use-render-nodes-and-tegra-tiling-format.patch \
            file://0002-HACK-enable-GLX-with-DRI3.patch \
            "
PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE_tegra124m = "(apalis-tk1-mainline)"

# provided by xf86-input-evdev_2.10.0
do_install_append () {
    rm -f ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}

PACKAGECONFIG_tegra124m ?= "dri2 dri3 xshmfence glamor xwayland udev ${XORG_CRYPTO} "

