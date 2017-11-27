# Provides common Tegra SoC settings

require conf/machine/include/soc-family.inc

# Tegra binaries require a specific X-Server binary ABI
PREFERRED_PROVIDER_virtual/xserver_tegra = "xserver-xorg"
PREFERRED_VERSION_xserver-xorg_tegra = "99:1.14%"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-input-mouse \
           xf86-video-fbdev \
           xf86-input-keyboard"

PREFERRED_PROVIDER_virtual/kernel = "linux-toradex"
KERNEL_IMAGETYPE = "uImage"
# The kernel lives in a seperate FAT or UBI partition, don't deploy it in /boot
RDEPENDS_kernel-base = ""

PREFERRED_PROVIDER_u-boot = "u-boot-toradex"
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-toradex"

# require u-boot-dtb-tegra.bin to be used
UBOOT_IMAGE = "u-boot-dtb-tegra-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY = "u-boot-dtb-tegra.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "u-boot-dtb-tegra-${MACHINE}.${UBOOT_SUFFIX}"

PREFERRED_PROVIDER_virtual/mesa = "mesa"
PREFERRED_PROVIDER_virtual/egl = "tdx-nv-binaries"
PREFERRED_PROVIDER_virtual/libgles1 = "tdx-nv-binaries"
PREFERRED_PROVIDER_virtual/libgles2 = "tdx-nv-binaries"

# Install NV binaries which contains tegra X-Server drivers when X11 is chosen
MACHINE_EXTRA_RRECOMMENDS_append_tegra = " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'tdx-nv-binaries', '', d)}"
