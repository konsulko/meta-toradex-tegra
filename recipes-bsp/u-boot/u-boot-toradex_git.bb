require recipes-bsp/u-boot/u-boot.inc

PROVIDES = "u-boot"
DEPENDS += "dtc-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=c7383a594871c03da76b3707929d2919"

COMPATIBLE_MACHINE = "(apalis-t30|colibri-t20|colibri-t30)"

DEFAULT_PREFERENCE_apalis-t30 = "1"
DEFAULT_PREFERENCE_colibri-t20 = "1"
DEFAULT_PREFERENCE_colibri-t30 = "1"

FILESPATHPKG =. "git:"
S="${WORKDIR}/git"
SRC_URI =  "git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=2015.04-toradex-next"
# This revision is based on upstream "v2015.04"
SRCREV = "98ec78b1f28627e6d09972e2b1237c2ef8f161d9"

PV = "${PR}+gitr${SRCREV}"

# override the solution passed in from u-boot.inc as we want to set additional flags
EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

# require u-boot-dtb-tegra.bin to be used
UBOOT_IMAGE = "u-boot-dtb-tegra-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY = "u-boot-dtb-tegra.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "u-boot-dtb-tegra-${MACHINE}.${UBOOT_SUFFIX}"

#do_install_append() {
#}

PACKAGE_ARCH = "${MACHINE_ARCH}"
