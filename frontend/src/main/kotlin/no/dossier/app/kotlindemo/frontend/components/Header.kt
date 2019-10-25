package no.dossier.app.kotlindemo.frontend.components

import no.dossier.app.kotlindemo.frontend.components.stylesheet.AppStyles
import no.dossier.app.kotlindemo.frontend.components.stylesheet.ContainersListStyles
import no.dossier.app.kotlindemo.frontend.constainsts.Pages
import no.dossier.app.kotlindemo.frontend.contexts.appContext
import react.*
import react.dom.*
import styled.css
import styled.styledH1
import styled.styledH2
import styled.styledHeader

class Header : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        appContext.Consumer { state ->
            var pageName = "Bitbucket branches"
            if (state.page == Pages.dockerContainersPage) pageName = "Docker containers"
            styledHeader {
                css {
                    +AppStyles.header
                }
                styledH1 {
                    css {
                        +AppStyles.mainTitle
                    }
                    +"Server Status Manager"
                }
                styledH2 {
                    css {
                        +AppStyles.subTitle
                    }
                    +pageName
                }
            }
        }
    }
}

fun RBuilder.header() = child(Header::class) {}
