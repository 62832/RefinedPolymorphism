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

            val minecraftVersion = "1.19.2"
            version("minecraft", minecraftVersion)

            val forgeVersion = "43.2.0"
            version("loader", forgeVersion.substringBefore('.'))
            library("forge", "net.minecraftforge", "forge").version("$minecraftVersion-$forgeVersion")
            library("mixin", "org.spongepowered", "mixin").version("0.8.5")

            version("refinedstorage", "1.11.7")
            library("refinedstorage", "com.refinedmods", "refinedstorage").versionRef("refinedstorage")

            version("polymorph", "0.46.5")
            library("polymorph", "curse.maven", "polymorph-388800").version("4813951")

            version("rsaddons", "0.9.0")
            library("rsaddons", "com.refinedmods", "refinedstorageaddons").versionRef("rsaddons")

            version("rebornstorage", "5.0.3")
            library("rebornstorage", "curse.maven", "rebornstorage-256662").version("4376288")

            version("universalgrid", "$minecraftVersion-1.1")
            library("universalgrid", "curse.maven", "universal-grid-536160").version("4576205")

            library("kubejs", "dev.latvian.mods", "kubejs-forge").version("1902.6.2-build.42")
            library("rhino", "dev.latvian.mods", "rhino-forge").version("1902.2.2-build.280")
            library("architectury", "dev.architectury", "architectury-forge").version("6.5.85")
        }
    }
}
