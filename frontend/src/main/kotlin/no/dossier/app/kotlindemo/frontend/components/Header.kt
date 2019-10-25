package no.dossier.app.kotlindemo.frontend.components

import react.*
import react.dom.*

class Header : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        header(classes = "app-header") {
            h1 (classes = "title") {
                +"Server Status Manager"
            }
        }
    }
}

fun RBuilder.header() = child(Header::class) {}
