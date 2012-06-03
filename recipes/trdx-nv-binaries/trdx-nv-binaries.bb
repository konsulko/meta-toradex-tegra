DESCRIPTION = "binary files from Nvidia along with there configuration"
LICENSE = "CLOSED"
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI_colibri-t20 =  "file://bin-files.tar.bz2 \
            "

SRC_URI_colibri-t30 =  "file://base.tgz \
	file://x/tegra_drv.abi*.so \
	file://restricted_codecs.tbz2 \
	file://nvgstplayer.tbz2 \
	file://wifi.tbz2 \
	file://xorg.conf \
	"

PACKAGES = "${PN}"

FILES_${PN} += " \
	${sysconfdir}/X11/def* \
	${sysconfdir}/X11/xorg.conf.* \
	/lib/firmware/* \
	/usr/lib/* \
	"

#no gnu_hash in NVIDIA binaries, skip QA for this package
INSANE_SKIP_${PN} = "True"
#we have symlinks ending in .so , remove "dev-so", refere to ./openembedded-core/meta/classes/insane.bbclass
ERROR_QA = "debug-deps dev-deps arch la2 pkgconfig la perms"

do_install_colibri-t20 () {
	install -d ${D} ${D}/bin ${D}/${sysconfdir}/X11 ${D}/lib/firmware ${D}/usr/lib/gstreamer-0.10 ${D}/usr/lib/xorg/modules/drivers
	install -m 0755 ${WORKDIR}/bin/* ${D}/bin/
	install -m 0644 ${WORKDIR}/${sysconfdir}/X11/* ${D}/${sysconfdir}/X11
	install -m 0644 ${WORKDIR}/lib/firmware/* ${D}/lib/firmware/
	install -m 0755 ${WORKDIR}/usr/lib/*.so ${D}/usr/lib/
	install -m 0755 ${WORKDIR}/usr/lib/gstreamer-0.10/*.so ${D}/usr/lib/gstreamer-0.10
	install -m 0755 ${WORKDIR}/usr/lib/xorg/modules/drivers/* ${D}/usr/lib/xorg/modules/drivers/
}

do_install_colibri-t30 () {
	#base.tgz, xorg driver, restricted codecs
	install -d ${D} ${D}/${sysconfdir}/X11/ ${D}/lib/firmware/ ${D}/usr/lib/xorg/modules/drivers
	install -m 0644 ${WORKDIR}/${sysconfdir}/X11/xorg.conf ${D}/${sysconfdir}/X11/xorg.conf.nvidia
	install -m 0644 ${WORKDIR}/lib/firmware/nv* ${D}/lib/firmware/
	install -m 0644 ${WORKDIR}/usr/lib/*.so ${D}/usr/lib/
	install -m 0644 ${WORKDIR}/x/tegra_drv.abi5.so ${D}/usr/lib/xorg/modules/drivers/
	install -m 0644 ${WORKDIR}/x/tegra_drv.abi6.so ${D}/usr/lib/xorg/modules/drivers/
	install -m 0644 ${WORKDIR}/x/tegra_drv.abi7.so ${D}/usr/lib/xorg/modules/drivers/
	install -m 0644 ${WORKDIR}/x/tegra_drv.abi8.so ${D}/usr/lib/xorg/modules/drivers/
	install -m 0644 ${WORKDIR}/x/tegra_drv.abi10.so ${D}/usr/lib/xorg/modules/drivers/
	install -m 0644 ${WORKDIR}/x/tegra_drv.abi11.so ${D}/usr/lib/xorg/modules/drivers/
	ln -s tegra_drv.abi8.so ${D}/usr/lib/xorg/modules/drivers/tegra_drv.so
	ln -s libGLESv2.so ${D}/usr/lib//libGLESv2.so.2
	ln -s libEGL.so ${D}/usr/lib/libEGL.so.1

	#nvgstplayer.tbz2
	install -d ${D} ${D}/usr/bin ${D}/usr/lib/gstreamer-0.10
	install -m 0755 ${WORKDIR}/usr/bin/nvgstplayer ${D}/usr/bin/
	install -m 0644 ${WORKDIR}/usr/lib/gstreamer-0.10/*.so ${D}/usr/lib/gstreamer-0.10/

	#wifi.tbz2
	install -d ${D} ${D}/lib/firmware/bcm4329
	install -m 0644 ${WORKDIR}/lib/firmware/bcm4329/* ${D}/lib/firmware/bcm4329
}
