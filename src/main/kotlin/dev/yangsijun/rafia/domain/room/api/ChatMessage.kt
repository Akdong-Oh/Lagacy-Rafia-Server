package dev.yangsijun.rafia.domain.room.api

import io.netty.channel.ChannelId

data class ChatMessage(
    var type: String,
    var sender: String,
    val channelId: String,
    val data: Any
) {
    fun newConnect() {
        this.type = "new"
    }

    fun closeConnect(sender: String) {
        this.type = "close"
    }
}