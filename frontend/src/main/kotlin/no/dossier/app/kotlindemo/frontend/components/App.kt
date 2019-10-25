package no.dossier.app.kotlindemo.frontend.components

import kotlinx.serialization.ImplicitReflectionSerializer
import react.*
import react.dom.*
import kotlin.browser.window
import kotlinx.serialization.json.*
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import no.dossier.app.kotlindemo.api.*
import no.dossier.app.kotlindemo.config.AppConfig
import no.dossier.app.kotlindemo.domain.bitbucket.BitBucketBranch
import no.dossier.app.kotlindemo.domain.docker.DockerContainer
import no.dossier.app.kotlindemo.frontend.components.stylesheet.AppStyles
import no.dossier.app.kotlindemo.frontend.constainsts.Pages
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import no.dossier.app.kotlindemo.frontend.wrappers.*
import styled.css
import styled.styledDiv

interface AppState: RState {
    //variables
    var loading: Boolean
    var page: Pages

    //actions
    var connected: Boolean
    var connections: MutableList<String>
    var dockerContainers: MutableList<DockerContainer>
    var bitBucketBranches: MutableList<BitBucketBranch>
    var setPage: (Pages) -> Unit
}

class App : RComponent<RProps, AppState>() {

    private val websocketEndpoint: String = ("http://" + window.location.host.split(':')[0] + ":"
            + AppConfig.ServerPort + AppConfig.WebSocketEndpoint)
    private var stompClient: Stomp = connect()

    init {
        state.page = Pages.dockerContainersPage
        state.loading = false
        state.connections = mutableListOf()
        state.dockerContainers = mutableListOf()
        state.bitBucketBranches = mutableListOf()

        state.setPage = {
            setState{
                page = it
            }
        }
    }

    @ImplicitReflectionSerializer
    override fun componentDidMount() {
        fetchData()
    }

    @ImplicitReflectionSerializer
    override fun componentDidUpdate(prevProps: RProps, prevState: AppState, snapshot: Any) {
        if (prevState.page !== state.page) {
            fetchData();
        }
    }

    private fun fetchData() {
        setState{
            loading = true
        }
        if (state.page === Pages.dockerContainersPage && state.dockerContainers.isEmpty()) {
            window.fetch(RestEndpoint.GetAllDockerContainers.value).then {
                it.text()
            }.then {
                setState {
                    dockerContainers = Json.parse(DockerContainer::class.serializer().list, it).toMutableList()
                    loading = false
                }
            }
        }
        if (state.page === Pages.bitbuckerBranchesPage && state.bitBucketBranches.isEmpty()) {
            window.fetch(RestEndpoint.GetAllBitBucketBranches.value).then {
                it.text()
            }.then {
                setState {
                    bitBucketBranches = Json.parse(BitBucketBranch::class.serializer().list, it).toMutableList()
                    loading = false
                }
            }
        }
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
            if (state.loading) {
                spinner()
            }
            div(classes = "container") {
                styledDiv {
                    css {
                        AppStyles.main
                    }
                    header()
                    menu()
                    containersList()
                    footer()
                }
            }

        }
    }
}

fun RBuilder.app() = child(App::class) {}
