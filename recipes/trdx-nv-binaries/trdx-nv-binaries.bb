DESCRIPTION = "binary files from Nvidia along with there configuration"
LICENSE = "CLOSED"
PR = "r3"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI_colibri-t20 =  "file://base.tgz \
	file://restricted_codecs.tbz2 \
	file://mimeapps.list \
	file://nvgstapps.tgz \
	file://nvgstplayer.desktop \
"

SRC_URI_colibri-t30 =  "file://base.tgz \
	file://x/tegra_drv.abi*.so \
	file://restricted_codecs.tbz2 \
	file://Tegra-Linux-nvgstplayerR15.alpha.1.0.tbz2 \
	file://wifi.tbz2 \
	file://xorg.conf \
	file://mimeapps.list \
	file://nvgstplayer.desktop \
"

PACKAGES = "${PN}"

FILES_${PN} += " \
	${sysconfdir}/X11/def* \
	${sysconfdir}/X11/xorg.conf.* \
	/lib/firmware/* \
	/usr/lib/* \
	/home/root/.local/share/applications/* \
	"

#no gnu_hash in NVIDIA binaries, skip QA dev-so for this package
#we have symlinks ending in .so, skip QA ldflags for this package
INSANE_SKIP_${PN} = "dev-so ldflags"

do_install_colibri-t20 () {
	#base.tgz
	install -d ${D} ${D}/usr/bin ${D}/${sysconfdir}/X11 ${D}/lib/firmware ${D}/usr/lib/gstreamer-0.10 
	install -d ${D}/usr/lib/xorg/modules/drivers ${D}/home/root/.local/share/applications/
	install -m 0755 ${WORKDIR}/usr/bin/* ${D}/usr/bin/
	install -m 0644 ${WORKDIR}/${sysconfdir}/X11/xorg.conf ${D}/${sysconfdir}/X11/xorg.conf.nvidia
	install -m 0644 ${WORKDIR}/${sysconfdir}/nv* ${D}/${sysconfdir}/
	install -m 0644 ${WORKDIR}/lib/firmware/* ${D}/lib/firmware/
	install -m 0644 ${WORKDIR}/usr/lib/*.so ${D}/usr/lib/
	install -m 0644 ${WORKDIR}/usr/lib/gstreamer-0.10/*.so ${D}/usr/lib/gstreamer-0.10
	install -m 0644 ${WORKDIR}/usr/lib/xorg/modules/drivers/* ${D}/usr/lib/xorg/modules/drivers/
	install -m 0644 ${WORKDIR}/mimeapps.list ${D}//home/root/.local/share/applications/
	install -m 0644 ${WORKDIR}/nvgstplayer.desktop ${D}/home/root/.local/share/applications/
	ln -s libpcre.so.0.0.1 ${D}/usr/lib/libpcre.so.3
	ln -s tegra_drv.abi11.so ${D}/usr/lib/xorg/modules/drivers/tegra_drv.so
	ln -s libGLESv2.so ${D}/usr/lib//libGLESv2.so.2
	ln -s libEGL.so ${D}/usr/lib/libEGL.so.1
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
	cd ${WORKDIR}; tar xjf nvgstplayer.tbz2; cd ${S}
	install -d ${D}/usr/bin ${D}/usr/lib/gstreamer-0.10 ${D}/home/root/.local/share/applications/
	install -m 0755 ${WORKDIR}/usr/bin/nvgstplayer ${D}/usr/bin/
	install -m 0644 ${WORKDIR}/usr/lib/gstreamer-0.10/*.so ${D}/usr/lib/gstreamer-0.10/
	install -m 0644 ${WORKDIR}/mimeapps.list ${D}//home/root/.local/share/applications/
	install -m 0644 ${WORKDIR}/nvgstplayer.desktop ${D}/home/root/.local/share/applications/
	ln -s libpcre.so.0.0.1 ${D}/usr/lib/libpcre.so.3

	#wifi.tbz2
	install -d ${D} ${D}/lib/firmware/bcm4329
	install -m 0644 ${WORKDIR}/lib/firmware/bcm4329/* ${D}/lib/firmware/bcm4329
}
