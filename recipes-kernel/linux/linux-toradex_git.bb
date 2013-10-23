inherit kernel
require recipes-kernel/linux/linux.inc

LINUX_VERSION ?= "3.1.10"

SRCREV_colibri-t20 = "d831d2d2a2459419ab1b9eeec55d816c72920556"
PR_colibri-t20 = "V2.1b1"
SRCREV_colibri-t30 = "d831d2d2a2459419ab1b9eeec55d816c72920556"
PR_colibri-t30 = "V2.1b1"
SRCREV_apalis-t30 = "d831d2d2a2459419ab1b9eeec55d816c72920556"
PR_apalis-t30 = "V2.1b1"
SRCREV_colibri-pxa = "d831d2d2a2459419ab1b9eeec55d816c72920556"
PR_colibri-pxa = "V2.1a1"

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

kernel_do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        export CC=`echo "${KERNEL_CC}" | sed 's/-mfloat-abi=hard//g'`
	oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} LD="${KERNEL_LD}"
	if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
		gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
	fi
}

do_compile_kernelmodules() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        export CC=`echo "${KERNEL_CC}" | sed 's/-mfloat-abi=hard//g'`
	if (grep -q -i -e '^CONFIG_MODULES=y$' .config); then
		oe_runmake ${PARALLEL_MAKE} modules LD="${KERNEL_LD}"
	else
		bbnote "no modules to compile"
	fi
}
