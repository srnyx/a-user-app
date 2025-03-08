package com.srnyx.auserapp.apps

import com.srnyx.auserapp.commands.playhosting.PlayHostingSupportCmd

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType


@Command
class PlayHostingSupportApp: ApplicationCommand() {
    @JDAMessageCommand(
        name = "Play Hosting Support",
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun lilypadPlayHostingApp(event: GlobalMessageEvent) {
        event.replyEmbeds(PlayHostingSupportCmd.getEmbed()).queue()
    }
}
