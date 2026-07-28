import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.srnyx.gradlegalaxy.data.config.DependencyConfig
import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupLazyLibrary


plugins {
    application
    kotlin("jvm") version "2.4.0"
    id("xyz.srnyx.gradle-galaxy") version "3.2.0"
    id("com.gradleup.shadow") version "9.6.1"
    id("dev.reformator.stacktracedecoroutinator") version "2.6.4"
}

repository("https://repo.freya02.dev/snapshots/")
setupLazyLibrary(
    javaSetupConfig = JavaSetupConfig(
        group = "com.srnyx",
        description = "A simple Discord user app"),
    jdaConfig = DependencyConfig("6.5.0"),
    lazyLibraryConfig = DependencyConfig("8f35377"))

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin.jvmToolchain(26)

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> { mergeServiceFiles() }
