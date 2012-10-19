DESCRIPTION = "binary files from Nvidia along with there configuration"
LICENSE = "CLOSED"
PR = "r6"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI_colibri-t20 =  " \
    file://ventana_Tegra-Linux-codecs-R16.1.0_armhf.tbz2 \
    file://ventana_Tegra-Linux-R16.1.0_armhf.tbz2 \
    file://nvgstplayer.desktop \
    file://mimeapps.list \
"

#base.tgz \
#restricted_codecs.tbz2 \
#nvgstapps.tgz \
#mimeapps.list \
#"

SRC_URI_colibri-t30 =  "file://base.tgz \
	file://x/tegra_drv.abi*.so \
	file://restricted_codecs.tbz2 \
	file://Tegra-Linux-nvgstplayerR15.alpha.1.0.tbz2 \
	file://wifi.tbz2 \
	file://xorg.conf \
	file://mimeapps.list \
	file://nvgstplayer.desktop \
"

PACKAGES = "${PN}-restricted-codecs ${PN}-nv-gstapps ${PN}"

FILES_${PN} += " \
    ${sysconfdir}/X11/def* \
    ${sysconfdir}/X11/xorg.conf.* \
    ${sysconfdir}/init/* \
    ${sysconfdir}/udev/rules.d/* \
    ${sysconfdir}/init/nv* \
    ${sysconfdir}/init/wpa* \	
    /lib/firmware/* \
    /opt/licenses/* \
    /usr/lib/* \
    /home/root/.local/share/applications/* \
"
FILES_${PN}-restricted-codecs += " \
    /lib/firmware/*.axf \
    /opt/licenses/restricted_codecs/* \
"
FILES_${PN}-nv-gstapps += " \
    /usr/bin/* \
    /usr/share/doc/nv_gstapps/* \
"

#no gnu_hash in NVIDIA binaries, skip QA dev-so for this package
#we have symlinks ending in .so, skip QA ldflags for this package
INSANE_SKIP_${PN} = "dev-so ldflags"
INSANE_SKIP_${PN}-nv-gstapps = "dev-so ldflags"

do_compile_colibri-t20() {
    #unpack the different packages
    #nvidia drivers
    mkdir -p nvidia_drivers/opt/licenses/nvidia_drivers
    tar -C nvidia_drivers -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nvidia_drivers.tbz2
    tar -C nvidia_drivers -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/config.tbz2
    cp ${WORKDIR}/Linux_for_Tegra/nv_tegra/LICENSE nvidia_drivers/opt/licenses/nvidia_drivers/

    #nvidia sample gstreamer apps
    mkdir -p nvgstapps/opt/licenses/nv_gstreamer
    mkdir -p nvgstapps/usr/share/doc/nv_gstapps
    tar -C nvgstapps -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/nvgstapps.tbz2
    cp ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/LICENSE* nvgstapps/opt/licenses/nv_gstreamer/
    cp ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/nv*.txt nvgstapps/usr/share/doc/nv_gstapps/

    #restricted codecs
    mkdir -p restricted_codecs/opt/licenses/restricted_codecs
    tar -C restricted_codecs -xjf ${WORKDIR}/restricted_codecs.tbz2
    cp ${WORKDIR}/*.txt restricted_codecs/opt/licenses/restricted_codecs/

}

do_install_colibri-t20 () {
    #nvidia_driver
    install -d ${D}/usr/lib/xorg/modules/drivers ${D}/home/root/.local/share/applications/
    install -d ${D}/opt/licenses/nvidia_drivers ${D}/lib/firmware/
    install -d ${D}/${sysconfdir}/X11 ${D}/${sysconfdir}/init ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 nvidia_drivers/${sysconfdir}/X11/xorg.conf ${D}/${sysconfdir}/X11/xorg.conf.nvidia
    install -m 0755 nvidia_drivers/${sysconfdir}/init/* ${D}/${sysconfdir}/init/
    install -m 0644 nvidia_drivers/${sysconfdir}/udev/rules.d/* ${D}/${sysconfdir}/udev/rules.d/
    install -m 0644 nvidia_drivers/${sysconfdir}/nv* ${D}/${sysconfdir}/
    install -m 0644 nvidia_drivers/${sysconfdir}/wpa* ${D}/${sysconfdir}/
    install -m 0644 nvidia_drivers/lib/firmware/* ${D}/lib/firmware/
    install -m 0644 nvidia_drivers/opt/licenses/nvidia_drivers/* ${D}/opt/licenses/nvidia_drivers/
    install -m 0644 nvidia_drivers/usr/lib/*.so ${D}/usr/lib/
    rm ${D}/usr/lib/libjpeg.so
    install -m 0644 nvidia_drivers/usr/lib/xorg/modules/drivers/* ${D}/usr/lib/xorg/modules/drivers/
    ln -s tegra_drv.abi11.so ${D}/usr/lib/xorg/modules/drivers/tegra_drv.so
    ln -s libGLESv2.so ${D}/usr/lib/libGLESv2.so.2
    ln -s libEGL.so ${D}/usr/lib/libEGL.so.1

    #nvidia sample gstreamer apps
    install -d ${D}/usr/bin ${D}/usr/lib/gstreamer-0.10 ${D}/usr/share/doc/nv_gstapps
    install -d ${D}/usr/lib/xorg/modules/drivers ${D}/home/root/.local/share/applications/
    install -d ${D}/opt/licenses/nv_gstreamer
    install -m 0755 nvgstapps/usr/bin/* ${D}/usr/bin/
    install -m 0644 nvgstapps/usr/lib/gstreamer-0.10/*.so ${D}/usr/lib/gstreamer-0.10/
    install -m 0644 nvgstapps/usr/share/doc/nv_gstapps/* ${D}/usr/share/doc/nv_gstapps/
    install -m 0644 nvgstapps/opt/licenses/nv_gstreamer/* ${D}/opt/licenses/nv_gstreamer
    install -m 0644 ${WORKDIR}/nvgstplayer.desktop ${D}/home/root/.local/share/applications/
    install -m 0644 ${WORKDIR}/mimeapps.list ${D}//home/root/.local/share/applications/
    ln -s libpcre.so.1.0.1 ${D}/usr/lib/libpcre.so.3
    ln -s libpcreposix.so.0.0.1 ${D}/usr/lib/libpcreposix.so.3

    #nvidia restricted codecs
    install -d ${D}/opt/licenses/restricted_codecs ${D}/lib/firmware/
    install -m 0644 restricted_codecs/lib/firmware/* ${D}/lib/firmware/
    install -m 0644 restricted_codecs/opt/licenses/restricted_codecs/* ${D}/opt/licenses/restricted_codecs
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
