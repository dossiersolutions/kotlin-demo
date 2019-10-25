package no.dossier.app.kotlindemo.domain.docker

import com.soywiz.klock.DateTime

/**
 *
 * Example Docker ps output
 * CONTAINER ID        IMAGE                        COMMAND                CREATED              STATUS              PORTS               NAMES
4c01db0b339c        ubuntu:12.04                 bash                   17 seconds ago       Up 16 seconds       3300-3310/tcp       webapp
d7886598dbe2        crosbymichael/redis:latest   /redis-server --dir    33 minutes ago       Up 33 minutes       6379/tcp            redis,webapp/db
 */

enum class StatusType{
    Running,
    Paused,
    Restarting,
    RemovalInProgress,
    Dead,
    Created,
    Exited
}

data class DockerContainer(
        val id: String,
        val image: String,
        val command: String,
        val status: StatusType,
        val created: DateTime
        )