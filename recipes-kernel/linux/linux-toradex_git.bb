inherit kernel
require recipes-kernel/linux/linux-toradex.inc

LINUX_VERSION ?= "3.1.10"

SRCREV_colibri-t20 = "38802c79a2ea9b0c69b937db94fe656a64581262"
PR_colibri-t20 = "V2.3b5"
SRCREV_colibri-t30 = "38802c79a2ea9b0c69b937db94fe656a64581262"
PR_colibri-t30 = "V2.3b5"
SRCREV_apalis-t30 = "38802c79a2ea9b0c69b937db94fe656a64581262"
PR_apalis-t30 = "V2.3b5"
SRCREV_colibri-pxa = "38802c79a2ea9b0c69b937db94fe656a64581262"
PR_colibri-pxa = "V2.3a1"

PV = "${LINUX_VERSION}+gitr${SRCREV}"
S = "${WORKDIR}/git"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=tegra-next"


COMPATIBLE_MACHINE = "(colibri-t20|colibri-t30|apalis-t30|colibri-pxa)"

# One possibiltiy for changes to the defconfig:
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
    DEFCONFIG="`echo ${MACHINE} | sed -e 's/\-/\_/g' -e 's/$/_defconfig/'`"

    oe_runmake $DEFCONFIG

    #maybe change some configuration
    config_script 
}

kernel_do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        export CC="`echo "${KERNEL_CC}" | sed 's/-mfloat-abi=hard//g'`"
	oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} LD="${KERNEL_LD}"
	if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
		gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
	fi
}

do_compile_kernelmodules() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        export CC="`echo "${KERNEL_CC}" | sed 's/-mfloat-abi=hard//g'`"
	if (grep -q -i -e '^CONFIG_MODULES=y$' .config); then
		oe_runmake ${PARALLEL_MAKE} modules LD="${KERNEL_LD}"
	else
		bbnote "no modules to compile"
	fi
}
