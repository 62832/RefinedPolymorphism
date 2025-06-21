plugins {
    id("refinedarchitect.root")
    id("refinedarchitect.base")
    id("com.diffplug.spotless")
}

val modId = "refinedpolymorph"
base.archivesName = modId

tasks.withType<Jar> {
    enabled = false
}

allprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "checkstyle")

    version = if (System.getenv("GITHUB_REF_TYPE") == "tag") System.getenv("GITHUB_REF_NAME") else "0.0.0"
    group = "gripe._90.$modId"

    checkstyle {
        // disable in favour of Spotless
        sourceSets = emptyList()
    }

    tasks {
        withType<Jar> {
            exclude("data")
        }
    }

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

    spotless {
        kotlinGradle {
            target("*.kts")
            diktat()
            leadingTabsToSpaces(4)
            endWithNewline()
        }

        java {
            target("/src/**/java/**/*.java")
            endWithNewline()
            leadingTabsToSpaces(4)
            removeUnusedImports()
            palantirJavaFormat()
            toggleOffOn()
            trimTrailingWhitespace()

            // courtesy of diffplug/spotless#240
            // https://github.com/diffplug/spotless/issues/240#issuecomment-385206606
            // also, ew (7.x): https://github.com/diffplug/spotless/issues/2387#issuecomment-2576459901
            custom("noWildcardImports", object : java.io.Serializable, com.diffplug.spotless.FormatterFunc {
                override fun apply(input: String): String {
                    if (input.contains("*;\n")) {
                        throw GradleException("No wildcard imports allowed.")
                    }

                    return input
                }
            })

            bumpThisNumberIfACustomStepChanges(1)
        }

        json {
            target("src/**/resources/**/*.json")
            biome()
            leadingTabsToSpaces(2)
            endWithNewline()
        }
    }
}
