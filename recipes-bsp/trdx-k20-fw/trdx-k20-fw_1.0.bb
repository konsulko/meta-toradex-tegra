DESCRIPTION = "Toradex Apalis TK1 K20 MCU Firmware"
HOMEPAGE = "https://developer.toradex.com/"
LICENSE = "GPLv2"

SRC_URI = " file://apalis-tk1-k20.bin"

LIC_FILES_CHKSUM = "file://LICENCE;md5=f711eb988c2634cf4f4184914b8174cf"

PR = "r0"

FILES_${PN} = " /lib/firmware/apalis-tk1-k20.bin "

do_install () {
    install -d ${D}/lib/firmware/
    install -m 0644 ${WORKDIR}/apalis-tk1-k20.bin ${D}/lib/firmware/
}
