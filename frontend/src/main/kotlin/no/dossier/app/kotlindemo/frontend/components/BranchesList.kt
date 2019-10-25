package no.dossier.app.kotlindemo.frontend.components

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import kotlinx.html.js.onClickFunction
import no.dossier.app.kotlindemo.frontend.components.stylesheet.ContainersListStyles
import react.*
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import react.dom.*
import styled.css
import styled.styledDiv

class BranchesList : RComponent<RProps, RState>() {

    private fun startPipLine() {

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
                                + "Branch name"
                            }
                            th {
                                + "Url"
                            }
                            th {
                                + "Commit message"
                            }
                            th {
                                + "Commit date"
                            }
                            th {
                                + "Actions"
                            }
                        }
                    }
                    tbody {
                        state.bitBucketBranches.forEach {
                            tr {
                                td {
                                    + it.name
                                }
                                td {
                                    a {
                                        attrs {
                                            href = it.url
                                        }
                                        + it.url
                                    }
                                }
                                td {
                                    + it.lastCommitInfo.first
                                }
                                td {
                                    + DateTime(it.lastCommitInfo.second).toString(DateFormat.FORMAT_DATE)
                                }
                                button(classes = "btn btn-danger") {
                                    + "Start pipline"
                                    attrs {
                                        onClickFunction = {
                                            startPipLine()
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

fun RBuilder.branchesList() = child(BranchesList::class) {}
