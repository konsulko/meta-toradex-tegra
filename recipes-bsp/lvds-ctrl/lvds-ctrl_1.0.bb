DESCRIPTION = "Scripts to enable the LVDS converter on Apalis-T30"
LICENSE = "Public Domain"
PR = "r1"

SRC_URI =  " \
    file://lvds-dual-channel.sh \
    file://lvds-single-channel.sh \
    file://COPYING \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=1c3a7fb45253c11c74434676d84fe7dd"

do_compile () {
}

do_install () {
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/*.sh ${D}/${bindir}
}
