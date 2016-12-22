require recipes-bsp/u-boot/u-boot.inc

PROVIDES = "u-boot virtual/bootloader"
DEPENDS += "dtc-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

COMPATIBLE_MACHINE = "(apalis-t30|apalis-tk1|colibri-t20|colibri-t30)"

DEFAULT_PREFERENCE_apalis-t30 = "1"
DEFAULT_PREFERENCE_apalis-tk1 = "1"
DEFAULT_PREFERENCE_colibri-t20 = "1"
DEFAULT_PREFERENCE_colibri-t30 = "1"

FILESPATHPKG =. "git:"
S="${WORKDIR}/git"
# This revision is based on upstream "v2016.11"
SRCREV = "2cb59d1289cb7994c98f9c91f94ed7bd7b279d93"
SRCBRANCH = "2016.11-toradex-next"
SRC_URI = "git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=${SRCBRANCH}"

PV = "v2016.11-v2.7b1+git${SRCPV}"

# require u-boot-dtb-tegra.bin to be used
UBOOT_IMAGE = "u-boot-dtb-tegra-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY = "u-boot-dtb-tegra.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "u-boot-dtb-tegra-${MACHINE}.${UBOOT_SUFFIX}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
