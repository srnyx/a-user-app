package com.srnyx.auserapp.commands

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.TopLevelSlashCommandData

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType


@Command
class DontAskToAskCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "dontasktoask",
        description = "Sends the dontasktoask.com website")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun dontAskToAsk(event: GlobalSlashEvent) {
        event.reply("https://dontasktoask.com").queue()
    }
}
