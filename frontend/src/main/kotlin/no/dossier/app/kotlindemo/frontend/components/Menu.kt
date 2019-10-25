package no.dossier.app.kotlindemo.frontend.components

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import no.dossier.app.kotlindemo.frontend.constainsts.Pages
import no.dossier.app.kotlindemo.frontend.contexts.appContext

class Menu : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        appContext.Consumer { state ->
            var dockerPageClasses = "nav-item"
            var bitBucketClasses = "nav-item"
            if (state.page == Pages.dockerContainersPage) {
                dockerPageClasses += " active"
            } else {
                bitBucketClasses += " active"
            }
            nav (classes = "navbar navbar-expand-sm bg-dark navbar-dark") {
                ul(classes = "navbar-nav") {
                    li(classes = dockerPageClasses) {
                        a(classes = "nav-link") {
                            + "Docker containers"
                            attrs {
                                href = "#"
                                onClickFunction = {
                                    state.setPage(Pages.dockerContainersPage)
                                }
                            }
                        }
                    }
                    li(classes = bitBucketClasses) {
                        a(classes = "nav-link") {
                            + "Branches"
                            attrs {
                                href = "#"
                                onClickFunction = {
                                    state.setPage(Pages.bitbuckerBranchesPage)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.menu() = child(Menu::class) {}
