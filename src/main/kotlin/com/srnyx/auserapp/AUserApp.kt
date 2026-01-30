package com.srnyx.auserapp

import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag

import okhttp3.OkHttpClient

import xyz.srnyx.lazylibrary.LazyLibrary

import java.util.logging.Level
import java.util.logging.Logger


object AUserApp {
    init {
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.FINE
    }

    @JvmStatic
    fun main(args: Array<out String>) {
        LazyLibrary.INSTANCE
            .gatewayIntents(GatewayIntent.MESSAGE_CONTENT)
            .jdaBuilder { builder -> builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.SCHEDULED_EVENTS) }
            .activities(Activity.watching("srnyx.com"))
            .startBot(this.javaClass)
    }
}
