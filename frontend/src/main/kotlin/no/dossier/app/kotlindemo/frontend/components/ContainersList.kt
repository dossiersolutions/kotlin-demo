package no.dossier.app.kotlindemo.frontend.components

import com.soywiz.klock.DateTime
import no.dossier.app.kotlindemo.frontend.components.stylesheet.ContainersListStyles
import react.*
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import react.dom.*
import styled.css
import styled.styledDiv

class ContainersList : RComponent<RProps, RState>() {
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
                            th {
                                + "Ports"
                            }
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
                                    + "Name"
                                }
                                td {
                                    it.status.toString()
                                }
                                td {
                                    + "Ports"
                                }
                                td {
                                    + DateTime(it.created).toString()
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
