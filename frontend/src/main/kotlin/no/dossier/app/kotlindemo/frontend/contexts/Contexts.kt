package no.dossier.app.kotlindemo.frontend.contexts

import no.dossier.app.kotlindemo.domain.docker.DockerContainer
import no.dossier.app.kotlindemo.frontend.components.AppState
import react.createContext

val defaultState = object : AppState {
    override var connected: Boolean = false
    override var loading: Boolean = false
    override var connections: MutableList<String> = mutableListOf()
    override var dockerContainers: MutableList<DockerContainer> = mutableListOf()
}

val appContext = createContext(defaultState)