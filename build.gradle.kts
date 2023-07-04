import java.util.*

plugins {
    kotlin("jvm") version "1.8.22"
    id("org.quiltmc.loom") version "1.+"
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

// load props from parent project
val parentProps = rootDir.resolve("gradle.properties").bufferedReader().use {
    Properties().apply {
        load(it)
    }
}

val modId: String by parentProps
val modVersion: String by parentProps
val group: String by parentProps
val minecraftVersion: String by parentProps

project.group = group
version = modVersion

repositories {
    maven("https://maven.fabricmc.net/") {
        name = "Fabric"
    }
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.20.1")
    mappings("org.quiltmc:quilt-mappings:1.20.1+build.9:intermediary-v2")

    modImplementation("org.quiltmc:qsl:6.0.4+1.20.1")
    modImplementation("org.quiltmc.quilted-fabric-api:quilted-fabric-api:7.0.5+0.84.0-1.20.1")
    modImplementation("org.quiltmc:quilt-loader:0.20.0-beta.1")
    modImplementation("org.quiltmc.quilt-kotlin-libraries:quilt-kotlin-libraries:2.1.0+kt.1.8.22+flk.1.9.4")

    modImplementation("software.bernie.geckolib:geckolib-fabric-1.20:4.2")
}

val fabricApiVersion = ""
val kotlinVersion = ""

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand(
            mutableMapOf(
                "modid" to modId,
                "version" to modVersion,
                "kotlinVersion" to kotlinVersion,
                "fabricApiVersion" to fabricApiVersion
            )
        )
    }
}
