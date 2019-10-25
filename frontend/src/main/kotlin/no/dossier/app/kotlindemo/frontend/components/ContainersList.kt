package no.dossier.app.kotlindemo.frontend.components

import react.*
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import react.dom.*

class ContainersList : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        appContext.Consumer { state ->
            div (classes = "container-list-wrapper") {
                table {
                    thead {
                        th {
                            td {
                                + "Container id"
                            }
                            td {
                                + "Name"
                            }
                            td {
                                + "Status"
                            }
                            td {
                                + "Image"
                            }
                            td {
                                + "Ports"
                            }
                            td {
                                + "Created at"
                            }
                            td {
                                + "Actions"
                            }
                        }
                    }
                    tbody {
                        state.connections.forEach {
                            tr {
                                td {
                                    + "Container id - $it"
                                }
                                td {
                                    + "Name - $it"
                                }
                                td {
                                    + "Status - $it"
                                }
                                td {
                                    + "Image - $it"
                                }
                                td {
                                    + "Ports - $it"
                                }
                                td {
                                    + "Created at - $it"
                                }
                                td {
                                    + "Actions - $it"
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
