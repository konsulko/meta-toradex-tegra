SUMMARY = "Linux Kernel for Toradex Apalis Tegra based modules"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo
require recipes-kernel/linux/linux-dtb.inc

LINUX_VERSION ?= "4.9"

LOCALVERSION = "-${PR}"
PR = "V2.6.2b1"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"
SRC_URI[md5sum] = "0a68ef3615c64bd5ee54a3320e46667d"
SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
           file://0001-toradex_apalis_tk1_t30-customize-defconfig.patch \
           file://0001-apalis-tk1-remove-spurious-new-lines.patch \
           file://0002-apalis-tk1-temp-alert-pull-up.patch \
           file://0003-apalis-tk1-optional-displayport-hot-plug-detect.patch \
           file://0004-apalis-tk1-adjust-pin-muxing-for-v1.1-hw.patch \
           file://0005-apalis-tk1-working-sd-card-detect-on-v1.1-hw.patch \
           file://0006-apalis-tk1-update-compatibility-comment.patch\
           file://0001-apalis_t30-tk1-fix-pcie-clock-and-reset-not-conformi.patch \
           file://0002-igb-integrate-tools-only-device-support.patch \
           file://0003-apalis_t30-tk1-igb-no-nvm-and-Ethernet-MAC-address-h.patch \
           file://0004-mmc-tegra-apalis-tk1-hack-to-make-sd1-functional.patch \
           "

COMPATIBLE_MACHINE = "(apalis-tk1-mainline|apalis-t30-mainline)"
KERNEL_EXTRA_ARGS = " LOADADDR=0x80008000 "

# One possibiltiy for changes to the defconfig:
config_script () {
    echo "dummy" > /dev/null
}

do_configure_prepend () {
    pushd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake ${KERNEL_DEFCONFIG}

    #maybe change some configuration
    config_script

    #Add Toradex BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    popd
}

do_uboot_mkimage_prepend() {
    cd ${B}
}

