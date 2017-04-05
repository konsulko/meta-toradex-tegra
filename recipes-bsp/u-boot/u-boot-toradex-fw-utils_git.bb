SUMMARY = "U-boot bootloader fw_printenv/setenv utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
SECTION = "bootloader"
PROVIDES = "u-boot-fw-utils"
DEPENDS = "mtd-utils"

include conf/tdx_version.conf
inherit tegra-u-boot-localversion

COMPATIBLE_MACHINE = "(apalis-t30|apalis-tk1|colibri-t20|colibri-t30)"

DEFAULT_PREFERENCE_apalis-t30 = "1"
DEFAULT_PREFERENCE_apalis-tk1 = "1"
DEFAULT_PREFERENCE_colibri-t20 = "1"
DEFAULT_PREFERENCE_colibri-t30 = "1"

FILESPATHPKG =. "git:"

# This revision is based on upstream "v2016.11"
SRCREV = "60021a4daa9720ae89e31def9483a09a78ead049"
SRCBRANCH = "2016.11-toradex"
SRC_URI = " \
    git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=${SRCBRANCH} \
    file://fw_env.config \
"
SRC_URI_append_tegra3 = " file://fw_unlock_mmc.sh"
SRC_URI_append_tegra124 = " file://fw_unlock_mmc.sh"

PV = "2016.11"
PR = "${TDX_VER_INT}-gitr${@d.getVar("SRCREV", False)[0:7]}"

S = "${WORKDIR}/git"

#actually this depend on the upstream U-Boot version and not on the machine
CC_remove = "-mfpu=neon"
EXTRA_OEMAKE = 'CC="${CC}"'

INSANE_SKIP_${PN} = "already-stripped ldflags"

inherit pkgconfig uboot-config

do_compile () {
    oe_runmake ${UBOOT_MACHINE}
    oe_runmake env
}

do_install () {
    install -d ${D}${base_sbindir} ${D}${sysconfdir}
    install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
    ln -s fw_printenv ${D}${base_sbindir}/fw_setenv
    install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/
}

do_install_append_tegra3() {
    install -d ${D}${sysconfdir}/profile.d/
    install -m 0644 ${WORKDIR}/fw_unlock_mmc.sh ${D}${sysconfdir}/profile.d/fw_unlock_mmc.sh
}

do_install_append_tegra124() {
    install -d ${D}${sysconfdir}/profile.d/
    install -m 0644 ${WORKDIR}/fw_unlock_mmc.sh ${D}${sysconfdir}/profile.d/fw_unlock_mmc.sh
}

pkg_postinst_${PN}_colibri-t20 () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    grep u-boot-env /proc/mtd | awk '{print "/dev/" substr($1,0,4) " 0x00000000 0x00010000 0x" $3 " 1" >> "/etc/fw_env.config" }'
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
