import java.util.Properties

plugins {
    kotlin("jvm") version "2.2.10"
    id("fabric-loom") version "1.11.7"
}

java {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
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
    maven("https://maven.fabricmc.net/")
    mavenLocal()
    mavenCentral()
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.3")
    mappings("net.fabricmc:yarn:1.21.3+build.2:v2")

    modImplementation("net.fabricmc:fabric-loader:0.16.14")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.114.1+1.21.3")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.3+kotlin.2.1.21")
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.21.3:4.7.2")
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
