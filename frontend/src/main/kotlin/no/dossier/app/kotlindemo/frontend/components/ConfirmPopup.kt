package no.dossier.app.kotlindemo.frontend.components

import kotlinx.html.style
import no.dossier.app.kotlindemo.frontend.components.stylesheet.SpinnerStyles
import react.*
import react.dom.*
import styled.css
import styled.styledDiv
import kotlinext.js.js

/**
@TODO make a popup confirmation window
* */
class ConfirmPopup : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                + SpinnerStyles.loading
            }
            div(classes = "spinner-border text-info") {
                attrs {
                    style = js {
                        fontSize = "30px"
                        height = "70px"
                        width = "70px"
                    }
                }
            }
        }
    }
}

fun RBuilder.confirmPopup() = child(ConfirmPopup::class) {}
