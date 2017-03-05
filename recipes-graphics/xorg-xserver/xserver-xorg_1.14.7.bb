require recipes-graphics/xorg-xserver/xserver-xorg-1.14.7.inc

COMPATIBLE_MACHINE = "(tegra2|tegra3)"

LIC_FILES_CHKSUM = "file://COPYING;md5=bc098b9774ed096943f6c37b5beeef13"

# Misc build failure for master HEAD
SRC_URI += "file://crosscompile.patch \
            file://fix_open_max_preprocessor_error.patch \
            file://mips64-compiler.patch \
            file://aarch64.patch \
            file://xorg-CVE-2013-6424.patch \
            file://0001-sdksyms.sh-Make-sdksyms.sh-work-with-gcc5.patch \
"
SRC_URI_append_tegra = " file://Revert_Use_new_pixman_glyph_cache_t_API_that_will_be_in_pixman0.28.0.patch"

SRC_URI[md5sum] = "0c285a813a6c3291c88d5a2b710aecb1"
SRC_URI[sha256sum] = "fcf66fa6ad86227613d2d3e8ae13ded297e2a1e947e9060a083eaf80d323451f"

# HACK: during do_rootfs opkg takes the latest and greatest available
# in any feed, e.g. one built for another machine without prefered version
# rather than what PREFERRED_VERSION puts in the MACHINE_ARCH feed
# so we set the recipe epoch here. Thus for our architecture we're
# always latest and greatest.
PE_tegra = "99"
PROVIDES_${PN} = "virtual/xserver"

PACKAGE_ARCH_tegra = "${MACHINE_ARCH}"

# These extensions are now integrated into the server, so declare the migration
# path for in-place upgrades.

RREPLACES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RPROVIDES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RCONFLICTS_${PN} = "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "

# provided by xf86-input-evdev_2.10.0
do_install_append () {
    rm -f ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}