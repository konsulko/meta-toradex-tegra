SUMMARY = "Linux Kernel for Toradex Apalis Tegra based modules"
SECTION = "kernel"
LICENSE = "GPLv2"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-toradex-mainline-4.9:"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo
require recipes-kernel/linux/linux-dtb.inc
include conf/tdx_version.conf

LINUX_VERSION ?= "4.9.33"

LOCALVERSION = "-${PR}"
PR = "${TDX_VER_INT}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"
SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
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
    file://0001-drm-tegra-add-tiling-FB-modifiers.patch \
    file://0001-tegra_defconfig-snapd-squashfs-configuration.patch \
"
SRC_URI[md5sum] = "7ed29fecc9775ecb5cf3ede79d6ed844"
SRC_URI[sha256sum] = "f5ca2ba3d6ab5130bcca9c693aecafbd9813db11d450bd74ac7305bc46544c34"

COMPATIBLE_MACHINE = "(apalis-tk1-mainline|apalis-t30-mainline)"
KERNEL_EXTRA_ARGS = " LOADADDR=0x80008000 "

# One possibiltiy for changes to the defconfig:
config_script () {
    echo "dummy" > /dev/null
}

do_configure_prepend () {
    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake ${KERNEL_DEFCONFIG}

    #maybe change some configuration
    config_script

    #Add Toradex BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    cd - > /dev/null
}

do_uboot_mkimage_prepend() {
    cd ${B}
}

