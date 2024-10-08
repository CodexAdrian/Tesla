import java.nio.file.Path
import net.darkhax.tesla.gradle.Remapper

plugins {
    idea
    java
    `maven-publish`
    id ("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val mod_id: String by project
val mod_name: String by project
val minecraft_version: String by project
val csl_version: String by project

base {
    archivesName = "${mod_name}-common-${minecraft_version}"
}

minecraft {
    version(minecraft_version)
    if (file("src/main/resources/${mod_id}.accesswidener").exists())
        accessWideners(file("src/main/resources/${mod_id}.accesswidener"))
}

dependencies {
    implementation("com.google.code.findbugs:jsr305:3.0.1")
    compileOnly(group = "org.spongepowered", name = "mixin", version = "0.8.5")
    compileOnly(group = "io.github.llamalad7", name = "mixinextras-common", version = "0.3.5")
    annotationProcessor(group = "io.github.llamalad7", name = "mixinextras-common", version = "0.3.5")

    // compileOnly(creates)

    val remapDir: Path = project.layout.buildDirectory.asFile.map { it.toPath() }.get().resolve("tesla_remaps");

    compileOnly(group = "earth.terrarium.common_storage_lib", name = "common-storage-lib-data-neoforge-$minecraft_version", version = csl_version) {
        isTransitive = false
    }

    compileOnly(group = "earth.terrarium.common_storage_lib", name = "common-storage-lib-lookup-neoforge-$minecraft_version", version = csl_version) {
        isTransitive = false
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}

//remapping