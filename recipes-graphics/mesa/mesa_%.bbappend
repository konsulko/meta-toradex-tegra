# tegra2, tegra3
# we use binary only EGL, GL ES1, GL ES2 drivers (package trdx-nv-binaries)
# adapted from meta-fsl-arm/recipes-graphics/mesa/
# FIXME: We may need to disable EGL, GL ES1 and GL ES2
PACKAGECONFIG_remove_tegra = "egl gles"

PROVIDES_remove_tegra = "virtual/libgles1 virtual/libgles2 virtual/egl"

PACKAGE_ARCH_tegra = "${MACHINE_ARCH}"

#####
# tegra tk1

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS_REMOVE = "linux-driver-package"

DEPENDS_REMOVE_tegra124 = ""

DEPENDS_remove = "${DEPENDS_REMOVE}"

# until meta-jetson-tk1 adds it through its bbappend:
DEPENDS_append_tegra124= " linux-driver-package "
