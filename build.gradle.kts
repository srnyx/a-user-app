import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.srnyx.gradlegalaxy.utility.setupLazyLibrary


plugins {
    application
    kotlin("jvm") version "2.2.10"
    id("xyz.srnyx.gradle-galaxy") version "1.3.3"
    id("com.gradleup.shadow") version "8.3.8"
    id("dev.reformator.stacktracedecoroutinator") version "2.5.6"
}

setupLazyLibrary("f96c14230b", "5.6.1", "com.srnyx", "1.0.0", "A simple Discord user app")
    .exclude("io.github.freya022", "BotCommands")
    .exclude("net.dv8tion", "JDA")

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.freya022", "BotCommands", "3.0.0-beta.3")
    implementation("org.flywaydb", "flyway-database-postgresql", "11.11.2")
    compileOnly("io.github.oshai", "kotlin-logging-jvm", "7.0.13")
}

kotlin.jvmToolchain(22)

tasks.withType<ShadowJar> {
    // Fix Java's service loading, which Flyway uses
    mergeServiceFiles()

    // Delete bc_localization files from Lazy Library implementation
    // The warning message will still appear in IDE but won't when JAR actually ran
    exclude("bc_localization/DefaultMessages.json")
    exclude("bc_localization/DefaultMessages_default.json")
}
