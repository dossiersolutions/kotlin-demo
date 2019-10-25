package no.dossier.app.kotlindemo.frontend.contexts

import no.dossier.app.kotlindemo.frontend.components.AppState
import no.dossier.app.kotlindemo.domain.User
import react.createContext

val defaultState = object : AppState {
    override var sendChatMessage: (String) -> Unit = { }
    override var fetchedUser: User? = null
    override var connected: Boolean = false
    override var loading: Boolean = false
    override var connections: MutableList<String> = mutableListOf()
    override var chatContent: String = ""
}

val appContext = createContext(defaultState)