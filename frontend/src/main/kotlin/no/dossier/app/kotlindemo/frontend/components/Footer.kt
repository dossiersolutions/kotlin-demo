package no.dossier.app.kotlindemo.frontend.components

import react.*
import react.dom.*

class Footer : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        footer(classes = "app-footer") {

        }
    }
}

fun RBuilder.footer() = child(Footer::class) {}
