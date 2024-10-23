package com.srnyx.auserapp

import io.github.freya022.botcommands.api.core.service.annotations.BService

import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader

import kotlin.io.path.Path
import kotlin.system.exitProcess


data class Config(val token: String, val owner: Long) {
    companion object {
        @get:BService
        val instance: Config by lazy {
            // Load config.yml
            val node: ConfigurationNode = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).path(Path("config.yml")).build().load()

            // Get token
            val token: String? = node.node("token").string
            if (token == null) {
                LOGGER.error { "Token is not set in the configuration file" }
                exitProcess(1)
            }

            // Return config
            return@lazy Config(token, node.node("owner").long)
        }
    }
}