# we now use 1.17.4 for TK1, do not consisder the 1.17.2 recipe for any
# machine
PNBLACKLIST[xserver-xorg] = "don't prefer older xserver over newer one from layer with less priority"