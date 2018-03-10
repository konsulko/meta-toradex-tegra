SUMMARY = "binary files from Nvidia along with their configuration"
LICENSE = "CLOSED & SGI & Khronos"
PR = "r19"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(tegra)"

PROVIDES += "virtual/egl virtual/libgles1 virtual/libgles2"
DEPENDS = "gstreamer gst-plugins-base libpcre virtual/xserver virtual/libx11 libxdamage libxext libxfixes"
RDEPENDS_${PN}-nv-gstapps = "libpcre libpcreposix"

# the khronos headers are taken from here: https://www.khronos.org/registry/khronos_headers.tgz
# this tarball changes from time to time breaking the receipe, thus it is provided with the recipe
SRC_COMMON = " \
    file://aplay.desktop \
    file://egl.pc \
    file://eglplatform.h \
    file://gles.pc \
    file://glesv2.pc \
    file://khronos_headers.tgz \
    file://mimeapps.list \
    file://nvgstplayer.desktop \
    https://www.khronos.org/registry/omxil/api/1.1.2/OpenMAX_IL_1_1_2_Header.zip;name=openmax-h;unpack=no \
    file://0001-egl-Add-EGL_PLATFORM_X11_KHR-alias-to-EGL_PLATFORM_X.patch;apply=no \
"

SRC_URI_tegra2 = " \
    http://developer.download.nvidia.com/mobile/tegra/l4t/r16.5.0/ventana_release_armhf/Tegra20_Linux-codecs_R16.5_armhf.tbz2;name=t20codecs \
    http://developer.download.nvidia.com/mobile/tegra/l4t/r16.5.0/ventana_release_armhf/Tegra20_Linux_R16.5_armhf.tbz2;name=t20drivers \
    ${SRC_COMMON} \
"

SRC_URI_tegra3 = " \
    http://developer.download.nvidia.com/mobile/tegra/l4t/r16.5.0/cardhu_release_armhf/Tegra30_Linux-codecs_R16.5_armhf.tbz2;name=t30codecs \
    http://developer.download.nvidia.com/mobile/tegra/l4t/r16.5.0/cardhu_release_armhf/Tegra30_Linux_R16.5_armhf.tbz2;name=t30drivers \
    http://developer.toradex.com/files/toradex-dev/uploads/media/Colibri/Linux/Extra/libgstomx.so;name=libgstomx \
    ${SRC_COMMON} \
"

SRC_URI[openmax-h.md5sum] = "402a7600397c341895cfecbbe1f4e028"
SRC_URI[openmax-h.sha256sum] = "e7159c88185df60a0dbb28583815067c31285a1f1d4652f6752a7329ccb534d1"

SRC_URI[t20codecs.md5sum] = "03da7451b755094c61dade6250f1ad0a"
SRC_URI[t20codecs.sha256sum] = "0c5fe818d5e0bdc83c24b7cb0db4f87705244258bfe1925d0a0a0698e3bf712b"
SRC_URI[t20drivers.md5sum] = "5252b747668c14376b3f82d08debad2c"
SRC_URI[t20drivers.sha256sum] = "02b7a83b29cec59cf275b98f0de0fc87a24ffa25593af6af729cd2e881c41587"

SRC_URI[t30codecs.md5sum] = "03a0b726f18a26ed379c62b83119e497"
SRC_URI[t30codecs.sha256sum] = "640df86c47d0dd6cbfc15eff49ee146a964c2a0362fe52ee6880c84e08b7a98b"
SRC_URI[t30drivers.md5sum] = "8759be1c7b93511cf70939df28b8af51"
SRC_URI[t30drivers.sha256sum] = "2288ad9ce30239975208cbd5fd896791c9d37d87ca8d30b51f04bacc74835427"
SRC_URI[libgstomx.md5sum] = "faa6ee38fb1b7aba7c6c7f98f6b2cedd"
SRC_URI[libgstomx.sha256sum] = "844c2f3ecb8498a9b287c2b2bc6184de75c2cf7d76f64f0a7749e76e1a37a1dc"


