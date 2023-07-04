import java.util.Properties

plugins {
    kotlin("jvm") version "1.8.22"
    id("fabric-loom") version "1.3.5"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft(group = "com.mojang", name = "minecraft", version = minecraftVersion)
    mappings(group = "net.fabricmc", name = "yarn", version = minecraftVersion + "+build.22", classifier = "v2")

    modImplementation("net.fabricmc:fabric-loader:0.14.21")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.85.0+1.20.1")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.9.6+kotlin.1.8.22")
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.18:3.0.93")
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
