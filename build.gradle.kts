import java.util.Properties

plugins {
    kotlin("jvm") version "2.0.0"
    id("fabric-loom") version "1.7.2"
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
    maven("https://jitpack.io")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft(group = "com.mojang", name = "minecraft", version = minecraftVersion)
    mappings(group = "net.fabricmc", name = "yarn", version = minecraftVersion + "+build.22", classifier = "v2")

    modImplementation("net.fabricmc:fabric-loader:0.16.0")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.100.6+1.21")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.11.0+kotlin.2.0.0")
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.21:4.5.6")
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
