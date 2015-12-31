# The tegras don't have a wayland backend, make sure it does not get enabled.
# Otherwise we get configure error, 'No package 'wayland-egl' found'
PACKAGECONFIG_remove_tegra = "wayland"
PACKAGE_ARCH_tegra = "${MACHINE_ARCH}"