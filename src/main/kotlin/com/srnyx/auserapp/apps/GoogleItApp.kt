package com.srnyx.auserapp.apps

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType

import java.net.URLEncoder


@Command
class GoogleItApp: ApplicationCommand() {
    @JDAMessageCommand(
        name = "Google It",
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun googleIt(event: GlobalMessageEvent) {
        event.reply("https://google.com/search?q=" + URLEncoder.encode(event.target.contentDisplay, "UTF-8")).queue()
    }
}
