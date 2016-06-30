SUMMARY = "Disable LVDS output in favour of primary HDMI one on Apalis TK1"
LICENSE = "PD"

SRC_URI =  " \
    file://COPYING \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=1c3a7fb45253c11c74434676d84fe7dd"

ALLOW_EMPTY_${PN} = "1"

pkg_postinst_${PN}() {
    mkdir -p ${sysconfdir}/xdg/lxsession/LXDE
    echo "${bindir}/xrandr --output LVDS-0 --off" >> ${sysconfdir}/xdg/lxsession/LXDE/autostart
}

pkg_postrm_${PN}() {
    sed -i /xrandr.*output.*LVDS.*off/d ${sysconfdir}/xdg/lxsession/LXDE/autostart || true
}
