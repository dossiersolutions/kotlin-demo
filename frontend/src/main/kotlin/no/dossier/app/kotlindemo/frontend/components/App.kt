package no.dossier.app.kotlindemo.frontend.components

import kotlinx.serialization.ImplicitReflectionSerializer
import no.dossier.app.kotlindemo.domain.User
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
    var fetchedUser: User?
    var connected: Boolean
    var connections: MutableList<String>
    var chatContent: String
    var loading: Boolean

    //actions
    var sendChatMessage: (String) -> Unit
}

class App : RComponent<RProps, AppState>() {

    private val websocketEndpoint: String = ("http://" + window.location.host.split(':')[0] + ":"
            + AppConfig.ServerPort + AppConfig.WebSocketEndpoint)
    private var stompClient: Stomp = connect()

    init {
        state.loading = true
        state.connections = mutableListOf()
        state.sendChatMessage = ::handleSendMessage
        state.chatContent = ""
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

    override fun componentDidMount() {
        window.fetch(RestEndpoint.GetUser.value.replace("{userId}", 1.toString())).then {
            it.text()
        }.then {
            setState {
                fetchedUser = Json.nonstrict.parse(User.serializer(), it)
            }
        }
    }

    @ImplicitReflectionSerializer
    override fun componentDidUpdate(prevProps: RProps, prevState: AppState, snapshot: Any) {
        if (!prevState.connected && state.connected) {
            stompClient.subscribe(Topic.UserInfo.value) { data ->
                setState {
                    val message = Json.parse(Message.ConnectionUpdated.serializer(), data.body)
                    when (message.eventType) {
                        EventType.NewConnection -> connections.add(message.connectionId)
                        EventType.ConnectionClosed -> connections.remove(message.connectionId)
                    }
                }
            }

            stompClient.subscribe(Topic.Chat.value) { data ->
                setState {
                    val message = Json.parse(Message.ChatMessage.serializer(), data.body)
                    chatContent += "${message.timeStamp}: ${message.message}\n"
                }
            }

            window.fetch(RestEndpoint.GetAllConnections.value).then {
                it.text()
            }.then {
                setState {
                    connections = Json.parse(String::class.serializer().list, it).toMutableList()
                }
            }
        }
    }

    override fun RBuilder.render() {
        appContext.Provider(state) {
            if (state.loading) {
                spinner()
            }
            div(classes = "container") {
                header()
                containersList()
                footer()
            }

        }
    }
}

fun RBuilder.app() = child(App::class) {}
