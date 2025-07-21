import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.srnyx.gradlegalaxy.utility.setupLazyLibrary


plugins {
    application
    kotlin("jvm")
    id("xyz.srnyx.gradle-galaxy") version "1.3.3"
    id("com.gradleup.shadow") version "8.3.8"
    id("dev.reformator.stacktracedecoroutinator") version "2.5.4"
}

setupLazyLibrary("976f3062f3", "5.6.1", "com.srnyx", "1.0.0", "A simple Discord user app")

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.freya022", "BotCommands", "3.0.0-beta.2")
    implementation("org.flywaydb", "flyway-database-postgresql", "11.10.3")
}

kotlin.jvmToolchain(21)

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> { mergeServiceFiles() }
