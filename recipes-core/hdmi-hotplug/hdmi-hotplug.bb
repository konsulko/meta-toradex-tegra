SECTION = "core"
SUMMARY = "Use xrandr after a HDMI hotplug event to switch the display on"
# The license is meant for this recipe and the files it installs.
# RNDIS is part of the kernel, udhcpd is part of busybox
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

PR = "r1"

PACKAGE_ARCH = "all"

SRC_URI = " \
    file://hdmi.rules \
    file://hdmi.sh \
"

do_install() {
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/hdmi.rules ${D}/${sysconfdir}/udev/rules.d/

    install -d ${D}/${sysconfdir}/udev/scripts
    install -m 0755 ${WORKDIR}/hdmi.sh ${D}/${sysconfdir}/udev/scripts/
}
