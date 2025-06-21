plugins {
    id("refinedarchitect.common")
}

base.archivesName = "${rootProject.base.archivesName.get()}-${project.name}"

refinedarchitect {
    common()
}

dependencies {
    compileOnly(libs.rs.common)
    compileOnly(libs.polymorph.neoforge)
    compileOnly(libs.quartzarsenal.common)

    // for whatever reason these aren't set transitively by RefinedArchitect
    compileOnly("org.ow2.asm:asm-tree:9.7.1")
    compileOnly("io.github.llamalad7:mixinextras-common:0.4.1")
}
