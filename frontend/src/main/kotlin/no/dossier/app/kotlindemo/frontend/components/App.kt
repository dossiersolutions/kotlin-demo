package no.dossier.app.kotlindemo.frontend.components

import kotlinx.html.style
import kotlinx.serialization.ImplicitReflectionSerializer
import react.*
import react.dom.*
import kotlin.browser.window
import kotlinx.serialization.json.*
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import no.dossier.app.kotlindemo.api.*
import no.dossier.app.kotlindemo.config.AppConfig
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import no.dossier.app.kotlindemo.frontend.wrappers.*

interface AppState: RState {
    //variables

    //actions
    var connected: Boolean
    var connections: MutableList<String>
}

class App : RComponent<RProps, AppState>() {

    private val websocketEndpoint: String = ("http://" + window.location.host.split(':')[0] + ":"
            + AppConfig.ServerPort + AppConfig.WebSocketEndpoint)
    private var stompClient: Stomp = connect()

    init {
        state.connections = mutableListOf()
    }

    private fun handleSendMessage(message: String) {
        stompClient.send(WsEndpoint.SendChatMessage.prefixedUrl,
                getDefaultSendOptions(), Json.stringify(Message.ChatMessage.serializer(),Message.ChatMessage(message)))
    }

    private fun connect(): Stomp {
        val socket = SockJS(websocketEndpoint)
        val stompClient = Stomp.over(socket)
        stompClient.connect(StompConfig()) {
            setState {
                connected = true
            }
        }
        return stompClient
    }

    private fun disconnect() {
        stompClient.disconnect()
        setState{
            connected = false
        }
    }

    override fun RBuilder.render() {
        appContext.Provider(state) {
            h1 {
                +"Kotlin demo"
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
