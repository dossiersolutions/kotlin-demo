package no.dossier.app.kotlindemo.api

import kotlinx.serialization.Serializable
import no.dossier.app.kotlindemo.domain.GameState

enum class EventType {
    NewConnection,
    ConnectionClosed,
}

@Serializable
sealed class Message {
    val messageType = this::class.simpleName

    @Serializable class ConnectionUpdated(val connectionId: String, val eventType: EventType) : Message()
    @Serializable class ChatMessage(val message: String, var timeStamp: String? = null) : Message()
    @Serializable class GameStateUpdated(val gameState: GameState) : Message()
}
