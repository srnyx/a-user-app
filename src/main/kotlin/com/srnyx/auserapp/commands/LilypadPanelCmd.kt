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
class LilypadPanelCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "lilypad_panel",
        description = "Send a message asking for the user to send their Lilypad server game panel link")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun lilypadPanel(event: GlobalSlashEvent, @SlashOption(description = "The user to request the panel of") user: User?) {
        val builder = MessageCreateBuilder()
        user?.let { builder.setContent(it.asMention) }
        event.reply(builder.setEmbeds(getEmbed()).build()).queue()
    }

    companion object {
        fun getEmbed(): MessageEmbed = EmbedBuilder()
            .setColor(0xAAD28A)
            .setTitle("Lilypad Panel Request")
            .setDescription(":wave: **Hi there!** In order to assist you, we require a link to your server's panel/dashboard\nPlease send it in a ticket or DMs, thanks!")
            .addField("Example", "https://panel.lilypad.gg/server/by2qft6p", true)
            .setFooter("Lilypad", "https://us-east-1.tixte.net/uploads/cdn.venox.network/lilypad.gif")
            .build()
    }
}
