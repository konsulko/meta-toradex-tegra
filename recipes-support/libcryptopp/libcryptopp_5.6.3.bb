SUMMARY = "A free C++ class library of cryptographic schemes"
HOMEPAGE = "http://www.cryptopp.com/wiki/Main_Page"
BUGTRACKER = "http://sourceforge.net/apps/trac/cryptopp/"
SECTION = "libs"

LICENSE = "PD"
LIC_FILES_CHKSUM = "file://License.txt;md5=48e7e415ea7fcc35278d8e7dc8e70b5d"

BBCLASSEXTEND = "native nativesdk"

PR = "r1"

PVSHORT = "${@'${PV}'.replace('.','')}"
SRC_URI = " \
    https://sourceforge.net/projects/cryptopp/files/cryptopp/5.6.3/cryptopp563.zip;subdir=libcryptopp-${PV} \
    file://0001-Fix-cross-compilation.patch \
"
SRC_URI[md5sum] = "3c5b70e2ec98b7a24988734446242d07"
SRC_URI[sha256sum] = "9390670a14170dd0f48a6b6b06f74269ef4b056d4718a1a329f6f6069dc957c9"

inherit autotools-brokensep pkgconfig

EXTRA_OECONF = "--libdir=${base_libdir}"
TARGET_CC_ARCH += "${LDFLAGS}"

#we want tegrarcm binary to run on a 32-bit architecture, on x86_64 this requires the 32-bit compatibility libs
EXTRA_OEMAKE_class-native = "CC='${CC} -m32' CXX='${CXX} -m32'"

do_compile() {
    sed -i -e 's/^CXXFLAGS/#CXXFLAGS/' GNUmakefile
    export CXXFLAGS="${CXXFLAGS} -DNDEBUG -fPIC"
    oe_runmake -f GNUmakefile
    oe_runmake libcryptopp.a
}

do_install_prepend() {
    export PREFIX=${D}${prefix}
}

do_install_append() {
    if [ -f "${D}/usr/lib/libcryptopp.so" ] && [ ! -e "${D}/usr/lib/libcryptopp.so.${PV}" ]
    then
        mv ${D}/usr/lib/libcryptopp.so  ${D}/usr/lib/libcryptopp.so.${PV}
        ln -fs libcryptopp.so.${PV} ${D}/usr/lib/libcryptopp.so.5
        ln -fs libcryptopp.so.${PV} ${D}/usr/lib/libcryptopp.so
    fi
}
