FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " file://0001-sync-kernel-UAPI.patch"
PACKAGES += " ${PN}-tegra"
FILES_${PN}-tegra = "${libdir}/libdrm_tegra.so.*"

EXTRA_OECONF_append += " --enable-tegra-experimental-api"
