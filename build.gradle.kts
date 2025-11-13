import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.srnyx.gradlegalaxy.utility.setupLazyLibrary
import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.data.config.DependencyConfig


plugins {
    application
    kotlin("jvm") version "2.2.10"
    id("xyz.srnyx.gradle-galaxy") version "2.0.2"
    id("com.gradleup.shadow") version "8.3.9"
    id("dev.reformator.stacktracedecoroutinator") version "2.5.7"
}

setupLazyLibrary(
    javaSetupConfig = JavaSetupConfig("com.srnyx", "1.0.0", "A simple Discord user app"),
    jdaConfig = DependencyConfig("6.1.1"),
    lazyLibraryConfig = DependencyConfig("botcommands-v3-SNAPSHOT"))

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin.jvmToolchain(22)

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> { mergeServiceFiles() }
