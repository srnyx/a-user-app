package com.srnyx.auserapp.commands.tag

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand


@Command
class DontAskToAskCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "tag",
        subcommand = "dontasktoask",
        description = "Sends the dontasktoask.com website")
    // TopLevelSlashCommandData in ContactSupportCmd
    fun dontAskToAsk(event: GlobalSlashEvent) {
        event.reply("https://dontasktoask.com").queue()
    }
}
