plugins {
    id("refinedarchitect.fabric")
}

base.archivesName = "${rootProject.base.archivesName.get()}-${project.name}"

repositories {
    maven {
        name = "ModMenu"
        url = uri("https://maven.terraformersmc.com/")
        content {
            includeGroup("com.terraformersmc")
        }
    }

    maven {
        name = "Cloth Config"
        url = uri("https://maven.shedaniel.me/")
        content {
            includeGroup("me.shedaniel.cloth")
        }
    }

    maven {
        name = "Cardinal Components"
        url = uri("https://maven.ladysnake.org/releases")
        content {
            includeGroup("org.ladysnake.cardinal-components-api")
        }
    }
}

refinedarchitect {
    modId = rootProject.base.archivesName.get()
    fabric()
}

dependencies {
    compileOnly(project(":common"))
    commonJava(project(path = ":common", configuration = "commonJava"))
    commonResources(project(path = ":common", configuration = "commonResources"))

    "modImplementation"(libs.rs.fabric)
    "modImplementation"(libs.polymorph.fabric)
    "modImplementation"(libs.quartzarsenal.fabric)

    var cca = "6.1.2"
    "modRuntimeOnly"("org.ladysnake.cardinal-components-api:cardinal-components-base:$cca")
    "modRuntimeOnly"("org.ladysnake.cardinal-components-api:cardinal-components-block:$cca")
    "modRuntimeOnly"("org.ladysnake.cardinal-components-api:cardinal-components-entity:$cca")
}

tasks {
    processResources {
        val props = mapOf(
            "version" to version,
            "rsVersion" to rootProject.libs.versions.rs.get(),
            "polymorphVersion" to rootProject.libs.versions.polymorph.get()
        )
        inputs.properties(props)

        filesMatching("fabric.mod.json") {
            expand(props)
        }
    }
}
