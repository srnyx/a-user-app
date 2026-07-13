import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.srnyx.gradlegalaxy.data.config.DependencyConfig
import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.utility.setupLazyLibrary


plugins {
    application
    kotlin("jvm") version "2.4.0"
    id("xyz.srnyx.gradle-galaxy") version "3.2.0"
    id("com.gradleup.shadow") version "9.5.1"
    id("dev.reformator.stacktracedecoroutinator") version "2.6.4"
}

setupLazyLibrary(
    javaSetupConfig = JavaSetupConfig("com.srnyx", "1.0.0", "A simple Discord user app"),
    jdaConfig = DependencyConfig("6.5.0"),
    lazyLibraryConfig = DependencyConfig("8cb5ea4"))

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin.jvmToolchain(26)

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> { mergeServiceFiles() }
