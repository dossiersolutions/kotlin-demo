package no.dossier.app.kotlindemo.frontend.components.stylesheet

import kotlinx.css.*
import styled.StyleSheet

object AppStyles : StyleSheet("AppStyles") {
    val header by css {
        marginTop = LinearDimension("20px")
        marginBottom = LinearDimension("20px")
    }
    val footer by css {
        height = LinearDimension("40px")
    }
    val mainContainer by css {
        maxWidth = LinearDimension("100%")
        paddingLeft = LinearDimension("15px")
        paddingRight = LinearDimension("15px")
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