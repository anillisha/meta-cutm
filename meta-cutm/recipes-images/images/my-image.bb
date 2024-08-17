# # Base this image on core-image-minimal
# include recipes-core/images/core-image-minimal.bb

# # Include modules in rootfs
# IMAGE_INSTALL += " \
# 	kernel-modules \
# 	"

# SPLASH = "psplash-raspberrypi"
# SERIAL_CONSOLE = "115200 ttyAMA0"
# MACHINE_FEATURES = "apm usbhost keyboard vfat ext2 screen touchscreen tools-sdk alsa bluetooth wifi sdio splash package-management x11-base x11-sato ssh-server-dropbear hwcodecs"
# TOOLCHAIN_HOST_TASK_append = " nativesdk-intltool nativesdk-glib-2.0"
# TOOLCHAIN_HOST_TASK_remove_task-populate-sdk-ext = " nativesdk-intltool nativesdk-glib-2.0"

# QB_MEM = '${@bb.utils.contains("DISTRO_FEATURES", "opengl", "-m 512", "-m 256", d)}'
# QB_MEM_qemuarmv5 = "-m 256"
# QB_MEM_qemumips = "-m 256"

# do_image_prepend() {
#     bb.warn("The image 'rpi-basic-image' is deprecated, please use 'core-image-base' instead")
# }

# do_install_append_rpi () {
#     if [ "${@bb.utils.contains("MACHINE_FEATURES", "vc4graphics", "1", "0", d)}" = "0" ]; then
#         rm -f ${D}${libdir}/libwayland-egl*
#         rm -f ${D}${libdir}/pkgconfig/wayland-egl.pc
#     fi
# }

# EXTRA_IMAGE_FEATURES = "debug-tweaks eclipse-debug"
# # DISTRO_FEATURES_remove = "x11 wayland"
# REQUIRED_DISTRO_FEATURES = "x11 wayland"
# PACKAGECONFIG += "fontconfig"
# IMAGE_INSTALL_:append = " qtbase qtbase-fonts qtbase-plugins"
# IMAGE_INSTALL:append=" qt-helloworld"
# # IMAGE_INSTALL:append = "helloworld"s
# ENABLE_UART = "1"
# IMAGE_FSTYPES = "tar.xz ext3 rpi-sdimg"

DESCRIPTION = "Image with Sato, a mobile environment and visual style for \
mobile devices. The image supports X11 with a Sato theme, Pimlico \
applications, and contains terminal, editor, and file manager."
HOMEPAGE = "https://www.yoctoproject.org/"

IMAGE_FEATURES += "splash package-management x11-base x11-sato ssh-server-dropbear hwcodecs"

LICENSE = "MIT"

inherit core-image

TOOLCHAIN_HOST_TASK_append = " nativesdk-intltool nativesdk-glib-2.0"
TOOLCHAIN_HOST_TASK_remove_task-populate-sdk-ext = " nativesdk-intltool nativesdk-glib-2.0"

QB_MEM = '${@bb.utils.contains("DISTRO_FEATURES", "opengl", "-m 512", "-m 256", d)}'
QB_MEM_qemuarmv5 = "-m 256"
QB_MEM_qemumips = "-m 256"
PACKAGECONFIG += "gles2"

do_configure_prepend() {
    cat > ${S}/mkspecs/oe-device-extra.pri <<EOF
    QMAKE_LIBS_EGL += -lEGL -ldl -lglib-2.0 -lpthread
    QMAKE_LIBS_OPENGL_ES2 += -lGLESv2 -lgsl -lEGL -ldl -lglib-2.0 -lpthread

    QMAKE_CFLAGS += -DLINUX=1 -DWL_EGL_PLATFORM
    QMAKE_CXXFLAGS += -DLINUX=1 -DWL_EGL_PLATFORM

    QT_QPA_DEFAULT_PLATFORM = eglfs
    EOF
}
QT_EDITION = "commercial"
IMAGE_INSTALL:append=" qt-helloworld qtsmarthome"

ENABLE_UART = "1"
IMAGE_FSTYPES = "tar.xz ext3 rpi-sdimg"