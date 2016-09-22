FILESEXTRAPATHS_prepend := "${THISDIR}/linux-driver-package-${PV}:"

SRC_URI_append_apalis-tk1 = " file://xorg.conf"

do_install_append_apalis-tk1 () {
    cp ${WORKDIR}/xorg.conf ${D}/etc/X11/
}

# deploy additional binaries from the nv_gst_apps tarball
PACKAGES_prepend = "${PN}-gstnvcamera ${PN}-gstnvvidconv ${PN}-nvgstjpeg ${PN}-nvgstapps "
RRECOMMENDS_${PN}_append = " ${PN}-gstnvcamera ${PN}-gstnvvidconv ${PN}-nvgstjpeg ${PN}-nvgstapps"

FILES_${PN}-gstnvcamera = " \
    ${libdir}/gstreamer-1.0/libgstnvcamera.so \
"
FILES_${PN}-gstnvvidconv = " \
    ${libdir}/gstreamer-1.0/libgstnvvidconv.so \
"
FILES_${PN}-nvgstjpeg = " \
    ${libdir}/gstreamer-1.0/libnvgstjpeg.so \
"
FILES_${PN}-nvgstapps = " \
    ${bindir}/nvgstcapture-1.0 \
    ${bindir}/nvgstplayer-1.0 \
    ${docdir}/nvgst*README.txt \
"

do_install_append () {
    NV_SAMPLE=${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps
    tar xjf ${NV_SAMPLE}/nvgstapps.tbz2 -C ${NV_SAMPLE}
    install -d ${D}${bindir} ${D}${libdir}/gstreamer-1.0 ${D}${docdir}
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstcapture-1.0 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstplayer-1.0 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/nvgstcapture-1.0_README.txt ${D}${docdir}
    install -m 0755 ${NV_SAMPLE}/nvgstplayer-1.0_README.txt ${D}${docdir}

    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libgstnvcamera.so ${D}${libdir}/gstreamer-1.0
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libgstnvvidconv.so ${D}${libdir}/gstreamer-1.0
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libnvgstjpeg.so ${D}${libdir}/gstreamer-1.0
}
