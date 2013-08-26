# When Apalis T30 is used with a external NV graphics card, the binary
# only driver fails (the dynamic linker can not resolve all symbols), 
# when xinerama is disabled in the X server

# adapted from meta-fsl-arm/recipes-graphics/mesa/
python __anonymous () {
    import re

    cur_machine = d.getVar('MACHINE', True)
    if cur_machine == 'apalis-t30':
        # Remove --disable-xinerama from EXTRA_OECONF
        extra_oeconf = d.getVar('EXTRA_OECONF', True).split()
        take_out = ['--disable-xinerama']
        new_extra_oeconf = []
        for i in extra_oeconf:
            if i not in take_out:
                new_extra_oeconf.append(i)

        d.setVar('EXTRA_OECONF', ' '.join(new_extra_oeconf))

        # We are now machine specific
        d.setVar('PACKAGE_ARCH', d.getVar('MACHINE_ARCH'))
}
