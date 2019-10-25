package no.dossier.app.kotlindemo.frontend.components

import kotlinx.serialization.ImplicitReflectionSerializer
import react.*
import react.dom.*
import kotlin.browser.window
import kotlinx.serialization.json.*
import kotlinx.serialization.list
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
    var reload: Boolean

    //actions
    var connected: Boolean
    var connections: MutableList<String>
    var dockerContainers: MutableList<DockerContainer>
    var bitBucketBranches: MutableList<BitBucketBranch>
    var setPage: (Pages) -> Unit
    var startPipLine: (String) -> Unit
}

class App : RComponent<RProps, AppState>() {

    private val websocketEndpoint: String = ("http://" + window.location.host.split(':')[0] + ":"
            + AppConfig.ServerPort + AppConfig.WebSocketEndpoint)
    private var stompClient: Stomp = connect()

    init {
        state.page = Pages.dockerContainersPage
        state.loading = false
        state.reload = false
        state.connections = mutableListOf()
        state.dockerContainers = mutableListOf()
        state.bitBucketBranches = mutableListOf()

        state.setPage = {
            setState{
                page = it
            }
        }

        state.startPipLine = {
            window.fetch(RestEndpoint.StartBitBucketPipeline.value + "?branchName=${it}").then {
                setState {
                    reload = true
                }
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
        if (state.page === Pages.dockerContainersPage) {
            window.fetch(RestEndpoint.GetAllDockerContainers.value).then {
                it.text()
            }.then {
                setState {
                    dockerContainers = Json.parse(DockerContainer.serializer().list, it).toMutableList()
                    console.log(dockerContainers)
                    loading = false
                }
            }
        } else if (state.page === Pages.bitbuckerBranchesPage) {
            window.fetch(RestEndpoint.GetAllBitBucketBranches.value).then {
                it.text()
            }.then {
                setState {
                    bitBucketBranches = Json.parse(BitBucketBranch.serializer().list, it).toMutableList()
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
            styledDiv {
                css {
                    + AppStyles.mainContainer
                }
                styledDiv {
                    css {
                        AppStyles.main
                    }
                    header()
                    menu()
                    if (state.page === Pages.dockerContainersPage) {
                        containersList()
                    } else {
                        branchesList()
                    }
                    footer()
                }
            }

        }
    }
}

fun RBuilder.app() = child(App::class) {}
