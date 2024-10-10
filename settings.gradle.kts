rootProject.name = "backend"

include("api")
include("common")
include("domain")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}