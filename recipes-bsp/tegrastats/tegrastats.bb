SUMMARY = "NVIDIAS tegrastats"
DESCRIPTION = "NVIDIAS tegrastats in a commandline version and one with output to a gtk title bar, gives information about cpu use"
LICENSE = "CLOSED"
PR = "r3"

SRC_URI = " \
    file://tegrastats \
    file://tegrastats-gtk \
    file://Tegrastats-gtk.desktop \
"

S = "${WORKDIR}"

PACKAGES_prepend = "${PN}-gtk "

# Inhibit warnings about files being stripped.
# Inhibit warnings about missing DEPENDS, Files are provided in binary form"
INSANE_SKIP_${PN} = "already-stripped build-deps file-rdeps ldflags"
INSANE_SKIP_${PN}-gtk = "already-stripped build-deps file-rdeps"


do_install() {
    install -d ${D}${bindir}/ ${D}/home/root/Desktop/
    install -m 0755 ${S}/tegrastats* ${D}${bindir}/
    install -m 0755 ${S}/Tegrastats-gtk.desktop ${D}/home/root/Desktop/
}

FILES_${PN}-gtk = " \
    ${bindir}/tegrastats-gtk \
    /home/root/Desktop/Tegra* \
"

FILES_${PN} = "${bindir}/tegrastats"

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"
