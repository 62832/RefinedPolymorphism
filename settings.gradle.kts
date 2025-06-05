pluginManagement {
    plugins {
        id("net.neoforged.moddev") version "2.0.77"
        id("net.neoforged.moddev.repositories") version "2.0.77"
        id("com.diffplug.spotless") version "7.0.1"
    }
}

plugins {
    id("net.neoforged.moddev.repositories")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

run {
    @Suppress("UnstableApiUsage")
    dependencyResolutionManagement {
        repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
        rulesMode = RulesMode.FAIL_ON_PROJECT_RULES

        repositories {
            mavenCentral()

            maven {
                url = uri("https://maven.pkg.github.com/refinedmods/refinedstorage2")
                credentials {
                    username = "anything"
                    password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
                }
            }

            maven {
                name = "Curse Maven"
                url = uri("https://cursemaven.com")
                content {
                    includeGroup("curse.maven")
                }
            }
        }

        versionCatalogs {
            create("libs") {
                val mc = "1.21.1"
                version("minecraft", mc)

                val nf = mc.substringAfter('.')
                version("neoforge", "${nf + (if (!nf.contains('.')) ".0" else "")}.104")
                version("parchment", "2024.11.17")

                library("rs2", "com.refinedmods.refinedstorage", "refinedstorage-neoforge").version("2.0.0-beta.2")
                library("polymorph", "curse.maven", "polymorph-388800").version("6451024-sources-6451025")

                library("quartzarsenal", "com.refinedmods.refinedstorage", "refinedstorage-quartz-arsenal-neoforge")
                    .version("1.0.0")
            }
        }
    }
}
