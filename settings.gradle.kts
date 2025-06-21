pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven {
            url = uri("https://maven.pkg.github.com/refinedmods/refinedarchitect")
            credentials {
                username = "anything"
                password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
            }
        }

        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }

    plugins {
        val refinedArchitectVersion = "0.21.0"
        id("refinedarchitect.root").version(refinedArchitectVersion)
        id("refinedarchitect.base").version(refinedArchitectVersion)
        id("refinedarchitect.common").version(refinedArchitectVersion)
        id("refinedarchitect.neoforge").version(refinedArchitectVersion)
        id("refinedarchitect.fabric").version(refinedArchitectVersion)

        id("com.diffplug.spotless") version "7.0.1"
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/refinedmods/refinedarchitect")
            credentials {
                username = "anything"
                password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
            }
        }
    }

    versionCatalogs {
        create("libs") {
            val refinedArchitectVersion = "0.21.0"
            from("com.refinedmods.refinedarchitect:refinedarchitect-versioning:$refinedArchitectVersion")

            version("rs", "2.0.0-beta.2")
            library("rs-common", "com.refinedmods.refinedstorage", "refinedstorage-common").versionRef("rs")
            library("rs-fabric", "com.refinedmods.refinedstorage", "refinedstorage-fabric").versionRef("rs")
            library("rs-neoforge", "com.refinedmods.refinedstorage", "refinedstorage-neoforge").versionRef("rs")

            version("quartzarsenal", "1.0.0")
            library("quartzarsenal-common", "com.refinedmods.refinedstorage", "refinedstorage-quartz-arsenal-common")
                .versionRef(
                    "quartzarsenal"
                )
            library("quartzarsenal-fabric", "com.refinedmods.refinedstorage", "refinedstorage-quartz-arsenal-fabric")
                .versionRef(
                    "quartzarsenal"
                )
            library("quartzarsenal-neoforge", "com.refinedmods.refinedstorage",
                "refinedstorage-quartz-arsenal-neoforge")
                .versionRef("quartzarsenal")

            version("polymorph", "1.0.6")
            library("polymorph-fabric", "curse.maven", "polymorph-388800").version("6451022-sources-6451023")
            library("polymorph-neoforge", "curse.maven", "polymorph-388800").version("6451024-sources-6451025")
        }
    }
}

include("common")
include("fabric")
include("neoforge")
