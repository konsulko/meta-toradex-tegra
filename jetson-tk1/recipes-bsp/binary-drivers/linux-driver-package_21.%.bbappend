FILESEXTRAPATHS_prepend := "${THISDIR}/linux-driver-package:"

SRC_URI_append_apalis-tk1 = " \
    file://xorg.conf \
    file://nv.service \
"
inherit systemd

do_install_append_apalis-tk1 () {
    rm ${D}/usr/lib/libGL.so
    ln -sf ./arm-linux-gnueabihf/tegra/libGL.so.1 ${D}/usr/lib/arm-linux-gnueabihf/tegra/libGL.so

    cp ${WORKDIR}/xorg.conf ${D}/etc/X11/
    install -d ${D}${systemd_unitdir}/system/
    install -m 0755 ${WORKDIR}/nv.service ${D}${systemd_unitdir}/system
}

# deploy additional binaries from the nv_gst_apps tarball
PACKAGES_prepend = "${PN}-gstnvcamera ${PN}-gstnvvidconv-1.0 ${PN}-nvgstjpeg-1.0 ${PN}-gstnvvidconv-0.10 ${PN}-nvgstjpeg-0.10 ${PN}-nvgstapps "
RRECOMMENDS_${PN}_append = " ${PN}-gstnvcamera ${PN}-gstnvvidconv-1.0 ${PN}-nvgstjpeg-1.0 ${PN}-gstnvvidconv-0.10 ${PN}-nvgstjpeg-0.10 ${PN}-nvgstapps"

DEPENDS = "gst-plugins-base"

RDEPENDS_${PN}-gstnvcamera = "libgstvideo-1.0"
RDEPENDS_${PN}-gstnvvidconv-1.0 = "libgstvideo-1.0"
RDEPENDS_${PN}-gstnvvidconv-0.10 = "gstreamer"
RDEPENDS_${PN}-nvgstjpeg-1.0 = "libgstvideo-1.0"
RDEPENDS_${PN}-nvgstjpeg-0.10 = "gstreamer"
RDEPENDS_${PN}-nvgstapps = "libgstpbutils-1.0 gstreamer"

FILES_${PN}-gstnvcamera = " \
    ${libdir}/gstreamer-1.0/libgstnvcamera.so \
"

FILES_${PN}-gstnvvidconv-1.0 = " \
    ${libdir}/gstreamer-1.0/libgstnvvidconv.so \
"
FILES_${PN}-gstnvvidconv-0.10 = " \
    ${libdir}/gstreamer-0.10/libgstnvvidconv.so \
"

FILES_${PN}-nvgstjpeg-1.0 = " \
    ${libdir}/gstreamer-1.0/libnvgstjpeg.so \
"
FILES_${PN}-nvgstjpeg-0.10 = " \
    ${libdir}/gstreamer-0.10/libnvgstjpeg.so \
"

FILES_${PN}-nvgstapps = " \
    ${bindir}/nvgstcapture-1.0 \
    ${bindir}/nvgstplayer-1.0 \
    ${bindir}/nvgstcapture-0.10 \
    ${bindir}/nvgstplayer-0.10 \
    ${docdir}/nvgst*README.txt \
"

FILES_${PN}-boot = " \
    ${systemd_unitdir}/system/nv.service \
    ${bindir}/nv \
"

#no gnu_hash in NVIDIA binaries, skip QA dev-so for this package
#we have symlinks ending in .so, skip QA ldflags for this package
#inhibit warnings about files being stripped
INSANE_SKIP_${PN} = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-gstnvcamera = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-gstnvvidconv-1.0 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nvgstjpeg-1.0 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-gstnvvidconv-0.10 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nvgstjpeg-0.10 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nvgstapps = "build-deps dev-so ldflags already-stripped textrel"

do_install_append () {
    NV_SAMPLE=${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps
    tar xjf ${NV_SAMPLE}/nvgstapps.tbz2 -C ${NV_SAMPLE}
    install -d ${D}${bindir} ${D}${libdir}/gstreamer-1.0 ${D}${docdir}
    install -d ${D}${libdir}/gstreamer-0.10
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstcapture-1.0 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstplayer-1.0 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/nvgstcapture-1.0_README.txt ${D}${docdir}
    install -m 0755 ${NV_SAMPLE}/nvgstplayer-1.0_README.txt ${D}${docdir}
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstcapture-0.10 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstplayer-0.10 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/nvgstcapture-0.10_README.txt ${D}${docdir}
    install -m 0755 ${NV_SAMPLE}/nvgstplayer-0.10_README.txt ${D}${docdir}

    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libgstnvcamera.so ${D}${libdir}/gstreamer-1.0
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libgstnvvidconv.so ${D}${libdir}/gstreamer-1.0
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libnvgstjpeg.so ${D}${libdir}/gstreamer-1.0
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-0.10/libgstnvvidconv.so ${D}${libdir}/gstreamer-0.10
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-0.10/libnvgstjpeg.so ${D}${libdir}/gstreamer-0.10

    install -m 0755 ${WORKDIR}/nv ${D}${bindir}
    rm -rf ${D}${sysconfdir}/init.d/nv

    # Correct the ld.so.conf.d file
    echo "/usr/lib/arm-linux-gnueabihf/tegra" > \
        ${D}${sysconfdir}/ld.so.conf.d/nvidia-tegra.conf
    echo "/usr/lib/arm-linux-gnueabihf/tegra-egl" >> \
        ${D}${sysconfdir}/ld.so.conf.d/nvidia-tegra.conf
}

SYSTEMD_SERVICE_${PN} += " nv.service"

