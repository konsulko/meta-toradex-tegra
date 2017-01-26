inherit kernel
require recipes-kernel/linux/linux-toradex.inc

LINUX_VERSION ?= "3.1.10"

LOCALVERSION = "-${PR}"
SRCREV = "b496d097ab8741678ae3027b8e5358a04fb5ea82"
PR = "v2.7b1"

PV = "${LINUX_VERSION}+gitr${SRCREV}"
S = "${WORKDIR}/git"
SRCBRANCH = "tegra"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH}"

COMPATIBLE_MACHINE = "(apalis-t30|colibri-pxa|colibri-t20|colibri-t30)"

# One possibiltiy for changes to the defconfig:
config_script () {
#    #example change to the .config
#    #sets CONFIG_TEGRA_CAMERA unconditionally to 'y'
#    sed -i -e /CONFIG_TEGRA_CAMERA/d ${B}/.config
#    echo "CONFIG_TEGRA_CAMERA=y" >> ${B}/.config
    echo "dummy" > /dev/null
}

do_configure_prepend () {
    #use the defconfig provided in the kernel source tree
    #assume its called ${MACHINE}_defconfig, but with '_' instead of '-'
    DEFCONFIG="`echo ${MACHINE} | sed -e 's/\-/\_/g' -e 's/$/_defconfig/'`"

    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake $DEFCONFIG

    #maybe change some configuration
    config_script

    #Add Toradex BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    #Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    printf "%s%s" +g $head > ${S}/.scmversion

    cd - > /dev/null
}

do_uboot_mkimage_prepend () {
    cd ${B}
}
