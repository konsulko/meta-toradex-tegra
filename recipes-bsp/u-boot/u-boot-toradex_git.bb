require recipes-bsp/u-boot/u-boot.inc

PROVIDES = "u-boot"
DEPENDS += "dtc-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb \
                    file://README;beginline=1;endline=22;md5=5ba4218ac89af7846802d0348df3fb90"

COMPATIBLE_MACHINE_colibri-t20 = "colibri-t20"
COMPATIBLE_MACHINE_colibri-t30 = "colibri-t30"
COMPATIBLE_MACHINE_apalis-t30 = "apalis-t30"

DEFAULT_PREFERENCE_colibri-t20 = "1"
DEFAULT_PREFERENCE_colibri-t30 = "1"
DEFAULT_PREFERENCE_apalis-t30 = "1"

FILESPATHPKG =. "git:"
S="${WORKDIR}/git"
SRC_URI_COLIBRI =  "git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=colibri"
SRC_URI_COLIBRI += "file://u-boot-dont-build-standalone.patch"
# This revision is based on upstream "v2011.06"
SRCREV_COLIBRI = "ccd521c4681a5850bbec1feec140eeaec33936b0"

PV_colibri-t20 = "${PR}+gitr${SRCREV}"
PV_colibri-t30 = "${PR}+gitr${SRCREV}"
PV_apalis-t30 = "${PR}+gitr${SRCREV}"

SRC_URI_colibri-t20 = "${SRC_URI_COLIBRI}"
SRC_URI_colibri-t30 = "${SRC_URI_COLIBRI}"
SRC_URI_apalis-t30 = "${SRC_URI_COLIBRI}"

SRCREV_colibri-t20 = "${SRCREV_COLIBRI}"
SRCREV_colibri-t30 = "${SRCREV_COLIBRI}"
SRCREV_apalis-t30 = "${SRCREV_COLIBRI}"

# override the solution passed in from u-boot.inc as we want to set additional flags
EXTRA_OEMAKE_colibri-t20 = "CROSS_COMPILE=${TARGET_PREFIX}"
EXTRA_OEMAKE_colibri-t30 = "CROSS_COMPILE=${TARGET_PREFIX}"
EXTRA_OEMAKE_apalis-t30 = "CROSS_COMPILE=${TARGET_PREFIX}"

# colibri-t20: build additionally a u-boot binary which uses/stores its environment on an T20 external sd or mmc card
SPL_BINARY_colibri-t20  = "u-boot-hsmmc.bin"
SPL_IMAGE_colibri-t20   = "u-boot-hsmmc-${MACHINE}-${PV}-${PR}.bin"
SPL_SYMLINK_colibri-t20 = "u-boot-hsmmc-${MACHINE}.bin"
do_compile_append_colibri-t20() {
    # keep u-boot-nand
    mv u-boot.bin u-boot-nand.bin
    oe_runmake colibri_t20_sdboot_config
    oe_runmake ${UBOOT_MAKE_TARGET}
    mv u-boot.bin u-boot-hsmmc.bin
    mv u-boot-nand.bin u-boot.bin
}

#do_install_append() {
#}

PACKAGE_ARCH = "${MACHINE_ARCH}"
