package com.srnyx.auserapp.commands.tag

import io.github.freya022.botcommands.api.commands.annotations.Command;
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand;
import io.github.freya022.botcommands.api.commands.application.slash.annotations.TopLevelSlashCommandData;

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.Interaction
import net.dv8tion.jda.api.interactions.InteractionContextType


@Command
class ContactSupportCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "tag",
        subcommand = "contactsupport",
        description = "Send a message telling the user to use the proper means to get support")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun contactSupport(event: GlobalSlashEvent) {
        event.replyEmbeds(getEmbed(event)).queue()
    }

    companion object {
        fun getEmbed(interaction: Interaction): MessageEmbed = EmbedBuilder()
            .setColor(0xFF0000)
            .setDescription(":wave: **Hey, looks like you're lost!**\nUnfortunately, my DMS / pinging me is not the way to get support\n\nInstead, look for official support channels (ticket/modmail system, community support, etc...) and ask there, thanks :heart:")
            .setFooter(interaction.user.name, interaction.user.effectiveAvatarUrl)
            .build()
    }
}
