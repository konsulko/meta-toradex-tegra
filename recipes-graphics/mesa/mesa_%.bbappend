FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# tegra2, tegra3
# we use binary only EGL, GL ES1, GL ES2 drivers (package trdx-nv-binaries)
# adapted from meta-fsl-arm/recipes-graphics/mesa/
PACKAGECONFIG_remove_tegra = "egl gles"

PROVIDES_remove_tegra = "virtual/libgles1 virtual/libgles2 virtual/egl"

PACKAGE_ARCH_tegra = "${MACHINE_ARCH}"

#####
# tegra tk1

DEPENDS_REMOVE = "linux-driver-package"
DEPENDS_REMOVE_tegra124 = ""
DEPENDS_remove = "${DEPENDS_REMOVE}"

SRC_URI_append_tegra124 = " file://tegra-path-add.patch"

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"

# until meta-jetson-tk1 adds it through its bbappend:
DEPENDS_append_tegra124= " linux-driver-package "

#####
## Tegra TK1 mainline kernel

PACKAGECONFIG_append_tegra124m = "dri3 egl gles gallium gbm "
EXTRA_OECONF_append_tegra124m = " --enable-texture-float --without-dri-drivers --enable-glx --enable-osmesa --enable-debug"
DRIDRIVERS_tegra124m = " "
GALLIUMDRIVERS_tegra124m = "tegra"

SRC_URI_append_tegra124m = "\
            file://0001-gallium-add-renderonly-library.patch \
            file://0004-gallium-add-tegra-support.patch \
            file://0005-tegra-open-card1-instead-of-renderD128.patch \
            file://0006-HACK-make-DRI-work-under-X.patch \
            file://0008-remove-dependency-on-libdrm_tegra.patch \
            "

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"
PE_tegra124m = "99"

