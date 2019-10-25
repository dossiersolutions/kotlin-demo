package no.dossier.app.kotlindemo.frontend.components

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import kotlinx.html.js.onClickFunction
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import no.dossier.app.kotlindemo.api.RestEndpoint
import no.dossier.app.kotlindemo.domain.docker.DockerContainer
import no.dossier.app.kotlindemo.frontend.components.stylesheet.ContainersListStyles
import react.*
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import react.dom.*
import styled.css
import styled.styledDiv
import kotlin.browser.window

class ContainersList : RComponent<RProps, RState>() {

    private fun stopDockerContainer() {

    }

    override fun RBuilder.render() {
        appContext.Consumer { state ->
            styledDiv {
                css {
                    +ContainersListStyles.wrapper
                }
                table(classes = "table") {
                    thead {
                        tr {
                            th {
                                +"Container id"
                            }
                            th {
                                +"Names"
                            }
                            th {
                                +"Status"
                            }
                            th {
                                +"Created at"
                            }
                            th {
                                +"Actions"
                            }
                        }
                    }
                    tbody {
                        state.dockerContainers.forEach {
                            tr {
                                td {
                                    +it.id
                                }
                                td {
                                    +it.name
                                }
                                td {
                                    +it.status.toString()
                                }
                                td {
                                    +DateTime(it.created).toString(DateFormat.FORMAT_DATE)
                                }
                                td {
                                    button(classes = "btn btn-danger") {
                                        +"Stop container"
                                        attrs {
                                            onClickFunction = {

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
