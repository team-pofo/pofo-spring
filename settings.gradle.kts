rootProject.name = "moeum-backend"

include(":moeum-api")
include(":moeum-common")
include(":moeum-domain")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}