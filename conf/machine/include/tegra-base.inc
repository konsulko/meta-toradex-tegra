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

PREFERRED_PROVIDER_virtual/mesa = "mesa"
PREFERRED_PROVIDER_virtual/egl = "trdx-nv-binaries"
PREFERRED_PROVIDER_virtual/libgles1 = "trdx-nv-binaries"
PREFERRED_PROVIDER_virtual/libgles2 = "trdx-nv-binaries"
