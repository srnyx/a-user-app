package com.srnyx.auserapp.apps

import dev.freya02.botcommands.jda.ktx.components.TextDisplay

import io.github.freya022.botcommands.api.commands.annotations.Command
import io.github.freya022.botcommands.api.commands.application.context.annotations.JDAMessageCommand
import io.github.freya022.botcommands.api.commands.application.context.message.GlobalMessageEvent

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.components.actionrow.ActionRow
import net.dv8tion.jda.api.components.buttons.Button
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.IntegrationType
import net.dv8tion.jda.api.interactions.InteractionContextType

import xyz.srnyx.lazylibrary.LazyEmoji
import xyz.srnyx.lazylibrary.utility.LazyUtilities

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI
import java.net.URLConnection
import java.util.stream.Collectors
import java.util.zip.GZIPInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


@Command
class PasteApp {
    companion object {
        const val PASTE_URL: String = "paste.venox.network"
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
                val inputStream: InputStream = attachment.proxy.download().get()
                if (attachment.fileExtension != null) extension = "." + attachment.fileExtension?.lowercase()

                if (extension == ".zip") {
                    // Extract and read if .zip
                    try {
                        ZipInputStream(inputStream).use { zipStream ->
                            // Get first entry
                            val entry: ZipEntry? = zipStream.nextEntry
                            if (entry == null) {
                                event.reply(LazyEmoji.NO.toString() + " Archive is empty!").setEphemeral(true).queue()
                                return
                            }

                            // Read extracted file
                            text = readStream(zipStream)

                            // Get name and extension of extracted file if possible
                            val entryName = entry.name
                            name = "extracted **$entryName**"
                            if (entryName != null && entryName.contains('.')) {
                                extension = "." + entryName.substringAfterLast('.').lowercase()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        event.reply(LazyEmoji.NO.toString() + " Failed to extract `.zip` archive!").setEphemeral(true).queue()
                        return
                    }
                } else if (extension == ".gz") {
                    // Extract and read if .gz
                    try {
                        // Read extracted file
                        text = GZIPInputStream(inputStream)
                            .bufferedReader()
                            .use { it.readText() }

                        // Get name and extension of extracted file if possible
                        val noGz = attachment.fileName.removeSuffix(".gz")
                        name = "extracted **$noGz**"
                        if (noGz.contains('.')) extension = "." + noGz.substringAfterLast('.').lowercase()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        event.reply(LazyEmoji.NO.toString() + " Failed to extract `.gz` archive!").setEphemeral(true).queue()
                        return
                    }
                } else {
                    // Read normal attachment
                    try {
                        text = readStream(inputStream)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        event.reply(LazyEmoji.NO.toString() + " Failed to read attachment!").setEphemeral(true).queue()
                        return
                    }
                    name = "file **${attachment.fileName}**"
                    if (attachment.fileExtension != null) extension = "." + attachment.fileExtension?.lowercase()
                }
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
                TextDisplay(LazyEmoji.YES.toString() + " Successfully uploaded " + message.author.asMention + "'s " + name + " to " + url),
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
