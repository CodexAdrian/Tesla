plugins {
    java
    idea
    `maven-publish`
    id("fabric-loom") version("1.7-SNAPSHOT")
}

val mod_id: String by project
val mod_name: String by project
val minecraft_version: String by project
val fabric_version: String by project
val fabric_loader_version: String by project

base {
    archivesName.set("${mod_name}-fabric-${minecraft_version}")
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$fabric_loader_version")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
    implementation("com.google.code.findbugs:jsr305:3.0.1")

    val csl_version: String by project
    include(implementation(group = "earth.terrarium.common_storage_lib", name = "common-storage-lib-lookup-fabric-$minecraft_version", version = csl_version)) {
        isTransitive = false
    }

    include(implementation(group = "earth.terrarium.common_storage_lib", name = "common-storage-lib-data-fabric-$minecraft_version", version = csl_version)) {
        isTransitive = false
    }

    compileOnly(project(":common"))
}

loom {
    if (project(":common").file("src/main/resources/${mod_id}.accesswidener").exists())
        accessWidenerPath.set(project(":common").file("src/main/resources/${mod_id}.accesswidener"))

    @Suppress("UnstableApiUsage")
    mixin { defaultRefmapName.set("${mod_id}.refmap.json") }

    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

tasks {
    withType<JavaCompile> { source(project(":common").sourceSets.main.get().allSource) }

    javadoc { source(project(":common").sourceSets.main.get().allJava) }

    named("sourcesJar", Jar::class) { from(project(":common").sourceSets.main.get().allSource) }

    processResources { from(project(":common").sourceSets.main.get().resources) }
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
