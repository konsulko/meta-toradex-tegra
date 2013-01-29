inherit kernel
require recipes-kernel/linux/linux.inc

LINUX_VERSION ?= "3.1.10"

SRCREV_colibri-t20 = "fa2371bff9ac03581881849d8f95678ef3992719"
PR_colibri-t20 = "V2.0"
SRCREV_colibri-t30 = "fa2371bff9ac03581881849d8f95678ef3992719"
PR_colibri-t30 = "V2.0a2"
SRCREV_apalis-t30 = "fa2371bff9ac03581881849d8f95678ef3992719"
PR_apalis-t30 = "V2.0a0"
SRCREV_colibri-pxa = "fa2371bff9ac03581881849d8f95678ef3992719"
PR_colibri-pxa = "V2.0a0"

PV = "${LINUX_VERSION}+gitr${SRCREV}"
S = "${WORKDIR}/git"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=colibri"

COMPATIBLE_MACHINE_colibri-t20 = "colibri-t20"
COMPATIBLE_MACHINE_colibri-t30 = "colibri-t30"
COMPATIBLE_MACHINE_apalis-t30 = "apalis-t30"
COMPATIBLE_MACHINE_colibri-pxa = "colibri-pxa"

do_configure_prepend () {
    #use the defconfig provided in the kernel source tree
    #assume its called ${MACHINE}_defconfig, but with '_' instead of '-'
    DEFCONFIG=`echo ${MACHINE} | sed -e 's/\-/\_/g' -e 's/$/_defconfig/'`
    oe_runmake $DEFCONFIG
}
