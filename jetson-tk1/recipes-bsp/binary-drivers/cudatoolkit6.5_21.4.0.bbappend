DEPENDS = "dpkg-native"

INSANE_SKIP_${PN} += "already-stripped libdir"

python do_unpack () {
    locals = d.getVar('WORKDIR', True)
    s = d.getVar('S', True)
    d.setVar('WORKDIR', s)
    d.setVar('S', s)
    bb.build.exec_func('base_do_unpack', d)
    d.setVar('WORKDIR', locals)
}
