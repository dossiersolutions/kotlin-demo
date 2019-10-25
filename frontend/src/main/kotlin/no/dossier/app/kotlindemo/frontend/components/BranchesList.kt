package no.dossier.app.kotlindemo.frontend.components

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import kotlinext.js.js
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
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
                            th {
                                + "Branch link"
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
                        state.bitBucketBranches.forEach { branch ->
                            tr {
                                td {
                                    + branch.name
                                    attrs.style = js {
                                        wordBreak = "break-all"
                                        minWidth = "200px"
                                    }
                                }
                                td {
                                    a {
                                        attrs {
                                            href = branch.url
                                        }
                                        + branch.url
                                    }
                                    attrs.style = js {
                                        wordBreak = "break-all"
                                        minWidth = "200px"
                                    }
                                }
                                td {
                                    a {
                                        attrs {
                                            href = branch.branchLink
                                        }
                                        + branch.branchLink
                                    }
                                    attrs.style = js {
                                        wordBreak = "break-all"
                                        minWidth = "200px"
                                    }
                                }
                                td {
                                    + branch.lastCommitInfo.first
                                    attrs.style = js {
                                        wordBreak = "break-all"
                                        minWidth = "200px"
                                    }
                                }
                                td {
                                    + DateTime(branch.lastCommitInfo.second).toString(DateFormat.FORMAT_DATE)
                                    attrs.style = js {
                                        wordBreak = "break-all"
                                        minWidth = "200px"
                                    }
                                }
                                button(classes = "btn btn-danger") {
                                    + "Start pipline"
                                    attrs {
                                        onClickFunction = {
                                            state.startPipLine(branch.name)
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
