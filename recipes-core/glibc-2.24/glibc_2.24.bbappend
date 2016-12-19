FILESEXTRAPATHS_prepend := "${THISDIR}/glibc-2.24:"
SRC_URI_append = " file://0001-linux-minimum-version-set-to-3.1.10.patch"
OLDEST_KERNEL = "3.1.10"
