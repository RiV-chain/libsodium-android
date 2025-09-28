pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://github.com/RiV-chain/artifact/raw/main")
        }
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://github.com/RiV-chain/artifact/raw/main")
        }
    }
}

rootProject.name = "libsodium-android"
include(":libsodium-android")
