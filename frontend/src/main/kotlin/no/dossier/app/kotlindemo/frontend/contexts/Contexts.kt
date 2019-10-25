package no.dossier.app.kotlindemo.frontend.contexts

import no.dossier.app.kotlindemo.domain.bitbucket.BitBucketBranch
import no.dossier.app.kotlindemo.domain.docker.DockerContainer
import no.dossier.app.kotlindemo.frontend.components.AppState
import no.dossier.app.kotlindemo.frontend.constainsts.Pages
import react.createContext
import react.setState

val defaultState = object : AppState {
    override var page: Pages = Pages.dockerContainersPage
    override var connected: Boolean = false
    override var loading: Boolean = false
    override var reload: Boolean = false
    override var connections: MutableList<String> = mutableListOf()
    override var dockerContainers: MutableList<DockerContainer> = mutableListOf()
    override var bitBucketBranches: MutableList<BitBucketBranch> = mutableListOf()
    override var setPage: (Pages) -> Unit = {}
    override var startPipLine: (String) -> Unit = {}
}

val appContext = createContext(defaultState)