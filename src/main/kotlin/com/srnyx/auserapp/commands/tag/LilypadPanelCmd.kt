package com.srnyx.auserapp.commands.tag

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.SlashOption

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder


@Command
class LilypadPanelCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "tag",
        subcommand = "lilypadpanel",
        description = "Send a message asking for the user to send their Lilypad server game panel link")
    // TopLevelSlashCommandData in ContactSupportCmd
    fun lilypadPanelCmd(event: GlobalSlashEvent,
                        @SlashOption(description = "The user to request the panel of") user: User?) {
        val builder = MessageCreateBuilder()
        user?.let { builder.setContent(it.asMention) }
        event.reply(builder.setEmbeds(getEmbed()).build()).queue()
    }

    companion object {
        fun getEmbed(): MessageEmbed = EmbedBuilder()
            .setColor(0xAAD28A)
            .setDescription(":wave: **Hi there!** In order to assist you, we require a link to your server's panel/dashboard\nPlease <#1111613901889613905> and send it in the ticket, thanks!")
            .addField("Example", "https://panel.lilypad.gg/server/1a2b3c4d", true)
            .setImage("https://i.imgur.com/ult4czh.png")
            .setFooter("Lilypad", "https://us-east-1.tixte.net/uploads/cdn.venox.network/lilypad.gif")
            .build()
    }
}
