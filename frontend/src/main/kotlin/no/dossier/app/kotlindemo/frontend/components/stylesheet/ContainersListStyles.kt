package no.dossier.app.kotlindemo.frontend.components.stylesheet

import kotlinx.css.*
import styled.StyleSheet

object ContainersListStyles : StyleSheet("ContainersListStyles") {
    val wrapper by css {
        marginTop = LinearDimension("20px")
        marginBottom = LinearDimension("20px")
        textAlign = TextAlign.center
    }
}