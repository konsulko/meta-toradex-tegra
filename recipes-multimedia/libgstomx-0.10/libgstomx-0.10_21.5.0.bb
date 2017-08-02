DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"

SRC_URI = " \
    http://developer.download.nvidia.com/embedded/L4T/r21_Release_v5.0/source/gstomx_src.tbz2 \
    file://0001-fix-gstomx-compilation-issues.patch \
"

SRC_URI[md5sum] = "457764edbcbe7239f93f6e1284d66b17"
SRC_URI[sha256sum] = "586b95c8c56ef0b7527dc0f7006e0eccd12a7da9613606cc766cf8d85b0f0a23"

DEPENDS += " libgstnvegl libffi glib-2.0 gstreamer gst-plugins-base libpcre libxml2 zlib "
DEPENDS += " virtual/egl virtual/mesa virtual/libgles2 gdbm drm "

S = "${WORKDIR}/gstomx_src/gst-openmax"

inherit autotools pkgconfig

EXTRA_OECONF = " --enable-tegra --enable-eglimage --enable-experimental --disable-static "

CXXFLAGS += " -I${S}/omx/openmax -I${WORKDIR}/gstomx_src/nv_headers -Wl,--no-undefined "
CFLAGS += " -I${S}/omx/openmax -I${WORKDIR}/gstomx_src/nv_headers -Wl,--no-undefined "


do_configure_prepend() {
	export LIBS="-lEGL -lGLESv2 -lX11 -lgstreamer-0.10 "
}

FILES_${PN}-dbg = " \
    /usr/src/debug/* \
    /usr/lib/.debug/* \
"
FILES_${PN} = " \
    /usr/lib/gstreamer-0.10/libgstomx.so \
    /usr/lib/gstreamer-0.10/libgstomx.la \
"
