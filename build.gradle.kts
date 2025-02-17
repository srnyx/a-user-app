import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupJda


plugins {
    application
    kotlin("jvm")
    id("xyz.srnyx.gradle-galaxy") version "1.3.3"
    id("com.gradleup.shadow") version "8.3.6"
    id("dev.reformator.stacktracedecoroutinator") version "2.4.5"
}

setupJda("5.3.0", "com.srnyx", "1.0.0", "A simple Discord user app")

repository(Repository.MAVEN_CENTRAL, Repository.JITPACK)
dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.freya022", "BotCommands", "3.0.0-alpha.24")
    implementation("org.spongepowered", "configurate-yaml", "4.1.2")
    implementation("org.postgresql", "postgresql", "42.7.5")
    implementation("com.zaxxer", "HikariCP", "6.2.1")
    implementation("ch.qos.logback", "logback-classic", "1.5.16")
}

kotlin {
    jvmToolchain(21)
}

// Fix some tasks
tasks {
    distZip { dependsOn("shadowJar") }
    distTar { dependsOn("shadowJar") }
    startScripts { dependsOn("shadowJar") }
    startShadowScripts { dependsOn("jar") }
}
