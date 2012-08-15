# Ugly temporary hack to have Xserver not segfaulting
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://crash-hack.patch"
