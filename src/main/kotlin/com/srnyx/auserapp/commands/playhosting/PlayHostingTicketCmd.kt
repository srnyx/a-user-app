package com.srnyx.auserapp.commands.playhosting

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
class PlayHostingTicketCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "playhosting",
        subcommand = "ticket",
        description = "Let a user know what they can use a ticket for")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun playHostingTicketCmd(event: GlobalSlashEvent, @SlashOption(description = "The user to tell about tickets") user: User?) {
        val builder = MessageCreateBuilder()
        user?.let { builder.setContent(it.asMention) }
        event.reply(builder.setEmbeds(getEmbed()).build()).queue()
    }

    companion object {
        fun getEmbed(): MessageEmbed = EmbedBuilder()
            .setColor(0xFFAD1F)
            .setTitle("<:playhosting_cloud:1345508978498469958> Play Hosting Tickets")
            .setDescription(":wave: **Hi there!** Looks like you may have opened a ticket for an invalid reason...\nYou should only open a ticket if there is a bug with Play Hosting or if something needs fixing on our end\n\n> **If you need help running your server, please use <#1332915646035660841> instead!**\n### ✅ Examples of valid ticket reasons\n- \"The panel is not loading for me\"\n- \"I created my server, but I can't access it\"\nThese are issues that we would need to investigate further to resolve\n### ❌ Examples of invalid ticket reasons\n- \"My server isn't starting due to incorrect Java version\"\n- \"How do I install Forge 1.19.2\"\n- \"My modpack isn't working\"\nThese belong in <#1332915646035660841>, where anyone can help you!\n")
            .setFooter("Play Hosting", "https://cdn.venox.network/r/profile_square_square.png")
            .build()
    }
}