# xserver-xorg driver ABI version to be used by the symlink, must match the required ABI version from the used xserver
XSERVER_DRIVER_ABI_REQUIRED = "14"

LIC_FILES_CHKSUM = " \
    file://../khronos_headers/GLES2/gl2.h;beginline=8;endline=29;md5=c0e8cc16602f8077310fb00bbf128ef6 \
    file://${WORKDIR}/Linux_for_Tegra/nv_tegra/LICENSE;md5=d88b0db2ba7f6dfe70852c64b2fce6ce \
    file://${WORKDIR}/Tegra_Software_License_Agreement-Tegra-Linux-codecs.txt;md5=47cc79e762df48980a032c610cfc172f \
    file://${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/LICENSE.gst-openmax;md5=a7d467726825e72551082b781a94d09d \
"

PACKAGES = "${PN}-dbg ${PN}-restricted-codecs ${PN}-nv-gstapps ${PN} ${PN}-dev"

FILES_${PN}-dbg += " \
    /usr/lib/gstreamer-0.10/.debug \
"
FILES_${PN} += " \
    ${sysconfdir}/X11/def* \
    ${sysconfdir}/X11/xorg.conf.* \
    ${sysconfdir}/init/* \
    ${sysconfdir}/udev/rules.d/* \
    ${sysconfdir}/init/nv* \
    ${sysconfdir}/init/wpa* \
    /lib/firmware/* \
    /usr/lib/lib* \
    /usr/lib/xorg/* \
    /usr/lib/gstreamer*/* \
    /home/root/.local/share/applications/* \
"
FILES_${PN}-restricted-codecs += " \
    /lib/firmware/*.axf \
"
FILES_${PN}-nv-gstapps += " \
    /usr/bin/* \
    /usr/lib/libpcre* \
    /usr/share/doc/nv_gstapps/* \
"

#no gnu_hash in NVIDIA binaries, skip QA ldflags
#we have symlinks ending in .so, skip QA dev-so
#the qa check is not able to follow the libpcre symlink, skip QA file-rdeps
#inhibit warnings about files being stripped
INSANE_SKIP_${PN} = "dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nv-gstapps = "dev-so ldflags already-stripped textrel file-rdeps"

do_patch () {
    mkdir -p OpenMAX/il
    unzip -o -d OpenMAX/il OpenMAX_IL_1_1_2_Header.zip
    cd ${WORKDIR}/khronos_headers
    patch -p 1 < ${WORKDIR}/0001-egl-Add-EGL_PLATFORM_X11_KHR-alias-to-EGL_PLATFORM_X.patch
}

do_compile () {
    #unpack the different packages
    #nvidia drivers
    mkdir -p nvidia_drivers
    tar -C nvidia_drivers -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nvidia_drivers.tbz2
    tar -C nvidia_drivers -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/config.tbz2

    #nvidia sample gstreamer apps
    mkdir -p nvgstapps/usr/share/doc/nv_gstapps
    tar -C nvgstapps -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/nvgstapps.tbz2
    cp ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/nv*.txt nvgstapps/usr/share/doc/nv_gstapps/

    #restricted codecs
    mkdir -p restricted_codecs
    tar -C restricted_codecs -xjf ${WORKDIR}/restricted_codecs.tbz2
}

