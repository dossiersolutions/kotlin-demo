package no.dossier.app.kotlindemo.frontend.components

import no.dossier.app.kotlindemo.frontend.components.stylesheet.AppStyles
import no.dossier.app.kotlindemo.frontend.components.stylesheet.ContainersListStyles
import react.*
import react.dom.*
import styled.css
import styled.styledH1
import styled.styledHeader

class Header : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledHeader {
            css {
                + AppStyles.header
            }
            styledH1 {
                css {
                    + AppStyles.mainTitle
                }
                + "Server Status Manager"
            }
        }
    }
}

fun RBuilder.header() = child(Header::class) {}
