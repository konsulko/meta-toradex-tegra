DESCRIPTION = "tegrastats, gives information about cpu use"
LICENSE = "Nvidia propriatry"
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#SRC_URI =  "file://tegrastats \
#            "

S = "${WORKDIR}/target-utils/tegrastats"

SVN_REV = "230"
SRC_URI = "svn://tegradev:tegra123!@mammut.toradex.int:8090/colibri_tegra_linux/trunk;module=target-utils/tegrastats;rev=${SVN_REV};proto=http"

PACKAGES = "${PN}"

#FILES_${PN} = 

do_compile() {
	${CC} -DNV_IS_LDK=1 ${CFLAGS} ${CPPFLAGS} ${LDFLAGS} ${S}/main.c -o tegrastats
}

do_install() {
	install -d ${D}/usr/bin
	install -m 0755  ${S}/tegrastats  ${D}/usr/bin
}
