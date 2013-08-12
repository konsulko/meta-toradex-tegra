DESCRIPTION = "binary files from Nvidia along with there configuration"
LICENSE = "CLOSED SGI Khronos"
PR = "r13"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LIC_DIR = "${datadir}/common-licenses"
#gstnvvidconf.tar.bz2 will hopefully be integrated into the public L4T packages
SRC_COMMON =  " \
    file://nvgstplayer.desktop \
    file://aplay.desktop \
    file://mimeapps.list \
    https://www.khronos.org/registry/khronos_headers.tgz;name=gles-h \
    https://www.khronos.org/registry/omxil/api/1.1.2/OpenMAX_IL_1_1_2_Header.zip;name=openmax-h;unpack=no \
    file://egl.pc \
    file://gles.pc \
    file://glesv2.pc \
"
SRC_URI_colibri-t20 =  " \
    file://ventana_Tegra-Linux-codecs-R16.3.0_armhf.tbz2 \
    file://ventana_Tegra-Linux-R16.3.0_armhf.tbz2 \
    ${SRC_COMMON} \
"

SRC_URI_colibri-t30 =  " \
    file://t30/cardhu_Tegra-Linux-codecs-R16.3.0_armhf.tbz2 \
    file://t30/cardhu_Tegra-Linux-R16.3.0_armhf.tbz2 \
    ${SRC_COMMON} \
"

SRC_URI_apalis-t30 =  " \
    file://t30/cardhu_Tegra-Linux-codecs-R16.3.0_armhf.tbz2 \
    file://t30/cardhu_Tegra-Linux-R16.3.0_armhf.tbz2 \
    ${SRC_COMMON} \
"

SRC_URI[gles-h.md5sum] = "b564caef8b776709a112754f7e9603af"
SRC_URI[gles-h.sha256sum] = "d8dbe2005d5312faf41c28f2b6de7e806f96cb3d3e269d80c95e4bda6d2c5526"

SRC_URI[openmax-h.md5sum] = "f8ac8d7272abdbe1980eeac8d03338e8"
SRC_URI[openmax-h.sha256sum] = "9e8aee85f37946202ff15a52836233f983e90a751c0816ba341ba0c1ffedf99e"
#    https://www.khronos.org/registry/omxil/api/1.2.0/OpenMAX_IL_1_2.0_Header.zip;name=openmax-h;unpack=no \
#SRC_URI[openmax-h.md5sum] = "a328b82e29d1e2abc1f20f070b9041a9"
#SRC_URI[openmax-h.sha256sum] = "9a121921450497e5373abcda000daf52af2ee31097d59c0d299a522b66936fa7"

LIC_FILES_CHKSUM = "file://../khronos_headers/GLES2/gl2.h;beginline=12;endline=15;md5=acbf6ad5edbe9552e8cc04776b0208fa"

PACKAGES = "${PN}-restricted-codecs ${PN}-nv-gstapps ${PN} ${PN}-dev"

