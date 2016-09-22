DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d"

SRC_URI = " \
	http://developer.download.nvidia.com/embedded/L4T/r21_Release_v5.0/source/gstegl_src.tbz2 \
	file://0001-rename-gstegl-to-gstnvegl.patch \
	file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
	file://0001-pkg-config-files-fix-qa-error.patch \
"
       
SRC_URI[md5sum] = "8a08b88f2faa807d94d1939ddcb93b22"
SRC_URI[sha256sum] = "f3ccf2dec5c283b1f4141609a4316bae1258b35181e2448264ae21754d8bdaed"

S = "${WORKDIR}/gstegl_src/gst-egl"

inherit autotools pkgconfig
# gobject-introspection

EXTRA_OECONF = "--disable-nls --disable-static-plugins --enable-introspection=no"

DEPENDS += " libffi glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base libpcre libxml2 zlib "
DEPENDS += " virtual/egl virtual/mesa virtual/libgles2 wayland gdbm drm "

FILES_${PN}-dbg = " \
	/usr/src/debug/* \
	/usr/lib/gstreamer-1.0/.debug/* \
	/usr/lib/.debug/* \
"

FILES_${PN} = " \
	/usr/lib/gstreamer-1.0/libgstnveglglessink.so \
	/usr/lib/gstreamer-1.0/libgstnveglglessink.la \
	/usr/lib/libgstnvegl-1.0.so \
	/usr/lib/libgstnvegl-1.0.so.0 \
	/usr/lib/libgstnvegl-1.0.so.0.203.0 \
"

