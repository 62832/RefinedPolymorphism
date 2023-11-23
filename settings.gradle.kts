pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.minecraftforge.net/") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            plugin("forge", "net.minecraftforge.gradle").version("6.0.+")
            plugin("mixin", "org.spongepowered.mixin").version("0.7.+")
            plugin("spotless", "com.diffplug.spotless").version("6.20.0")

            val minecraftVersion = "1.20.1"
            version("minecraft", minecraftVersion)

            val forgeVersion = "47.1.0"
            version("loader", forgeVersion.substringBefore('.'))
            library("forge", "net.minecraftforge", "forge").version("$minecraftVersion-$forgeVersion")
            library("mixin", "org.spongepowered", "mixin").version("0.8.5")

            version("refinedstorage", "1.12.4")
            library("refinedstorage", "com.refinedmods", "refinedstorage").versionRef("refinedstorage")

            version("polymorph", "0.49.1")
            library("polymorph", "curse.maven", "polymorph-388800").version("4813985")

            version("rsaddons", "0.10.0")
            library("rsaddons", "com.refinedmods", "refinedstorageaddons").versionRef("rsaddons")

            version("rebornstorage", "5.0.7")
            library("rebornstorage", "curse.maven", "rebornstorage-256662").version("4861934")

            version("universalgrid", "$minecraftVersion-1.1")
            library("universalgrid", "curse.maven", "universal-grid-536160").version("4839781")

            library("kubejs", "dev.latvian.mods", "kubejs-forge").version("2001.6.4-build.95")
            library("rhino", "dev.latvian.mods", "rhino-forge").version("2001.2.2-build.13")
            library("architectury", "dev.architectury", "architectury-forge").version("9.1.12")
        }
    }
}