FILES_${PN} += " \
    ${sysconfdir}/X11/def* \
    ${sysconfdir}/X11/xorg.conf.* \
    ${sysconfdir}/init/* \
    ${sysconfdir}/udev/rules.d/* \
    ${sysconfdir}/init/nv* \
    ${sysconfdir}/init/wpa* \	
    /lib/firmware/* \
    ${LIC_DIR}/${PN}/* \
    /usr/lib/* \
    /home/root/.local/share/applications/* \
"
FILES_${PN}-restricted-codecs += " \
    /lib/firmware/*.axf \
    ${LIC_DIR}/${PN}-restricted-codecs/* \
"
FILES_${PN}-nv-gstapps += " \
    /usr/bin/* \
    /usr/share/doc/nv_gstapps/* \
    ${LIC_DIR}/${PN}-nv-gstapps/* \
"

#no gnu_hash in NVIDIA binaries, skip QA dev-so for this package
#we have symlinks ending in .so, skip QA ldflags for this package
INSANE_SKIP_${PN} = "dev-so ldflags"
INSANE_SKIP_${PN}-nv-gstapps = "dev-so ldflags"

do_patch () {
    mkdir -p OpenMAX/il
    unzip -d OpenMAX/il OpenMAX_IL_1_1_2_Header.zip
}

do_compile () {
    #unpack the different packages
    #nvidia drivers
    mkdir -p nvidia_drivers${LIC_DIR}/${PN}/nvidia_drivers
    tar -C nvidia_drivers -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nvidia_drivers.tbz2
    tar -C nvidia_drivers -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/config.tbz2
    cp ${WORKDIR}/Linux_for_Tegra/nv_tegra/LICENSE nvidia_drivers${LIC_DIR}/${PN}/nvidia_drivers/

    #nvidia sample gstreamer apps
    mkdir -p nvgstapps${LIC_DIR}/${PN}-nv-gstapps
    mkdir -p nvgstapps/usr/share/doc/nv_gstapps
    tar -C nvgstapps -xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/nvgstapps.tbz2
    cp ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/LICENSE* nvgstapps${LIC_DIR}/${PN}-nv-gstapps/
    cp ${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/nv*.txt nvgstapps/usr/share/doc/nv_gstapps/

    #restricted codecs
    mkdir -p restricted_codecs${LIC_DIR}/${PN}-restricted-codecs/
    tar -C restricted_codecs -xjf ${WORKDIR}/restricted_codecs.tbz2
    cp ${WORKDIR}/*.txt restricted_codecs${LIC_DIR}/${PN}-restricted-codecs/
}

do_install () {
    #nvidia_driver
    install -d ${D}/usr/lib/xorg/modules/drivers ${D}/home/root/.local/share/applications/
    install -d ${D}${LIC_DIR}/${PN}/nvidia_drivers ${D}/lib/firmware/
    install -d ${D}/${sysconfdir}/X11 ${D}/${sysconfdir}/init ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 nvidia_drivers/${sysconfdir}/X11/xorg.conf ${D}/${sysconfdir}/X11/xorg.conf.nvidia
    install -m 0755 nvidia_drivers/${sysconfdir}/init/* ${D}/${sysconfdir}/init/
    install -m 0644 nvidia_drivers/${sysconfdir}/udev/rules.d/* ${D}/${sysconfdir}/udev/rules.d/
    install -m 0644 nvidia_drivers/${sysconfdir}/nv* ${D}/${sysconfdir}/
    install -m 0644 nvidia_drivers/${sysconfdir}/wpa* ${D}/${sysconfdir}/
    install -m 0644 nvidia_drivers/lib/firmware/* ${D}/lib/firmware/
    install -m 0644 nvidia_drivers${LIC_DIR}/${PN}/nvidia_drivers/* ${D}${LIC_DIR}/${PN}/nvidia_drivers/
    install -m 0644 nvidia_drivers/usr/lib/*.so ${D}/usr/lib/
    install -m 0644 nvidia_drivers/usr/lib/*.so.? ${D}/usr/lib/
    rm ${D}/usr/lib/libjpeg.so
    install -m 0644 nvidia_drivers/usr/lib/xorg/modules/drivers/* ${D}/usr/lib/xorg/modules/drivers/
    ln -s tegra_drv.abi11.so ${D}/usr/lib/xorg/modules/drivers/tegra_drv.so
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
    install -d ${D}${LIC_DIR}/${PN}-nv-gstapps/
    install -m 0755 nvgstapps/usr/bin/* ${D}/usr/bin/
    install -m 0644 nvgstapps/usr/lib/gstreamer-0.10/*.so ${D}/usr/lib/gstreamer-0.10/
    install -m 0644 nvgstapps/usr/share/doc/nv_gstapps/* ${D}/usr/share/doc/nv_gstapps/
    install -m 0644 nvgstapps${LIC_DIR}/${PN}-nv-gstapps/* ${D}${LIC_DIR}/${PN}-nv-gstapps/
    install -m 0644 ${WORKDIR}/*.desktop ${D}/home/root/.local/share/applications/
    install -m 0644 ${WORKDIR}/mimeapps.list ${D}//home/root/.local/share/applications/
    ln -s libpcre.so.1.0.1 ${D}/usr/lib/libpcre.so.3
    ln -s libpcreposix.so.0.0.1 ${D}/usr/lib/libpcreposix.so.3

    #nvidia restricted codecs
    install -d ${D}${LIC_DIR}/${PN}-restricted-codecs ${D}/lib/firmware/
    install -m 0644 restricted_codecs/lib/firmware/* ${D}/lib/firmware/
    install -m 0644 restricted_codecs${LIC_DIR}/${PN}-restricted-codecs/* ${D}${LIC_DIR}/${PN}-restricted-codecs/

    #khronos headers for EGL/GLES/GLES2/OpenMax
    for dir in EGL GLES GLES2 KD KHR
    do
        install -d ${D}${includedir}/$dir 
        install -m 0644 ${WORKDIR}/khronos_headers/$dir/* ${D}${includedir}/$dir
    done
    dir="OpenMAX/il"
    install -d ${D}${includedir}/$dir
    install -m 0644 ${WORKDIR}/$dir/* ${D}${includedir}/$dir

    install -d  ${D}/usr/lib/pkgconfig
    install -m 0644 ${WORKDIR}/*.pc ${D}/usr/lib/pkgconfig/
}
