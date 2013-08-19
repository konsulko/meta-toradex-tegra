inherit kernel
require recipes-kernel/linux/linux.inc

LINUX_VERSION ?= "3.1.10"

SRCREV_colibri-t20 = "fa2371bff9ac03581881849d8f95678ef3992719"
PR_colibri-t20 = "V2.0"
SRCREV_colibri-t30 = "304fae6e8872c4b9d694900c5cef91abdbc81c7e"
PR_colibri-t30 = "V2.0b3"
SRCREV_apalis-t30 = "304fae6e8872c4b9d694900c5cef91abdbc81c7e"
PR_apalis-t30 = "V2.0b2"
SRCREV_colibri-pxa = "61a50f833622ce901054b32c332c4e168ced4c98"
PR_colibri-pxa = "V2.0a1"

PV = "${LINUX_VERSION}+gitr${SRCREV}"
S = "${WORKDIR}/git"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=colibri"
# Patch to set parallel RGB (aka LVDS-1) to full-hd
#SRC_URI += "file://full-hd.patch "


COMPATIBLE_MACHINE_colibri-t20 = "colibri-t20"
COMPATIBLE_MACHINE_colibri-t30 = "colibri-t30"
COMPATIBLE_MACHINE_apalis-t30 = "apalis-t30"
COMPATIBLE_MACHINE_colibri-pxa = "colibri-pxa"

# Place changes to the defconfig here
config_script () {
#    #example change to the .config
#    #sets CONFIG_TEGRA_CAMERA unconditionally to 'y'
#    sed -i -e /CONFIG_TEGRA_CAMERA/d ${S}/.config
#    echo "CONFIG_TEGRA_CAMERA=y" >> ${S}/.config
    echo "dummy" > /dev/null
}

do_configure_prepend () {
    #use the defconfig provided in the kernel source tree
    #assume its called ${MACHINE}_defconfig, but with '_' instead of '-'
    DEFCONFIG=`echo ${MACHINE} | sed -e 's/\-/\_/g' -e 's/$/_defconfig/'`

    oe_runmake $DEFCONFIG

    #maybe change some configuration
    config_script 
}
