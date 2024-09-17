pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Scan Thing"
include(":app")
include(":core")
include(":feature_photo")
include(":feature_scan_photo")
include(":feature_main")
