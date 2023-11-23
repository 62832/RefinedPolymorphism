plugins {
    eclipse
    idea
    alias(libs.plugins.forge)
    alias(libs.plugins.mixin)
    alias(libs.plugins.spotless)
}

val modId = "refinedpolymorph"

version = (System.getenv("REFPOLY_VERSION") ?: "0.0.0").substringBefore('-')
group = "gripe.90"
base.archivesName.set(modId)

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

minecraft {
    mappings("official", libs.versions.minecraft.get())

    copyIdeResources.set(true)

    runs {
        configureEach {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "info")

            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("client")
        create("server")
    }
}

repositories {
    maven {
        name = "CreeperHost"
        url = uri("https://maven.creeperhost.net")
        content {
            includeGroup("com.refinedmods")
        }
    }

    maven {
        name = "CurseMaven"
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }

    maven {
        name = "saps.dev"
        url = uri("https://maven.saps.dev/releases")
        content {
            includeGroup("dev.latvian.mods")
        }
    }

    maven {
        name = "Architectury"
        url = uri("https://maven.architectury.dev")
        content {
            includeGroup("dev.architectury")
        }
    }
}

dependencies {
    minecraft(libs.forge)
    annotationProcessor(variantOf(libs.mixin) { classifier("processor") })

    // https://www.youtube.com/watch?v=GQPM4_fMIEg&t=44s
    implementation(fg.deobf(libs.refinedstorage.get(), closureOf<ModuleDependency> { isTransitive = false }))
    implementation(fg.deobf(libs.polymorph.get()))

    implementation(fg.deobf(libs.rsaddons.get(), closureOf<ModuleDependency> { isTransitive = false }))
    implementation(fg.deobf(libs.rebornstorage.get()))
    implementation(fg.deobf(libs.universalgrid.get()))

    runtimeOnly(fg.deobf(libs.kubejs.get()))
    runtimeOnly(fg.deobf(libs.rhino.get()))
    runtimeOnly(fg.deobf(libs.architectury.get()))
}

mixin {
    add(sourceSets.main.get(), "$modId.refmap.json")
    config("$modId.mixins.json")
}

tasks {
    register("releaseInfo") {
        doLast {
            val output = System.getenv("GITHUB_OUTPUT")

            if (!output.isNullOrEmpty()) {
                val outputFile = File(output)
                outputFile.appendText("MOD_VERSION=$version\n")
                outputFile.appendText("MINECRAFT_VERSION=${libs.versions.minecraft.get()}\n")
            }
        }
    }

    processResources {
        val replaceProperties = mapOf(
            "version" to project.version,
            "fmlVersion" to "[${libs.versions.loader.get()},)",
            "rsVersion" to "[${libs.versions.refinedstorage.get()},)",
            "polymorphVersion" to "[${libs.versions.polymorph.get()},)"
        )

        inputs.properties(replaceProperties)

        filesMatching("META-INF/mods.toml") {
            expand(replaceProperties)
        }
    }

    jar {
        finalizedBy("reobfJar")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

spotless {
    kotlinGradle {
        target("*.kts")
        diktat()
    }

    java {
        target("src/**/java/**/*.java")
        palantirJavaFormat()
        endWithNewline()
        indentWithSpaces(4)
        removeUnusedImports()
        toggleOffOn()
        trimTrailingWhitespace()

        // courtesy of diffplug/spotless#240
        // https://github.com/diffplug/spotless/issues/240#issuecomment-385206606
        custom("noWildcardImports") {
            if (it.contains("*;\n")) {
                throw Error("No wildcard imports allowed")
            }

            it
        }

        bumpThisNumberIfACustomStepChanges(1)
    }

    json {
        target("src/*/resources/**/*.json")
        targetExclude("src/generated/resources/**")
        prettier().config(mapOf("parser" to "json"))
    }
}
