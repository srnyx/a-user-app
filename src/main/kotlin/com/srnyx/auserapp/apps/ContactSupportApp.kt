package com.srnyx.auserapp.apps;

import com.srnyx.auserapp.commands.ContactSupportCmd

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType


@Command
class ContactSupportApp: ApplicationCommand() {
    @JDAMessageCommand(
        name = "Contact Support",
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun contactSupport(event: GlobalMessageEvent) {
        event.replyEmbeds(ContactSupportCmd.getEmbed()).queue()
    }
}
