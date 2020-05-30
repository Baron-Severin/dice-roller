pluginManagement {
    repositories {
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "dice-roller"

include(
    "engine",
    "core",
    "view"
)
