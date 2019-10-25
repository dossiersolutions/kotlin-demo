package no.dossier.app.kotlindemo.frontend.components.stylesheet

import kotlinx.css.*
import styled.StyleSheet

object SpinnerStyles : StyleSheet("SpinnerStyles") {
    val loading by css {
        position = Position.absolute
        top = LinearDimension(0.toString())
        right = LinearDimension(0.toString())
        bottom = LinearDimension(0.toString())
        left = LinearDimension(0.toString())
        background = "#fff"
        display = Display.flex
        alignItems = Align.center
        justifyContent = JustifyContent.center
        zIndex = 9999
    }
}