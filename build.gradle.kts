import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setMainClass
import xyz.srnyx.gradlegalaxy.utility.setupJava


plugins {
    application
    kotlin("jvm")
    id("xyz.srnyx.gradle-galaxy") version "1.3.2"
    id("io.github.goooler.shadow") version "8.1.8"
    id("dev.reformator.stacktracedecoroutinator") version "2.4.5"
}

setupJava("com.srnyx", "0.0.1", "A simple Discord user app")
setMainClass("com.srnyx.auserapp.AUserApp")

repository(Repository.MAVEN_CENTRAL, Repository.JITPACK)
dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.JDA-Fork", "JDA", "f5840dab04")
    implementation("io.github.freya022", "BotCommands", "09cddfe8a6")
    implementation("org.spongepowered", "configurate-yaml", "4.1.2")
    implementation("org.postgresql", "postgresql", "42.7.4")
    implementation("com.zaxxer", "HikariCP", "6.0.0")
    implementation("ch.qos.logback", "logback-classic", "1.5.8")
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
