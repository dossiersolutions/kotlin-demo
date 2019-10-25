package no.dossier.app.kotlindemo.backend.docker

import java.time.LocalDate

data class DockerContainer(
        val id: String,
        val name: String,
        val status: String,
        val startedAt: LocalDate) {
}