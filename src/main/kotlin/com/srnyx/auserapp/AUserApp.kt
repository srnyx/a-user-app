package com.srnyx.auserapp

import ch.qos.logback.classic.ClassicConstants

import io.github.freya022.botcommands.api.core.BotCommands
import io.github.oshai.kotlinlogging.KotlinLogging

import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.system.exitProcess


val LOGGER by lazy { KotlinLogging.logger {} }

object AUserApp {
    @JvmStatic
    fun main(args: Array<out String>) {
        try {
            System.setProperty(ClassicConstants.CONFIG_FILE_PROPERTY, Path("logback.xml").absolutePathString())

            BotCommands.create {
                addPredefinedOwners(Config.instance.owner)
                addSearchPath("com.srnyx.auserapp")
                textCommands { enable = false }
            }
        } catch (e: Exception) {
            LOGGER.error(e) { "Unable to start the bot" }
            exitProcess(1)
        }

        LOGGER.info { "A User App has finished starting!" }
    }
}
