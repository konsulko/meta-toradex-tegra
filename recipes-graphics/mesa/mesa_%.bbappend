# we use binary only EGL, GL ES1, GL ES2 drivers (package trdx-nv-binaries)
# adapted from meta-fsl-arm/recipes-graphics/mesa/
# FIXME: We may need to disable EGL, GL ES1 and GL ES2
PACKAGECONFIG_remove_tegra = "egl gles"

PROVIDES_remove_tegra = "virtual/libgles1 virtual/libgles2 virtual/egl"

PACKAGE_ARCH_tegra = "${MACHINE_ARCH}"

#####

PACKAGE_ARCH_apalis-tk1 = "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS_REMOVE = "linux-driver-package"

DEPENDS_REMOVE_apalis-tk1 = ""

DEPENDS_remove = "${DEPENDS_REMOVE}"

# until meta-jetson-tk1 adds it through its bbappend:
DEPENDS_append_apalis-tk1 = "linux-driver-package "