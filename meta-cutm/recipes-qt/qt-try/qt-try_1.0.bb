SUMMARY = "QT Example Recipe"
LICENSE = "CLOSED"


DEPENDS += "qtbase"

RDEPENDS:${PN} = "qtwayland"

SRC_URI = "file://qt_try.cpp \
           file://qt_try.pro"

S = "${WORKDIR}"

inherit qmake5


