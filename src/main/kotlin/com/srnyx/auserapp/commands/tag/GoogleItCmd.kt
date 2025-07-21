package com.srnyx.auserapp.commands.tag

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.SlashOption

import java.net.URLEncoder


@Command
class GoogleItCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "tag",
        subcommand = "googleit",
        description = "Generates a Google query link")
    // TopLevelSlashCommandData in ContactSupportCmd
    fun googleIt(event: GlobalSlashEvent, @SlashOption(description = "What to Google for") query: String?) {
        event.reply("https://google.com" + (query?.let { "/search?q=" + URLEncoder.encode(it, "UTF-8") } ?: "")).queue()
    }
}
