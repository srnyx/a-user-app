package com.srnyx.auserapp.commands.tag

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand


@Command
class HowToAskForHelpCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "tag",
        subcommand = "howtoaskforhelp",
        description = "Sends the How to Ask for Help website")
    // TopLevelSlashCommandData in ContactSupportCmd
    fun dontAskToAsk(event: GlobalSlashEvent) {
        event.reply(":wave: **Hi!** Please take a quick moment to read this guide:\nhttps://blog.jonathanchun.com/2025/02/17/the-art-of-asking-for-help").queue()
    }
}