do_install () {
    #nvidia_driver
    install -d ${D}/usr/lib/xorg/modules/drivers ${D}/home/root/.local/share/applications/
    install -d ${D}/lib/firmware/
    install -d ${D}/${sysconfdir}/X11 ${D}/${sysconfdir}/init ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 nvidia_drivers/${sysconfdir}/X11/xorg.conf ${D}/${sysconfdir}/X11/xorg.conf.nvidia
    install -m 0755 nvidia_drivers/${sysconfdir}/init/* ${D}/${sysconfdir}/init/
    install -m 0644 nvidia_drivers/${sysconfdir}/udev/rules.d/* ${D}/${sysconfdir}/udev/rules.d/
    install -m 0644 nvidia_drivers/${sysconfdir}/nv* ${D}/${sysconfdir}/
    install -m 0644 nvidia_drivers/${sysconfdir}/wpa_supplicant.conf ${D}/${sysconfdir}/wpa_supplicant.conf.nvidia
    install -m 0644 nvidia_drivers/lib/firmware/* ${D}/lib/firmware/
    install -m 0644 nvidia_drivers/usr/lib/*.so ${D}/usr/lib/
    install -m 0644 nvidia_drivers/usr/lib/*.so.? ${D}/usr/lib/
    rm ${D}/usr/lib/libjpeg.so
    install -m 0644 nvidia_drivers/usr/lib/xorg/modules/drivers/* ${D}/usr/lib/xorg/modules/drivers/
    ln -s tegra_drv.abi${XSERVER_DRIVER_ABI_REQUIRED}.so ${D}/usr/lib/xorg/modules/drivers/tegra_drv.so
    # create symlink to the shared libs for development, *.so -> *.so.x
    export LIBNAME=`ls ${D}/usr/lib/libGLESv2.so.?`
    export LIBNAME=`basename $LIBNAME`
    ln -s $LIBNAME ${D}/usr/lib/libGLESv2.so
    export LIBNAME=`ls ${D}/usr/lib/libEGL.so.?`
    export LIBNAME=`basename $LIBNAME`
    ln -s $LIBNAME ${D}/usr/lib/libEGL.so
    export LIBNAME=`ls ${D}/usr/lib/libGLESv1_CM.so.?`
    export LIBNAME=`basename $LIBNAME`
    ln -s $LIBNAME ${D}/usr/lib/libGLESv1_CM.so

    #nvidia sample gstreamer apps
    install -d ${D}/usr/bin ${D}/usr/lib/gstreamer-0.10 ${D}/usr/share/doc/nv_gstapps
    install -d ${D}/usr/lib/xorg/modules/drivers ${D}/home/root/.local/share/applications/
    install -m 0755 nvgstapps/usr/bin/* ${D}/usr/bin/
    install -m 0644 nvgstapps/usr/lib/gstreamer-0.10/*.so ${D}/usr/lib/gstreamer-0.10/
    install -m 0644 nvgstapps/usr/share/doc/nv_gstapps/* ${D}/usr/share/doc/nv_gstapps/
    install -m 0644 ${WORKDIR}/*.desktop ${D}/home/root/.local/share/applications/
    install -m 0644 ${WORKDIR}/mimeapps.list ${D}//home/root/.local/share/applications/
    ln -s libpcre.so.1 ${D}/usr/lib/libpcre.so.3
    ln -s libpcreposix.so.0 ${D}/usr/lib/libpcreposix.so.3

    #nvidia restricted codecs
    install -d ${D}/lib/firmware/
    install -m 0644 restricted_codecs/lib/firmware/* ${D}/lib/firmware/

    #khronos headers for EGL/GLES/GLES2/OpenMax
    for dir in EGL GLES GLES2 KD KHR
    do
        install -d ${D}${includedir}/$dir
        install -m 0644 ${WORKDIR}/khronos_headers/$dir/* ${D}${includedir}/$dir
    done

    #Override eglplatform.h that khronos provide.
    install -m 0644 ${WORKDIR}/eglplatform.h ${D}${includedir}/EGL/

    dir="OpenMAX/il"
    install -d ${D}${includedir}/$dir
    install -m 0644 ${WORKDIR}/$dir/* ${D}${includedir}/$dir

    install -d  ${D}/usr/lib/pkgconfig
    install -m 0644 ${WORKDIR}/*.pc ${D}/usr/lib/pkgconfig/
}

do_install_append_tegra3 () {
    #OpenMAX-IL implementation library, evaluation version with fix to validate input frame rate
    install -m 0644 ${WORKDIR}/libgstomx.so ${D}/usr/lib/gstreamer-0.10/
}

# Add the ABI dependency at package generation time, as otherwise bitbake will
# attempt to find a provider for it (and fail) when it does the parse.
python populate_packages_prepend() {
    pn = d.getVar("PN", True)
    d.appendVar("RDEPENDS_" + pn, " xorg-abi-video-${XSERVER_DRIVER_ABI_REQUIRED}")
}
