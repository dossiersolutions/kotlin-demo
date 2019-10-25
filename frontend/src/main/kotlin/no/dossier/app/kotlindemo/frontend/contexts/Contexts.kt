package no.dossier.app.kotlindemo.frontend.contexts

import no.dossier.app.kotlindemo.frontend.components.AppState
import react.createContext

val defaultState = object : AppState {
    override var connected: Boolean = false
    override var connections: MutableList<String> = mutableListOf()
}

val appContext = createContext(defaultState)