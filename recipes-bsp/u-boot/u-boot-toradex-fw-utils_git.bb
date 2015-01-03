DESCRIPTION = "U-boot bootloader fw_printenv/setenv utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM_colibri-t20 = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb \
                    file://README;beginline=1;endline=22;md5=5ba4218ac89af7846802d0348df3fb90"
LIC_FILES_CHKSUM_tegra3 = "file://Licenses/README;md5=c7383a594871c03da76b3707929d2919"
SECTION = "bootloader"
PROVIDES = "u-boot-fw-utils"
DEPENDS = "mtd-utils"

COMPATIBLE_MACHINE = "(colibri-t20|colibri-t30|apalis-t30)"

DEFAULT_PREFERENCE_colibri-t20 = "1"
DEFAULT_PREFERENCE_colibri-t30 = "1"
DEFAULT_PREFERENCE_apalis-t30 = "1"

FILESPATHPKG =. "git:"
S="${WORKDIR}/git"
SRC_URI_T20 =  "git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=colibri"
SRC_URI_T20 += "file://fw_env.config"
SRC_URI_T30 =  "git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=2014.10-toradex"
SRC_URI_T30 += "file://fw_env.config"
# This revision is based on upstream "v2011.06"
SRCREV_T20 = "278ca22a59e3df4dd1e1494e8920a01c172118af"
# This revision is based on upstream "v2014.10"
SRCREV_T30 = "f7e57f71da1eecbf1ac28339868d15492c929925"

PV_colibri-t20 = "${PR}+gitr${SRCREV}"
PV_colibri-t30 = "${PR}+gitr${SRCREV}"
PV_apalis-t30 = "${PR}+gitr${SRCREV}"

SRC_URI_colibri-t20 = "${SRC_URI_T20}"
SRC_URI_colibri-t30 = "${SRC_URI_T30}"
SRC_URI_apalis-t30 = "${SRC_URI_T30}"

SRCREV_colibri-t20 = "${SRCREV_T20}"
SRCREV_colibri-t30 = "${SRCREV_T30}"
SRCREV_apalis-t30 = "${SRCREV_T30}"

S = "${WORKDIR}/git"

#actually this depend on the upstream U-Boot version and not on the machine
CC_remove_tegra3 = "-mfpu=neon"
EXTRA_OEMAKE_tegra3 = 'CC="${CC}"'
EXTRA_OEMAKE_tegra2 = 'HOSTCC="${CC}" HOSTSTRIP="true"'

INSANE_SKIP_${PN} = "already-stripped"

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

pkg_postinst_${PN}_colibri-t20 () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    grep ENV /proc/mtd | awk '{print "/dev/" substr($1,0,4) " 0x00000000 0x00001000 0x" $3 " 1" >> "/etc/fw_env.config" }'
}

pkg_postinst_${PN}_tegra3 () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    # Environment in eMMC, at the end of 2nd "boot sector"
    DISK="mmcblk0boot1"
    DISK_SIZE=`cat /sys/block/$DISK/size`
    CONFIG_ENV_SIZE=8192 # 0x2000
    CONFIG_ENV_OFFSET=`expr $DISK_SIZE \* 512 - $CONFIG_ENV_SIZE`
    printf "/dev/%s\t0x%X\t0x%X\n" $DISK $CONFIG_ENV_OFFSET $CONFIG_ENV_SIZE >> "/etc/fw_env.config"
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
