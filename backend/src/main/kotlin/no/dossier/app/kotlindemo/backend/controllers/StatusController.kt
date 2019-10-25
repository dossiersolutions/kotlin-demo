package no.dossier.app.kotlindemo.backend.controllers

import no.dossier.app.kotlindemo.api.Message
import no.dossier.app.kotlindemo.api.Topic
import no.dossier.app.kotlindemo.api.WsEndpoint
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class StatusController {

    @MessageMapping(WsEndpoint.Urls.SEND_CHAT_MESSAGE)
    @SendTo(Topic.Urls.CHAT)
    fun sendChatMessage(message: Message.ChatMessage): Message.ChatMessage {
        message.timeStamp = Date().toString()
        return message
    }

}