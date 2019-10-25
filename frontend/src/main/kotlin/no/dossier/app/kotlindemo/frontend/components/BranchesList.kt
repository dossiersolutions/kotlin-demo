package no.dossier.app.kotlindemo.frontend.components

import no.dossier.app.kotlindemo.frontend.components.stylesheet.ContainersListStyles
import react.*
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import react.dom.*
import styled.css
import styled.styledDiv

class BranchesList : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        appContext.Consumer { state ->
            styledDiv {
                css {
                    + ContainersListStyles.wrapper
                }
                table(classes = "table") {
                    thead {
                        tr {
                            th {
                                + "Branch name"
                            }
                            th {
                                + "Url"
                            }
                        }
                    }
                    tbody {
                        state.bitBucketBranches.forEach {
                            tr {
                                td {
                                    it.name
                                }
                                td {
                                    it.url
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.branchesList() = child(BranchesList::class) {}
