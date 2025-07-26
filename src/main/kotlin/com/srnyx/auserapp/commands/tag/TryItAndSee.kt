package com.srnyx.auserapp.commands.tag

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand


@Command
class TryItAndSee: ApplicationCommand() {
    @JDASlashCommand(
        name = "tag",
        subcommand = "tryitandsee",
        description = "Sends the tryitands.ee website")
    // TopLevelSlashCommandData in ContactSupportCmd
    fun tryItAndSee(event: GlobalSlashEvent) {
        event.reply("https://tryitands.ee").queue()
    }
}