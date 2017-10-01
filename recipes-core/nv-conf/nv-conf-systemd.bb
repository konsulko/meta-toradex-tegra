SECTION = "sysinit"
SUMMARY = "NVIDIA T30 CPU hot-plug configuration and startup"
# The license is meant for this recipe and the files it installs.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r1"

inherit allarch systemd

SRC_URI = " \
    file://nv-conf.sh \
    file://nv-conf.service \
"

do_install() {
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/nv-conf.sh ${D}/${bindir}/

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/nv-conf.service ${D}${systemd_unitdir}/system
}

FILES_${PN} += " \
    ${systemd_unitdir}/system \
"

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "nv-conf.service"

