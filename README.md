# OtgSwitch

Tiny system app with a single switch that forces the USB controller into host
mode on devices whose micro-USB ID pin is not wired to the SoC (LG Optimus L3
E400, MSM7225A). It sets the `sys.otg.mode` property (`host` / `peripheral`);
the device's `init.*.rc` turns that into a write to
`/sys/kernel/debug/otg/mode`.

- Runs as the system uid (`android.uid.system`, platform key), so it must be
  built into the ROM.
- Source is compatible with API 9 (Gingerbread) up to API 17 (Jelly Bean 4.2):
  the hidden `android.os.SystemProperties` is reached through reflection.
- The phone cannot power the attached device (VBUS GPIO not usable): use an OTG
  cable with external 5 V.

Needs in the device tree:

```
on boot
    mount debugfs debugfs /sys/kernel/debug

on property:sys.otg.mode=host
    write /sys/kernel/debug/otg/mode host

on property:sys.otg.mode=peripheral
    write /sys/kernel/debug/otg/mode peripheral
```

and `PRODUCT_PACKAGES += OtgSwitch`.
