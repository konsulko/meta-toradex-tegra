LICENSE_${PN}-nvidia = "Firmware-nvidia"
LICENSE_${PN}-nvidia-license = "Firmware-nvidia"

PACKAGES_prepend = "\
                     ${PN}-nvidia-license ${PN}-nvidia \
                   "
FILES_${PN}-nvidia-license = "/lib/firmware/LICENCE.nvidia"
FILES_${PN}-nvidia = "/lib/firmware/nvidia"
RDEPENDS_${PN}-nvidia += "${PN}-nvidia-license"

