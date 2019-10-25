package no.dossier.app.kotlindemo.frontend.components.stylesheet

import kotlinx.css.*
import styled.StyleSheet

object AppStyles : StyleSheet("AppStyles") {
    val header by css {
        marginTop = LinearDimension("20px")
        marginBottom = LinearDimension("20px")
    }
    val footer by css {
        position = Position.fixed
        bottom = LinearDimension(0.toString())
        height = LinearDimension("40px")
    }
    val main by css {

    }
    val mainTitle by css {

    }
    val subTitle by css {
        fontStyle =  FontStyle.italic
        fontWeight = FontWeight.bold
    }
}