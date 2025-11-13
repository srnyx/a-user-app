package com.srnyx.auserapp.apps

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.ApplicationCommand
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.components.actionrow.ActionRow
import net.dv8tion.jda.api.components.buttons.Button
import net.dv8tion.jda.api.components.textdisplay.TextDisplay
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType

import okhttp3.OkHttpClient

import xyz.srnyx.lazylibrary.LazyEmoji
import xyz.srnyx.lazylibrary.utility.LazyUtilities

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI
import java.net.URLConnection
import java.util.logging.Level
import java.util.logging.Logger
import java.util.stream.Collectors


@Command
class PasteApp: ApplicationCommand() {
    companion object {
        const val PASTE_URL: String = "paste.venox.network"
    }

    init {
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.FINE
    }

    @JDAMessageCommand(
        name = "Upload paste",
        contexts = [InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM],
        integrationTypes = [IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL])
    fun pasteContext(event: GlobalMessageEvent) {
        if (event.channelId == null) return
        if (!LazyUtilities.userHasChannelPermission(event, Permission.MESSAGE_SEND)) {
            event.reply(LazyEmoji.NO.toString() + " You do not have permission to use this command in this channel!").setEphemeral(true).queue()
            return
        }

        // Get the message's text (attachment/message content) and extension
        val message = event.target
        var text: String = message.contentRaw
        var name = "**text**"
        var extension = ""
        val attachments: List<Message.Attachment> = message.attachments
        if (attachments.isNotEmpty()) {
            val attachment = attachments[0]
            if (!attachment.isImage && !attachment.isVideo) {
                try {
                    text = readStream(attachment.proxy.download().get())
                } catch (e: Exception) {
                    e.printStackTrace()
                    event.reply(LazyEmoji.NO.toString() + " Failed to read attachment!").setEphemeral(true).queue()
                    return
                }
                val fileName: String = attachment.fileName
                name = "file **${fileName}**"
                extension = "." + fileName.substring(fileName.lastIndexOf('.') + 1)
            }
        }

        // No text to upload
        if (text.trim().isBlank()) {
            event.reply(LazyEmoji.NO.toString() + " No text to upload!").setEphemeral(true).queue()
            return
        }

        // Establish connection
        val connection: URLConnection
        try {
            connection = URI.create("https://${PASTE_URL}/documents").toURL().openConnection()
        } catch (e: IOException) {
            event.reply(LazyEmoji.NO.toString() + " Failed to upload paste!").setEphemeral(true).queue()
            e.printStackTrace()
            return
        }
        connection.setRequestProperty("authority", PASTE_URL)
        connection.setRequestProperty("accept", "application/json, text/javascript, /; q=0.01")
        connection.setRequestProperty("x-requested-with", "XMLHttpRequest")
        connection.setRequestProperty("user-agent", event.user.asTag + " via A User App")
        connection.setRequestProperty("content-type", "application/json; charset=UTF-8")
        connection.doOutput = true

        // Write to connection
        try {
            connection.getOutputStream().use { stream ->
                stream.write(text.toByteArray())
                stream.flush()
            }
        } catch (e: Exception) {
            event.reply(LazyEmoji.NO.toString() + " Failed to upload paste!").setEphemeral(true).queue()
            e.printStackTrace()
            return
        }

        // Reply with paste link
        try {
            val id: String = readStream(connection.getInputStream()).split("\"".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3]
            val url = "https://$PASTE_URL/$id$extension"
            event.replyComponents(
                TextDisplay.of(LazyEmoji.YES.toString() + " Successfully uploaded " + message.author.asMention + "'s " + name + " to " + url),
                ActionRow.of(
                    Button.link(url, "Open paste"),
                    Button.link(message.jumpUrl, "Go to source message")))
                .setAllowedMentions(LazyUtilities.NO_MENTIONS)
                .useComponentsV2()
                .queue()
        } catch (e: IOException) {
            event.reply(LazyEmoji.NO.toString() + " Failed to upload paste!").setEphemeral(true).queue()
            e.printStackTrace()
        }
    }

    private fun readStream(stream: InputStream): String {
        return BufferedReader(InputStreamReader(stream)).lines().collect(Collectors.joining("\n"))
    }
}
