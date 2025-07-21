package com.srnyx.auserapp.apps

import com.srnyx.auserapp.commands.MockCmd

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent

import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.exceptions.ErrorHandler
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException
import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.requests.ErrorResponse

import xyz.srnyx.lazylibrary.LazyEmoji
import xyz.srnyx.lazylibrary.utility.LazyUtilities


@Command
class MockApp: ApplicationCommand() {
    companion object {
        val IGNORE_AUTOMOD_MESSAGE_FILTER: ErrorHandler = ErrorHandler().ignore(
            ErrorResponse.MESSAGE_BLOCKED_BY_AUTOMOD,
            ErrorResponse.MESSAGE_BLOCKED_BY_HARMFUL_LINK_FILTER)
    }

    @JDAMessageCommand(
        name = "Mock",
        contexts = [net.dv8tion.jda.api.interactions.InteractionContextType.GUILD, net.dv8tion.jda.api.interactions.InteractionContextType.PRIVATE_CHANNEL, net.dv8tion.jda.api.interactions.InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun mockApp(event: GlobalMessageEvent) {
        val content = event.target.contentRaw
        if (content.trim().isBlank()) {
            event.reply(LazyEmoji.NO.toString() + " No text to mock!").setEphemeral(true).queue()
            return
        }

        // Check if user has permission to send messages in channel
        val member = event.member
        val channel = event.channel
        if (member != null && channel is GuildMessageChannel && !channel.canTalk(member)) {
            event.reply(LazyEmoji.NO.toString() + " You don't have permission to chat here!").setEphemeral(true).queue()
            return
        }

        try {
            event.reply(MockCmd.mockify(content))
                .mentionRepliedUser(false)
                .setAllowedMentions(LazyUtilities.NO_MENTIONS)
                .queue(null, IGNORE_AUTOMOD_MESSAGE_FILTER)
        } catch (e: InsufficientPermissionException) {
            event.reply(LazyEmoji.NO.toString() + " I don't have permission to send messages in <#" + event.channelId + ">!").queue();
        }
    }
}
