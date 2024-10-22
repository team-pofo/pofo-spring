rootProject.name = "pofo-spring"

include(":pofo-api")
include(":pofo-common")
include(":pofo-domain")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
