plugins {
    id("refinedarchitect.neoforge")
}

base.archivesName = "${rootProject.base.archivesName.get()}-${project.name}"

refinedarchitect {
    modId = rootProject.base.archivesName.get()
    neoForge()
}

dependencies {
    compileOnly(project(":common"))
    commonJava(project(path = ":common", configuration = "commonJava"))
    commonResources(project(path = ":common", configuration = "commonResources"))

    implementation(libs.rs.neoforge)
    implementation(libs.polymorph.neoforge)
    implementation(libs.quartzarsenal.neoforge)
}
