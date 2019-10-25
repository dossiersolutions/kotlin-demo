package no.dossier.app.kotlindemo.frontend.components

import kotlinx.html.js.onClickFunction
import no.dossier.app.kotlindemo.frontend.components.stylesheet.ContainersListStyles
import react.*
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import react.dom.*
import styled.css
import styled.styledDiv

class ContainersList : RComponent<RProps, RState>() {

    private fun stopDockerContainer() {

    }

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
                                + "Container id"
                            }
                            th {
                                + "Names"
                            }
                            th {
                                + "Status"
                            }
//                            th {
//                                + "Ports"
//                            }
                            th {
                                + "Created at"
                            }
                            th {
                                + "Actions"
                            }
                        }
                    }
                    tbody {
                        state.dockerContainers.forEach {
                            tr {
                                td {
                                    it.id
                                }
                                td {
                                    it.name
                                }
                                td {
                                    it.status.toString()
                                }
//                                td {
//                                    + "Ports"
//                                }
                                td {
                                    + it.created.toString()
                                }
                                td {
                                    button(classes = "btn btn-dang") {
                                        + "Stop container"
                                        attrs {
                                            onClickFunction = {
                                                stopDockerContainer()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.containersList() = child(ContainersList::class) {}
