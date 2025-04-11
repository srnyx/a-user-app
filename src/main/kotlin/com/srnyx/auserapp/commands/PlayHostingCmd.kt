package com.srnyx.auserapp.commands

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.SlashOption
import io.github.freya022.botcommands.api.commands.application.slash.annotations.TopLevelSlashCommandData

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder


@Command
class PlayHostingCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "playhosting",
        description = "Send a message telling the user to ask for help in the Play Hosting server")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun playHosting(event: GlobalSlashEvent, @SlashOption(description = "The user to tell about the Play Hosting server") user: User?) {
        val builder = MessageCreateBuilder()
        user?.let { builder.setContent(it.asMention) }
        event.reply(builder.setEmbeds(getEmbed()).build()).queue()
    }

    companion object {
        fun getEmbed(): MessageEmbed = EmbedBuilder()
            .setColor(0xFFAD1F)
            .setTitle("Play Hosting Support")
            .setDescription(":wave: **Hi there!** If you need support for Play Hosting, please ask in the [Play Hosting Discord server](https://discord.gg/playhosting)")
            .setFooter("Play Hosting", "https://cdn.venox.network/r/profile_square_square.png")
            .build()
    }
}
