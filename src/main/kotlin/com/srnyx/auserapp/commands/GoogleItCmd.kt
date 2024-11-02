package com.srnyx.auserapp.commands

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.SlashOption
import io.github.freya022.botcommands.api.commands.application.slash.annotations.TopLevelSlashCommandData

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType

import java.net.URLEncoder


@Command
class GoogleItCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "googleit",
        description = "Generates a Google query link")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun googleIt(event: GlobalSlashEvent, @SlashOption(description = "What to Google for") query: String?) {
        event.reply("https://google.com" + (query?.let { "/search?q=" + URLEncoder.encode(it, "UTF-8") } ?: "")).queue()
    }
}
