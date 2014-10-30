SUMMARY = "TegraRCM"
DESCRIPTION = "Utility used to upload payloads to a NVIDIA Tegra based device in recovery mode (RCM)."
SECTION = "bootloader"
DEPENDS = "libusb1-native libcryptopp-native"

LICENSE = "NVIDIA-Public"
LIC_FILES_CHKSUM = "file://LICENSE;md5=395fe5affb633ad84474e42989a8e5be"

BBCLASSEXTEND = "native nativesdk"

SRC_URI = "git://github.com/NVIDIA/tegrarcm.git \
	  "
SRCREV = "12b9718fc6b5be374d252be691733837e756d816"
PV = "1.6"

EXTRA_OEMAKE='PREFIX="${prefix}" LIBDIR="${libdir}"'

S = "${WORKDIR}/git"

inherit autotools

