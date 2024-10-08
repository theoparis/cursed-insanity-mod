import java.util.Properties

plugins {
    kotlin("jvm") version "2.0.10"
    id("fabric-loom") version "1.7.3"
}

java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}

// load props from parent project
val parentProps =
    rootDir.resolve("gradle.properties").bufferedReader().use {
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
    mavenLocal()
    mavenCentral()
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.21")
    mappings("net.fabricmc:yarn:1.21.1+build.3:v2")

    modImplementation("net.fabricmc:fabric-loader:0.16.0")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.1+1.21.1")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.12.0+kotlin.2.0.10")
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.21:4.5.8")
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
                "fabricApiVersion" to fabricApiVersion,
            ),
        )
    }
}
