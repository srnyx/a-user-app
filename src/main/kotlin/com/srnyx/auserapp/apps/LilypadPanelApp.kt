package com.srnyx.auserapp.apps

import com.srnyx.auserapp.commands.LilypadPanelCmd

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType


@Command
class LilypadPanelApp: ApplicationCommand() {
    @JDAMessageCommand(
        name = "Lilypad Panel",
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun lilypadPanel(event: GlobalMessageEvent) {
        event.replyEmbeds(LilypadPanelCmd.getEmbed()).queue()
    }
}
