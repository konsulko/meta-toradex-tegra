FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

ADD_PATCHES = ""
ADD_PATCHES_tegra = "file://Revert_Use_new_pixman_glyph_cache_t_API_that_will_be_in_pixman0.28.0.patch"

SRC_URI += "${ADD_PATCHES}"

PACKAGE_ARCH_tegra = "${MACHINE_ARCH}"
