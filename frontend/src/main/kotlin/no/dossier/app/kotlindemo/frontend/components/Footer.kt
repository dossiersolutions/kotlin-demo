package no.dossier.app.kotlindemo.frontend.components

import no.dossier.app.kotlindemo.frontend.components.stylesheet.AppStyles
import react.*
import react.dom.*
import styled.css
import styled.styledFooter

class Footer : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledFooter {
            css {
               + AppStyles.footer
            }
             + "Developed by "
             strong {
                 + "SSM team"
             }
        }
    }
}

fun RBuilder.footer() = child(Footer::class) {}
