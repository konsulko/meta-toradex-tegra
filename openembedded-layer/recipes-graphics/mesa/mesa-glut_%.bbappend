FILESEXTRAPATHS_prepend := "${THISDIR}/mesa-glut:"

SRC_URI_append_arm = " file://0001-mesa-glut-fix-building-on-arm.patch"
