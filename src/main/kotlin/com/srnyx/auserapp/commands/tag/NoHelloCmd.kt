package com.srnyx.auserapp.commands.tag

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.SlashOption


@Command
class NoHelloCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "tag",
        subcommand = "nohello",
        description = "Sends the nohello.net website")
    // TopLevelSlashCommandData in ContactSupportCmd
    fun noHello(event: GlobalSlashEvent, @SlashOption language: String?) {
        event.reply("https://nohello.net/${language ?: "en"}").queue()
    }
}
