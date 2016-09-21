FILESEXTRAPATHS_prepend := "${THISDIR}/linux-driver-package-${PV}:"

SRC_URI_append_apalis-tk1 = " file://xorg.conf"

do_install_append_apalis-tk1 () {
    cp ${WORKDIR}/xorg.conf ${D}/etc/X11/
}
