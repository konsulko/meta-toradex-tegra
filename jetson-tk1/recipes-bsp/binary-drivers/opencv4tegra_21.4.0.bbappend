DEPENDS = "dpkg-native tiff zlib jpeg glib-2.0 python gtk+ libpng12 jasper libav cudatoolkit6.5"

SRC_URI = "http://developer.download.nvidia.com/embedded/L4T/r23_Release_v1.0/libopencv4tegra-repo_2.4.12.3_armhf_l4t-r23.deb"

LIC_FILES_CHKSUM = "file://usr/share/doc/libopencv4tegra-repo/copyright;md5=99d8c0c1313afdf990f6407c07a88407"

SRC_URI[md5sum] = "ee3ad8fb8eaafc357d5e228d61ad7123"
SRC_URI[sha256sum] = "096fb80a83c53f5e946c8106985eb147c8791b0cbe1e1fc38ebc49a3b932bf19"

do_install () {
    cp -r ${WORKDIR}/opencv4tegra/usr ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/opencv4tegra-repo/libopencv4tegra_2.4.12.3_armhf.deb ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/opencv4tegra-repo/libopencv4tegra-dev_2.4.12.3_armhf.deb ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/opencv4tegra-repo/libopencv4tegra-python_2.4.12.3_armhf.deb ${D}
}
