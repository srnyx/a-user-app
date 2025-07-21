package com.srnyx.auserapp.commands

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.SlashOption
import io.github.freya022.botcommands.api.commands.application.slash.annotations.TopLevelSlashCommandData

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType

import xyz.srnyx.lazylibrary.utility.LazyUtilities


@Command
class MockCmd: ApplicationCommand() {
    @JDASlashCommand(
        name = "mock",
        description = "Convert normal text to mock text (randomly capitalize)")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun mockCmd(event: GlobalSlashEvent,
                @SlashOption(description = "The text to mockify") text: String) {
        event.reply(mockify(text)).setEphemeral(true).queue()
    }

    companion object {
        fun mockify(text: String): String = text
            .map { c -> if (LazyUtilities.RANDOM.nextBoolean()) c.uppercaseChar() else c.lowercaseChar() }
            .joinToString("")
    }
}
