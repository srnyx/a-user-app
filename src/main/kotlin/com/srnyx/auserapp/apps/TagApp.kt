package com.srnyx.auserapp.apps

import com.srnyx.auserapp.commands.tag.ContactSupportCmd
import com.srnyx.auserapp.commands.tag.PlayHostingCmd

import dev.minn.jda.ktx.messages.into

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent
import io.github.freya022.botcommands.api.components.SelectMenus
import io.github.freya022.botcommands.api.components.data.InteractionConstraints

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType
import net.dv8tion.jda.api.interactions.components.selections.SelectOption

import xyz.srnyx.lazylibrary.LazyEmoji

import java.net.URLEncoder


@Command
class TagApp(private val menus: SelectMenus): ApplicationCommand() {
    @JDAMessageCommand(
        name = "Tag menu",
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    suspend fun tagMenu(event: GlobalMessageEvent) {
        event.replyComponents(menus.stringSelectMenu().ephemeral {
            singleUse = true
            constraints(InteractionConstraints.ofUsers(event.user))
            options += listOf(
                SelectOption.of("No Hello", "hello")
                    .withEmoji(Emoji.fromUnicode("👋"))
                    .withDescription("nohello.net"),
                SelectOption.of("Contact Support", "support")
                    .withEmoji(Emoji.fromUnicode("🆘"))
                    .withDescription("Tel the user to use the proper means to get support"),
                SelectOption.of("Don't Ask to Ask", "ask")
                    .withEmoji(Emoji.fromUnicode("❔"))
                    .withDescription("dontasktoask.com"),
                SelectOption.of("Google It", "google")
                    .withEmoji(Emoji.fromUnicode("🔍"))
                    .withDescription("google.com"),
                SelectOption.of("How to Ask for Help", "howtoask")
                    .withEmoji(Emoji.fromUnicode("❓"))
                    .withDescription("How to ask for help in a proper way"),
                SelectOption.of("Play Hosting Support", "playhosting")
                    .withEmoji(Emoji.fromUnicode("☁️"))
                    .withDescription("Tell the user to ask for help in Play Hosting"))

            bindTo { selectEvent ->
                val hook = event.hook
                val selection = selectEvent.selectedOptions[0].value
                when (selection) {
                    "hello" -> hook.editOriginal("https://nohello.net/en")
                    "support" -> hook.editOriginalEmbeds(ContactSupportCmd.getEmbed(event))
                    "ask" -> hook.editOriginal("https://dontasktoask.com")
                    "google" -> hook.editOriginal("https://google.com/search?q=" + URLEncoder.encode(event.target.contentDisplay, "UTF-8"))
                    "howtoask" -> hook.editOriginal(":wave: **Hi!** Please take a quick moment to read this guide:\nhttps://blog.jonathanchun.com/2025/02/17/the-art-of-asking-for-help")
                    "playhosting" -> hook.editOriginalEmbeds(PlayHostingCmd.getEmbed())
                    else -> hook.editOriginal(LazyEmoji.NO.toString() + " Unknown tag selected: `" + selection + "`!")
                }.setComponents().queue()
            }
        }.into()).queue()
    }
}
