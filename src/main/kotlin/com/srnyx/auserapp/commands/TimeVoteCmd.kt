package com.srnyx.auserapp.commands

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.slash.GlobalSlashEvent
import io.github.freya022.botcommands.api.commands.application.slash.annotations.JDASlashCommand
import io.github.freya022.botcommands.api.commands.application.slash.annotations.SlashOption
import io.github.freya022.botcommands.api.commands.application.slash.annotations.TopLevelSlashCommandData

import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType

import xyz.srnyx.lazylibrary.LazyEmoji


@Command
class TimeVoteCmd : ApplicationCommand() {
    companion object {
        private const val LETTERS = "abcdefghijklmnopqrstuvwxyz"
    }

    @JDASlashCommand(
        name = "timevote",
        description = "Generate a time vote message to send")
    @TopLevelSlashCommandData(
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun timeVoteCommand(event: GlobalSlashEvent,
                        @SlashOption(description = "The ending time (in seconds) of the vote") end: Long,
                        @SlashOption(description = "The starting time (in seconds) of the vote") start: Long?,
                        @SlashOption(description = "The interval (in seconds) between each option") interval: Long?) {
        // Defer
        event.deferReply(true).queue()

        var newStart = start
        var newInterval = interval
        if (newStart == null) newStart = System.currentTimeMillis() / 1000
        if (newInterval == null) newInterval = 3600 // 1 hour

        // Check if start is greater than end
        if (newStart > end) {
            event.hook.editOriginal(LazyEmoji.NO.toString() + " The start time must be less than the end time!").queue()
            return
        }

        // Check if interval is greater than end
        if (newInterval > end) {
            event.hook.editOriginal(LazyEmoji.NO.toString() + " The interval must be less than the end time!").queue()
            return
        }

        // Check if too many options are generated
        val options = ((end - newStart) / newInterval).toInt()
        if (options > 26) {
            event.hook.editOriginal(LazyEmoji.NO.toString() + " The interval is too small!").queue()
            return
        }

        // Build reply
        val builder = StringBuilder("```")
        var iChar = 0
        var i: Long = newStart
        while (i <= end) {
            builder.append("\n:regional_indicator_").append(LETTERS[iChar]).append(": <t:").append(i).append(":f>")
            i += newInterval
            iChar++
        }
        builder.append("\n```")

        // Send reply
        event.hook.editOriginal(builder.toString()).queue()
    }
}