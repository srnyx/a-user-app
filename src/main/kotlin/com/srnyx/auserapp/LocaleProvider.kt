package com.srnyx.auserapp

import io.github.freya022.botcommands.api.core.service.annotations.BService
import io.github.freya022.botcommands.api.localization.interaction.GuildLocaleProvider
import io.github.freya022.botcommands.api.localization.interaction.UserLocaleProvider
import io.github.freya022.botcommands.api.localization.text.TextCommandLocaleProvider

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

import net.dv8tion.jda.api.interactions.DiscordLocale
import net.dv8tion.jda.api.interactions.Interaction

import java.util.*


@BService
class LocaleProvider: UserLocaleProvider, GuildLocaleProvider, TextCommandLocaleProvider {
    override fun getLocale(interaction: Interaction): Locale {
        return Locale.ENGLISH
    }

    override fun getDiscordLocale(interaction: Interaction): DiscordLocale {
        return DiscordLocale.ENGLISH_US
    }

    override fun getDiscordLocale(event: MessageReceivedEvent): DiscordLocale {
        return DiscordLocale.ENGLISH_US
    }
}
