SECTION = "graphical/utils"
DESCRIPTION = "Nvidia samples for OpenGL ES, OpenGL ES  headers"
LICENSE = "CLOSED"
DEPENDS = "trdx-nv-binaries"
RDEPENDS = "trdx-nv-binaries"

PR = "r0"

SRC_URI = "http://developer.toradex.com/files/toradex-dev/uploads/media/Colibri/Linux/Samples/nvsamples.tar.bz2 \
	   file://nvsamples-oe.patch \
"

S = "${WORKDIR}/nvsamples"

SRC_URI[md5sum] = "240b0beb0056dde2e6ac1538dc8b6684"
SRC_URI[sha256sum] = "2638beea80fd85fdc5f9443e6959c210e275e627f662266cd404ce7c68b0806d"

#no gnu_hash in NVIDIA binaries, skip QA for this package
INSANE_SKIP_${PN} = "dev-so ldflags"

FILES_${PN} += " \
        /home/root/textures/* \
"

do_compile () {
	cd ${S}/samples/opengles2
	oe_runmake clean
	oe_runmake WORKDIR=${WORKDIR}
}

do_install () {
# install the sample code
	install -d ${D}${bindir}
	install -d ${D}/home/root/textures
        install -m 0755 ${S}/samples/opengles2/ctree/ctree ${D}${bindir}
        install -m 0644 ${S}/samples/opengles2/ctree/textures/* ${D}/home/root/textures
        install -m 0755 ${S}/samples/opengles2/bubble/bubble ${D}${bindir}
        install -m 0644 ${S}/samples/opengles2/bubble/textures/* ${D}/home/root/textures
        install -m 0755 ${S}/samples/opengles2/gears/gears ${D}${bindir}

# export OpenGL ES headers
	for dir in EGL GLES2 KD KHR OpenMAX/il OpenMAX/ilclient
	do
		install -d ${D}${includedir}/$dir 
		install -m 0644 ${S}/include/$dir/* ${D}${includedir}/$dir
	done
}