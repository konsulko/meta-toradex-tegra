DESCRIPTION = "U-boot bootloader fw_printenv/setenv utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb \
                    file://README;beginline=1;endline=22;md5=5ba4218ac89af7846802d0348df3fb90"
SECTION = "bootloader"
PROVIDES = "u-boot-fw-utils"
DEPENDS = "mtd-utils"

COMPATIBLE_MACHINE = "(colibri-t20|colibri-t30|apalis-t30)"

DEFAULT_PREFERENCE_colibri-t20 = "1"
DEFAULT_PREFERENCE_colibri-t30 = "1"
DEFAULT_PREFERENCE_apalis-t30 = "1"

FILESPATHPKG =. "git:"
S="${WORKDIR}/git"
SRC_URI_COLIBRI =  "git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=colibri"
SRC_URI_COLIBRI += "file://u-boot-dont-build-standalone.patch"
SRC_URI_COLIBRI += "file://fw_env.config"
# This revision is based on upstream "v2011.06"
SRCREV_COLIBRI = "278ca22a59e3df4dd1e1494e8920a01c172118af"

PV_colibri-t20 = "${PR}+gitr${SRCREV}"
PV_colibri-t30 = "${PR}+gitr${SRCREV}"
PV_apalis-t30 = "${PR}+gitr${SRCREV}"

SRC_URI_colibri-t20 = "${SRC_URI_COLIBRI}"
SRC_URI_colibri-t30 = "${SRC_URI_COLIBRI}"
SRC_URI_apalis-t30 = "${SRC_URI_COLIBRI}"

SRCREV_colibri-t20 = "${SRCREV_COLIBRI}"
SRCREV_colibri-t30 = "${SRCREV_COLIBRI}"
SRCREV_apalis-t30 = "${SRCREV_COLIBRI}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'HOSTCC="${CC}" HOSTSTRIP="true"'

inherit uboot-config

do_compile () {
    oe_runmake ${UBOOT_MACHINE}
    oe_runmake env
}

do_install () {
    install -d ${D}${base_sbindir} ${D}${sysconfdir}
    install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
    install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_setenv
    install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/
}

pkg_postinst_${PN}_tegra2 () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    grep ENV /proc/mtd | awk '{print "/dev/" substr($1,0,4) " 0x00000000 0x00001000 0x" $3 " 1" >> "/etc/fw_env.config" }'
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
