import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.srnyx.gradlegalaxy.utility.setupLazyLibrary


plugins {
    application
    kotlin("jvm") version "2.2.10"
    id("xyz.srnyx.gradle-galaxy") version "1.3.4"
    id("com.gradleup.shadow") version "8.3.8"
    id("dev.reformator.stacktracedecoroutinator") version "2.5.7"
}

setupLazyLibrary("botcommands-v3-SNAPSHOT", "5.6.1", "com.srnyx", "1.0.0", "A simple Discord user app")

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin.jvmToolchain(22)

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> { mergeServiceFiles() }
