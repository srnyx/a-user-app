package com.srnyx.auserapp.commands

import dev.freya02.botcommands.jda.ktx.components.into

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.TopLevelSlashCommandData
import io.github.freya022.botcommands.api.components.Buttons

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType

import xyz.srnyx.lazylibrary.LazyEmoji


@Command
class InstallCmd(private val buttons: Buttons): ApplicationCommand() {
    @JDASlashCommand(
        name = "install",
        description = "Get a link to install the app")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun embed(event: GlobalSlashEvent) {
        event.reply(LazyEmoji.YES.toString() + " Click the button below to install **A User App**")
            .setComponents(buttons.link("https://discord.com/oauth2/authorize?client_id=1298765381296717845", "Install A User App").into())
            .queue()
    }
}
